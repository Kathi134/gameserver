package view;

import java.util.List;

public interface ClientListener
{
	public void lobbyPlayersChanged(List<String> currentPlayersInLobby);
	
	public void lobbyStarted(String playerTurn);
	
	public void moveMade(String byPlayer, String move);
}
