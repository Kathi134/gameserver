package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
	public void playerJoined(ClientInterface joinedClient) throws RemoteException;
	
	public void lobbyStarted(String playerTurn) throws RemoteException;
	
	public void moveMade(ClientInterface byPlayer, String move) throws RemoteException;
}
