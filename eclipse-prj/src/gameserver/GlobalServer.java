package gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import utils.Connection;
import utils.request.Data;
import utils.request.Game;
import utils.request.Request;
import utils.request.RequestType;
	
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
		try
		(
			ServerSocket ss = new ServerSocket(PORT);
			Connection c = new Connection(ss.accept());
		)
		{
			System.out.println("connection established with a client");
			Request tmp = c.read();
			Data d = new Data();
			if(tmp.type() == RequestType.PORT_REQUEST)
			{
				int port = switch ((Game)(tmp.laod()[0]))
				{
					case CASCADIA -> 31193;
				};
				d.i = port;
			}
			Request request = new Request(RequestType.PORT_REQUEST, d);
			c.print(request);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void prepareRMIService()
	{
		System.out.println("initializing services...");
		try
		{
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			var service = new GameService();
			// Stub von Remote-Objekt in Registry bekanntmachen
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("GameServiceInterface", service);
			System.out.println("server ready!");
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}