package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gameserver.GameServiceInterface;
import gameserver.GlobalServiceInterface;

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
			new Client(serverIp, gameService, globalService).testFunctionality();
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
