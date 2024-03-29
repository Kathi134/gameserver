package gameserver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import client.ClientInterface;

public class Lobby
{
	private int id;
	private String lobbyCode;
	private List<ClientInterface> clients = new ArrayList<ClientInterface>();
	private boolean running;
	
	// TODO: autoclean up -> try reaching clients in list, if not available remove them from list
	
	public Lobby(int id)
	{
		this.id = id;
		this.lobbyCode = "Lobby"+id;
	}
	
	public String getLobbyCode()
	{
		return lobbyCode;
	}
	
	public void addClient(ClientInterface cai)
	{
		if(!running)
			clients.add(cai);
	}
	
	public void removeClient(ClientInterface client)
	{
		clients.remove(client);
	}
	
	public void notifyAllClients(Consumer<ClientInterface> notifyFunctionality)
	{
		clients.stream().forEach(c -> notifyFunctionality.accept(c));
	}
	
	public List<ClientInterface> getClients()
	{
		return clients;
	}
	
	public void start()
	{
		running = true;
	}

	public ClientInterface getStartingPlayer()
	{
		if (clients.isEmpty())
			return null;
		return clients.get(0);
	}
}