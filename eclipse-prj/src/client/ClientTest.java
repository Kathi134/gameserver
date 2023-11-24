package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gameserver.GameServiceInterface;

public class ClientTest
{
	public static final String LOCALHOST = "localhost";
	public static final String ZAP_SERVER = "185.249.198.58";
	public static final String PC_IP = "192.168.1.117";
	
	public static void main(String[] args)
	{
		System.out.println("starting client...");
		// maybe ist port 1109 nciht freigebeen?

		Client c = createClient(PC_IP);
		if(c != null)
		{
			c.testFunctionality();
		}
	}
	
	public static Client createClient(String serverIp)
	{
		try
		{
			System.out.println("initializing server services");
			Registry r = LocateRegistry.getRegistry(serverIp);
			GameServiceInterface gameService = (GameServiceInterface) r.lookup("GameServiceInterface");
			return new Client(serverIp, gameService);
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
