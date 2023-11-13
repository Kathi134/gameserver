package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
	public void playerJoined(String playerInfo) throws RemoteException;
	
	public void lobbyStarted(String playerTurn) throws RemoteException;
	
	public void moveMade(String byPlayer, String move) throws RemoteException;
}
