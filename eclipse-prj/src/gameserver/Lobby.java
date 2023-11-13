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
		clients.add(cai);
	}
	
	public void notifyAllClients(Consumer<ClientInterface> notifyFunctionality)
	{
		clients.stream().forEach(c -> notifyFunctionality.accept(c));
	}
}