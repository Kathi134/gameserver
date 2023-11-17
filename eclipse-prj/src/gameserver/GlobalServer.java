package gameserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
	
public class GlobalServer
{
//	private final String ipAdress = "localhost";
	public static final String IP_ADDRESS = "185.249.198.58";
	
	public GlobalServer() 
	{
		System.out.println("starting server...");
	}

	public void run()
	{
		prepareRMIService();
	}
	
	private void prepareRMIService()
	{
		System.out.println("initializing services...");
		try
		{
			System.setProperty("java.rmi.server.hostname", IP_ADDRESS);
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Registry registry = LocateRegistry.getRegistry();

			var service = new GameService();
			registry.rebind("GameServiceInterface", service);
			
			var globalService = new GlobalService();
			registry.rebind("GlobalServiceInterface", globalService);
			
			System.out.println("server ready!");
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}