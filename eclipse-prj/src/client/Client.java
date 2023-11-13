package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gameserver.GameServiceInterface;
import gameserver.GlobalServiceInterface;
import view.Game;

public class Client extends UnicastRemoteObject implements Serializable, ClientInterface
{
	private static final long serialVersionUID = 1L;
	
	private GameServiceInterface gameService;
	private GlobalServiceInterface globalService;

	private int GAME_PORT;
	private String lobbyCode;
	
	private String playerName = "";

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
			lobbyCode = openLobby(this);
			System.out.println(lobbyCode);
			joinLobby("Lobby1", this);		
			gameService.move(lobbyCode, this);
			System.out.println("everything works");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getGamePort() throws RemoteException
	{
		return globalService.getGamePort(Game.CASCADIA);
	}

	public void joinLobby(String lobbyCode, ClientInterface client) throws RemoteException
	{
		gameService.joinLobby(lobbyCode, client);
	}
	
	public String openLobby(ClientInterface client) throws RemoteException
	{
		return gameService.openLobby(client);
	}
	
	@Override
	public void playerJoined(ClientInterface joinedClient) throws RemoteException
	{
		System.out.println("player joined: " + joinedClient);
	}

	@Override
	public void lobbyStarted(String playerTurn) throws RemoteException
	{
		System.out.println("lobby started. player turn: " + playerTurn);
	}

	@Override
	public void moveMade(ClientInterface byPlayer, String move) throws RemoteException
	{
		System.out.println(byPlayer + " moved: " + move);
	}
	
	public String getPlayerId()
	{
		return playerName;
	}
}