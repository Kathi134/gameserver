package main;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;

import communication.Payload;
import communication.Request;
import communication.RequestType;

class Connection implements Closeable
{
	private Socket commSocket;
	private PrintWriter out;
	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;
	private BufferedReader in;
	
	public Connection(Socket commSocket)
	{
		this.commSocket = commSocket;
		try
		{
			objOut = new ObjectOutputStream(commSocket.getOutputStream());
//			out = new PrintWriter(commSocket.getOutputStream());
			objIn = new ObjectInputStream(commSocket.getInputStream());
//			in = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
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
	
	private void print(Request r)
	{
		out.println(r);
	}
	
	public void sendRequest(Request r)
	{
		try
		{
			objOut.writeObject(r);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Request receiveRequest()
	{
		try
		{
			var r = (Request) objIn.readObject();
//			Payload foo = r.payloads().get(0);
//			Class type = Class.forName(foo.targetClazz());
//			var obj = type.cast(foo.payload());
//			System.out.println(obj);
			
			Payload foo = r.payloads().get(0);
			Class type = RequestType.map.get(r.type());
			System.out.println(type.cast(foo.payload()));
		} 
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	private <T> T read()
	{
		try
		{
			Request r = Request.parseString(in.readLine());
			System.out.println(r);
			return (T) r;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
//		try
//		{
//			result = (T) in.readLine();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		return result;
		return null;
	}
}