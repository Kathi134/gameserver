package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServiceInterface extends Remote
{
	public String openLobby() throws RemoteException;
	
	public void joinLobby(String lobbyCode, ClientAddressInformation c) throws RemoteException;
}