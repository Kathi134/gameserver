package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import communication.Payload;
import communication.Request;
import communication.RequestType;

public class ClientTest
{
	public static void main(String[] args)
	{
		final int PORT = Main.GLOBAL_PORT;
		final String serverIp = "localhost";
		new Client(PORT, serverIp).run();
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
		System.out.println("client: connecting");
		try
		(
			Connection c = new Connection(new Socket(address, CASC_PORT));
		)
		{
			Payload p1 = new Payload("Hello from Client. You are Server", "String");
			Request r = new Request(RequestType.TEST, List.of(p1));
			c.sendRequest(r);
			System.out.println("c: sent: " + r);
			System.out.println("c: got: " + c.receiveRequest());
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
