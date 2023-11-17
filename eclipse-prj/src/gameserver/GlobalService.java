package gameserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import view.Game;

public class GlobalService extends UnicastRemoteObject implements GlobalServiceInterface
{
	private static final long serialVersionUID = 1L;

	public GlobalService() throws RemoteException
	{}

	@Override
	public int getGamePort(Game requestedGame)
	{
		return switch(requestedGame) 
		{
			case CASCADIA -> 31193;
			default -> throw new IllegalArgumentException("Unexpected value: " + requestedGame);
		};
	}

	
}