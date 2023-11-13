package client;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import utils.GameServiceInterface;
import utils.GlobalServiceInterface;
import utils.request.Game;

public class ClientTest
{
	public static void main(String[] args)
	{
		System.out.println("starting client...");
//		final String serverIp = "185.249.198.58";
		// maybe ist port 1109 nciht freigebeen?
		final String serverIp = "localhost";
		try
		{
			System.out.println("initializing server services");
			Registry r = LocateRegistry.getRegistry(serverIp);
			GameServiceInterface gameService = (GameServiceInterface) r.lookup("GameServiceInterface");
			GlobalServiceInterface globalService = (GlobalServiceInterface) r.lookup("GlobalServiceInterface");
			new Client(serverIp, gameService, globalService).run();
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}
}

class Client implements Serializable, ClientInterface
{
	private GameServiceInterface gameService;
	private GlobalServiceInterface globalService;

	private int GAME_PORT;
	private InetAddress serverAddress;
	private String lobbyCode;

	public Client(String ip, GameServiceInterface gameService, GlobalServiceInterface globalService)
	{
		this.gameService = gameService;
		this.globalService = globalService;
		try
		{
			serverAddress = InetAddress.getByName(ip);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		System.out.println("testing functionality");
		try
		{
			GAME_PORT = getGamePort();
			System.out.println(GAME_PORT);
			lobbyCode = openLobby();
			System.out.println(lobbyCode);
			joinLobby(lobbyCode);		
			gameService.move(lobbyCode);
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

	public void joinLobby(String lobbyCode) throws RemoteException
	{
		// how to determine the cai? -> maybe do a callback?
		// problem: callback is still ran serverside -> syso on server console
		// alternativ: in join/openLobby Socket connection aufbauen und dann ein json runden objekt uebermittlen
		gameService.joinLobby(lobbyCode, this::notify);
	}
	
	public String openLobby() throws RemoteException
	{
		return gameService.openLobby(this::notify);
	}
	
	@Override
	public void playerJoined(String playerInfo) throws RemoteException
	{
		System.out.println("player joined: " + playerInfo);
	}

	@Override
	public void lobbyStarted(String playerTurn) throws RemoteException
	{
		System.out.println("lobby started. player turn: " + playerTurn);
	}

	@Override
	public void moveMade(String byPlayer, String move) throws RemoteException
	{
		System.out.println(byPlayer + " moved: " + move);
	}
}
