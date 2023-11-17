package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gameserver.GameServiceInterface;
import gameserver.GlobalServer;
import gameserver.GlobalServiceInterface;

public class ClientTest
{
	public static void main(String[] args)
	{
		System.out.println("starting client...");
		// maybe ist port 1109 nciht freigebeen?

		Client c = createLocalhostClient();
		if(c != null)
		{
			c.testFunctionality();
		}
	}
	
	public static Client createLocalhostClient()
	{
//		final String serverIp = "localhost";
		final String serverIp = GlobalServer.IP_ADDRESS;
		try
		{
			System.out.println("initializing server services");
			Registry r = LocateRegistry.getRegistry(serverIp);
			GameServiceInterface gameService = (GameServiceInterface) r.lookup("GameServiceInterface");
			GlobalServiceInterface globalService = (GlobalServiceInterface) r.lookup("GlobalServiceInterface");
			return new Client(serverIp, gameService, globalService);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		catch (NotBoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
