package gameserver;

import java.util.ArrayList;
import java.util.List;

import utils.ClientAddressInformation;

public class Lobby
{
	private int id;
	private String lobbyCode;
	private List<ClientAddressInformation> clients = new ArrayList<ClientAddressInformation>();
	
	public Lobby(int id)
	{
		this.id = id;
		this.lobbyCode = "Lobby"+id;
	}
	
	public String getLobbyCode()
	{
		return lobbyCode;
	}
	
	public void addClient(ClientAddressInformation cai)
	{
		clients.add(cai);
	}
	
	public void notifyAllClients(String message)
	{
		clients.stream()
			.map(cai -> cai.callback())
			.forEach(c -> c.accept("ring ring: " + message));
	}
}