package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import communication.Payload;
import communication.Request;
import communication.RequestType;


public class GlobalServer
{
	private final int PORT;
	
	public GlobalServer(int p)
	{
		PORT = p;
	}

	public void run()
	{
		System.out.println("server: listening");
		try
		(
			ServerSocket ss = new ServerSocket(PORT);
			Connection c = new Connection(ss.accept());
		)
		{
			String host = c.getClientAddress().getHostName();
			System.out.println("connection request from " + host);
			
			System.out.println("s: got:" + c.receiveRequest());
			Payload p1 = new Payload("Hello from Server. You are " + host, "String");
			Request r = new Request(RequestType.TEST, List.of(p1));
			c.sendRequest(r);
			System.out.println("s: sent: " + r);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
	}
}