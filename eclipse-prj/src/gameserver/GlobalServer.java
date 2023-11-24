package gameserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
	
public class GlobalServer
{
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
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Registry registry = LocateRegistry.getRegistry();

			var service = new GameService();
			registry.rebind("GameServiceInterface", service);
			
//			var globalService = new GlobalService();
//			registry.rebind("GlobalServiceInterface", globalService);
			
			System.out.println("server ready!");
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
