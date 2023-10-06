package gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest
{
	public static void main(String[] args)
	{
		final int CASC_PORT = args.length != 0 ? Integer.parseInt(args[0]) : 31193;
		final String serverIp = "185.249.198.58";
		new Client(CASC_PORT, serverIp).run();
	}
}

class Client
{
	private final int CASC_PORT;
	private InetAddress address;
	
	public Client(int p, String ip)
	{
		CASC_PORT = p;
		try
		{
			address = InetAddress.getByName(ip);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		System.out.println("connecting to server");
		try
		(
			Socket s = new Socket(address, CASC_PORT);
			var out = new PrintWriter(s.getOutputStream(), true);
			var in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		)
		{			
			String msg = "Hello! - the client";
			out.println(msg);
			System.out.println("c: sent: " + msg);
			System.out.println("c: got: " + in.readLine());
		}  
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
