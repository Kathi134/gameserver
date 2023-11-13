package gameserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

//import $2a_LambdaExpressions.InstanceStringChecker;
import client.ClientInterface;

import java.util.Optional;

import utils.ClientAddressInformation;
import utils.GameServiceInterface;
import utils.SerializableConsumer;

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
		
//		var foo = (ClientInterface inst, String str) -> inst.playerJoined(str);
		findLobby(lobbyCode).ifPresent(l -> l.notifyAllClients(new ClientInterface()::playerJoined));
		findLobby(lobbyCode).ifPresent(l -> l.addClient(client));
	}
	
	@Override
	public void move(String lobbyCode)
	{
		findLobby(lobbyCode).ifPresent(l -> l.notifyAllClients(new ClientInterface()::moveMade));
	}
	
	private Optional<Lobby> findLobby(String lobbyCode)
	{
		return lobbies.stream()
			.filter(l -> l.getLobbyCode().equals(lobbyCode))
			.findFirst();
	}
	
	private InetAddress determineHost()
	{
		try
		{
			return InetAddress.getByName(RemoteServer.getClientHost());
		}
		catch (UnknownHostException | ServerNotActiveException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private ClientAddressInformation getCAI(SerializableConsumer<String> callback)
	{
		return new ClientAddressInformation(determineHost(), callback);
	}
}