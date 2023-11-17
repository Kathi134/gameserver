package gameserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import client.ClientInterface;

public class GameService extends UnicastRemoteObject implements GameServiceInterface
{
	private static final long serialVersionUID = 1L;
	private final String game;

	protected GameService() throws RemoteException
	{
		super();
		this.game = "cascadia";
	}

	int numberOfActiveLobbies;
	List<Lobby> lobbies = new ArrayList<Lobby>();
	
	@Override
	public String openLobby(ClientInterface client) throws RemoteException
	{
		System.out.println("server: open lobby");
		Lobby l = new Lobby(numberOfActiveLobbies++);
		lobbies.add(l);
		try
		{
			joinLobby(l.getLobbyCode(), client);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		return l.getLobbyCode();
	}
	
	@Override
	public void joinLobby(String lobbyCode, ClientInterface client) throws RemoteException
	{
		System.out.println("server: join lobby");
		findLobby(lobbyCode).ifPresent(l -> l.addClient(client));
		notifyClientsOnPlayerStateChange(lobbyCode);
	}
	
	@Override
	public void leaveLobby(String lobbyCode, ClientInterface client) throws RemoteException
	{
		System.out.println("server: leave lobby");
		findLobby(lobbyCode).ifPresent(l -> l.removeClient(client));
		notifyClientsOnPlayerStateChange(lobbyCode);
	}
	
	private void notifyClientsOnPlayerStateChange(String lobbyCode)
	{
		findLobby(lobbyCode).ifPresent(l -> l.notifyAllClients(c -> {
			try
			{
				List<ClientInterface> players = findLobby(lobbyCode).get().getClients();
				c.lobbyPlayersChanged(players);
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}));
	}
	
	@Override
	public void startLobby(String lobbyCode)
	{
		findLobby(lobbyCode).ifPresent(l -> l.start());
		findLobby(lobbyCode).ifPresent(l -> l.notifyAllClients(c->{
			try
			{
				c.notifyLobbyStarted(l.getStartingPlayer());
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}));
	}
	
	@Override
	public void move(String lobbyCode, ClientInterface client)
	{
		findLobby(lobbyCode).ifPresent(l -> l.notifyAllClients(c -> {
			try
			{
				c.notifyMoveMade(client, lobbyCode);
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}));
	}
	
	private Optional<Lobby> findLobby(String lobbyCode)
	{
		return lobbies.stream()
			.filter(l -> l.getLobbyCode().equals(lobbyCode))
			.findFirst();
	}
}