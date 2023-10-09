package utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConnectionInfo
{
	public ConnectionInfo(InetAddress host, int gamePort)
	{
		this.host = host;
		this.port = gamePort;
	}
	
	protected InetAddress host;
	protected int port;

	public Connection connect()
	{
		try
		{
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), 1000);
			System.out.println("Connection established with " + socket.getInetAddress().getHostAddress());
			return new Connection(socket);
		}
		catch (SocketTimeoutException e)
		{
			System.err.println("Connection not establishable. Socket timed out.");
			return new Connection();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return new Connection();
		}
	}

}