package gameserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import utils.ClientAddressInformation;
import utils.GameServiceInterface;

public class GameService extends UnicastRemoteObject implements GameServiceInterface
{
	private static final long serialVersionUID = 1L;

	protected GameService() throws RemoteException
	{
		super();
	}

	int numberOfActiveLobbies;
	List<Lobby> lobbies = new ArrayList<Lobby>();
	
	@Override
	public String openLobby() 
	{
		System.out.println("open lobby");
		Lobby l = new Lobby(numberOfActiveLobbies++);
		try
		{
			InetAddress host = determineHost();
			joinLobby(l.getLobbyCode(), new ClientAddressInformation(host));
			return l.getLobbyCode();
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	@Override
	public void joinLobby(String lobbyCode, ClientAddressInformation c)
	{
		System.out.println("join lobby");
		lobbies.stream()
			.filter(l -> l.getLobbyCode().equals(lobbyCode))
			.findFirst()
			.ifPresent(l -> l.addClient(c));
	}
	
	private InetAddress determineHost() throws UnknownHostException, ServerNotActiveException
	{
		return InetAddress.getByName(RemoteServer.getClientHost());
	}
}