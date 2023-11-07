package utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import utils.request.Game;

public class GlobalService extends UnicastRemoteObject implements GlobalServiceInterface
{
	private static final long serialVersionUID = 1L;

	public GlobalService() throws RemoteException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getGamePort(Game requestedGame)
	{
		return switch(requestedGame) 
		{
			case CASCADIA -> 31193;
		};
	}

	
}