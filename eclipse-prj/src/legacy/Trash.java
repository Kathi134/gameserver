package legacy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Trash
{

}


class Client2
{
	private final int GAME_PORT;
	private InetAddress serverAddress;

	public Client2(int p, String ip)
	{
		GAME_PORT = p;
		try
		{
			serverAddress = InetAddress.getByName(ip);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		System.out.println("connecting to server");
		Connection c = tryConnection();
		if(c == null)
		{
			System.out.println("an error occurred");
		}
		else if(!c.isEstablishable())
		{
			System.out.println("server not reachable");
		}
		else
		{
//			var foo = c.read();
			System.out.println("game connection establishable on port " + c.getClientAddress());
		}
	}

	public Connection tryConnection()
	{
		try
		{
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(serverAddress, GAME_PORT), 1000);
			System.out.println(socket.getInetAddress().getHostAddress());
			return new Connection(socket);
		}
		catch (SocketTimeoutException e)
		{
			return new Connection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
//		try
//		(
//		)
//		{			
//			Connection c = new Connection(s);
//			String msg = "Hello! - the client";
//			c.print(msg);
//			System.out.println("c: sent: " + msg);
//			System.out.println("c: got: " + c.read());
//			return c;
//		}  
//		catch (UnknownHostException e)
//		{
//			e.printStackTrace();
//		} 
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		return null;
	}

	public int getGamePort()
	{
		return 0;
	}
}
