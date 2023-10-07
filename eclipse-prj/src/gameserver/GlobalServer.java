package gameserver;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class GlobalServer
{
	private final int PORT;
	
	public GlobalServer(int p)
	{
		PORT = p;
		OkHttpClient client = new OkHttpClient () ;
	}

	public void run()
	{
		System.out.println("server is listening");
		try
		(
			ServerSocket ss = new ServerSocket(PORT);
			Connection c = new Connection(ss.accept());
		)
		{
			String host = c.getClientAddress().getHostName();
			System.out.println("connection request from " + host);
			String msg = "Hello from the Server. You are " + host;
			c.print(msg);
			String answer = c.read();		
			System.out.println("answer from " + host + ": " + answer);	
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

class Connection implements Closeable
{
	private Socket commSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public Connection(Socket commSocket)
	{
		this.commSocket = commSocket;
		try
		{
			out = new PrintWriter(commSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() throws IOException
	{}
	
	public InetAddress getClientAddress()
	{
		return commSocket.getInetAddress();
	}
	
	public void print(Object o)
	{
		out.print(o);
	}
	
	public <T> T read()
	{
		T result = null;
		try
		{
			result = (T) in.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}

class Packet
{
	private int gamePort;
	private int sessionId;
}