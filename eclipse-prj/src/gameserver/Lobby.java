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
		System.out.println("added" + cai.toString());
		clients.add(cai);
		clients.stream().forEach(c -> System.out.print(c.a()));
	}
}