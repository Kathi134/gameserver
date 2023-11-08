

package gameserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import utils.GlobalService;
	
public class GlobalServer
{
	private final int PORT;
	
	public GlobalServer(int p) 
	{
		System.out.println("starting server...");
		PORT = p;
//		GameService service;
		
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
			
			var globalService = new GlobalService();
			registry.rebind("GlobalServiceInterface", globalService);
			
			System.out.println("server ready!");
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}