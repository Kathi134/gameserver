package gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
	public static void main(String[] args)
	{
		final int CASC_PORT = args.length != 0 ? Integer.parseInt(args[0]) : 31193;
		new Server2(CASC_PORT).run();
	}
}

class Server2
{
	private final int CASC_PORT;
	
	public Server2(int p)
	{
		CASC_PORT = p;
	}

	public void run()
	{
		System.out.println("server is listening");
		try
		(
			ServerSocket ss = new ServerSocket(CASC_PORT);
			Socket commSocket = ss.accept();
			var out = new PrintWriter(commSocket.getOutputStream(), true);
			var in = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
		)
		{
			String host = commSocket.getInetAddress().getHostName();
			System.out.println("connection request from " + host);
			String msg = "Hello from the Server. You are " + host;
			out.println(msg);
			String answer = in.readLine();		
			System.out.println("answer from " + host + ": " + answer);	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

