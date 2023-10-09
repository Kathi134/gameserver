package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gameserver.Main;
import utils.Connection;
import utils.ConnectionInfo;
import utils.GameConnectionInfo;
import utils.GameServiceInterface;
import utils.request.Data;
import utils.request.Game;
import utils.request.Request;
import utils.request.RequestType;

public class ClientTest
{
	public static void main(String[] args)
	{
		System.out.println("starting client...");
//		final String serverIp = "185.249.198.58";
		final String serverIp = "localhost";
		try
		{
			System.out.println("initializing server services");
			Registry r = LocateRegistry.getRegistry(serverIp);
			GameServiceInterface gameService = (GameServiceInterface) r.lookup("GameServiceInterface");
			new Client(serverIp, gameService).run();
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

class Client
{
	private GameServiceInterface service;

	private int GAME_PORT;
	private InetAddress serverAddress;
	private String lobbyCode;

	public Client(String ip, GameServiceInterface service)
	{
		this.service = service;
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
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getGamePort()
	{
		ConnectionInfo connectionInfo = new ConnectionInfo(serverAddress, Main.GLOBAL_PORT);
		Connection connection = connectionInfo.connect();
		connection.print(new Request(RequestType.PORT_REQUEST, Game.CASCADIA));
		System.out.println("request sent");
		Request r = connection.read();
		Data answer = (Data) r.laod()[0];
		return answer.i;
	}

	public void joinLobby(String lobbyCode) throws RemoteException
	{
		// how to determine the cai?
		service.joinLobby(lobbyCode, null);
	}
	
	public String openLobby() throws RemoteException
	{
		return service.openLobby();
	}

	private GameConnectionInfo getGameRequest()
	{
		if(GAME_PORT == 0)
			return null;
		else
			return new GameConnectionInfo(serverAddress, GAME_PORT, 0, lobbyCode);
	}
}
