package utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import utils.request.Request;

public class Connection implements Closeable
{
	private Socket commSocket;
	private PrintWriter out;
	private BufferedReader in;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	private boolean establishable;
	
	public Connection()
	{
		establishable = false;
	}
	
	public Connection(Socket commSocket)
	{
		establishable = false;
		this.commSocket = commSocket;
		try
		{
			out = new PrintWriter(commSocket.getOutputStream(), true);
			objectOut = new ObjectOutputStream(commSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
			objectIn = new ObjectInputStream(commSocket.getInputStream());
			establishable = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() throws IOException
	{}
	
	public boolean isEstablishable()
	{
		return establishable;
	}
	
	public int getPort()
	{
		return 0;
	}
	
	public InetAddress getClientAddress()
	{
		return commSocket.getInetAddress();
	}
	
	public void print(Request r)
	{
//		out.println(Request.serialize(r));
		try
		{
			objectOut.writeObject(r);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Request read()
	{
		Request result = null;
		try
		{
//			String foo = in.readLine();
//			System.out.println("HERE " + foo);
//			result = Request.deserialize(foo);
//			result = (Request) in.readLine();
			result = (Request) objectIn.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}