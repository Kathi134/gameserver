package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote
{
	public void lobbyPlayersChanged(List<ClientInterface> currentPlayersInLobby) throws RemoteException;
	
	public void notifyLobbyStarted(ClientInterface playerTurn) throws RemoteException;
	
	public void notifyMoveMade(ClientInterface byPlayerName, String move) throws RemoteException;
	
	public String getPlayerName() throws RemoteException;
}
