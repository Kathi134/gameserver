package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import gameserver.GameServiceInterface;
import gameserver.GlobalServiceInterface;
import view.ClientListener;
import view.Game;

public class Client extends UnicastRemoteObject implements Serializable, ClientInterface
{
	private static final long serialVersionUID = 1L;
	
	private GameServiceInterface gameService;
	private GlobalServiceInterface globalService;

	private int GAME_PORT;
	private String lobbyCode;
	
	private String playerName = "";
	
	private List<ClientListener> listeners = new ArrayList<>();

	public Client(String ip, GameServiceInterface gameService, GlobalServiceInterface globalService) throws RemoteException
	{
		this.gameService = gameService;
		this.globalService = globalService;
	}

	public void testFunctionality()
	{
		System.out.println("testing functionality");
		try
		{
			GAME_PORT = getGamePort();
			System.out.println(GAME_PORT);
			lobbyCode = openLobbyOnServer();
			System.out.println(lobbyCode);
			joinLobbyOnServer("Lobby1");		
			gameService.move(lobbyCode, this);
			System.out.println("everything works");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addListener(ClientListener listener)
	{
		listeners.add(listener);
	}
	
	public void setPlayerName(String playerName)
	{
		if (this.playerName!="")
		{
			System.err.printf("Name was already set! [from %s to %s.%n", this.playerName, playerName);
		}
		this.playerName = playerName;
	}

	public int getGamePort() throws RemoteException
	{
		return globalService.getGamePort(Game.CASCADIA);
	}

	public void joinLobbyOnServer(String lobbyCode) throws RemoteException
	{
		gameService.joinLobby(lobbyCode, this);
	}
	
	public void leaveLobbyOnServer(String lobbyCode) throws RemoteException
	{
		gameService.leaveLobby(lobbyCode, this);
	}
	
	public String openLobbyOnServer() throws RemoteException
	{
		return gameService.openLobby(this);
	}
	
	public void startLobbyOnServer(String lobbyCode) throws RemoteException
	{
		gameService.startLobby(lobbyCode);
	}
	
	@Override
	public void lobbyPlayersChanged(List<ClientInterface> currentPlayersInLobby) throws RemoteException
	{
		List<String> clients = currentPlayersInLobby.stream()
			.map(p -> {
				try
				{
					return p.getPlayerName();
				}
				catch (RemoteException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return"";
			})
			.toList();
		listeners.forEach(l->l.lobbyPlayersChanged(clients));
	}

	@Override
	public void notifyLobbyStarted(ClientInterface playerTurn) throws RemoteException
	{
		listeners.forEach(l->{
			try
			{
				l.lobbyStarted(playerTurn.getPlayerName());
			}
			catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void notifyMoveMade(ClientInterface byPlayer, String move) throws RemoteException
	{
		listeners.forEach(l->{
			try
			{
				l.moveMade(byPlayer.getPlayerName(), move);
			}
			catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public String getPlayerName() throws RemoteException
	{
		return playerName;
	}

}