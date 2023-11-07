package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServiceInterface extends Remote
{
	public String openLobby(SerializableConsumer<String> callback) throws RemoteException;
	
	public void joinLobby(String lobbyCode, SerializableConsumer<String> notifyCallback) throws RemoteException;
	
//	public void joinLobby(String lobbyCode) throws RemoteException;
	
	public void move(String lobbyCode) throws RemoteException;
}