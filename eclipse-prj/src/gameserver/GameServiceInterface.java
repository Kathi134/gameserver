package gameserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;

public interface GameServiceInterface extends Remote
{
	public String openLobby(ClientInterface client) throws RemoteException;
	
	public void joinLobby(String lobbyCode, ClientInterface client) throws RemoteException;
	
	public void leaveLobby(String lobbyCode, ClientInterface client) throws RemoteException;
	
	public void startLobby(String lobbyCode) throws RemoteException;
	
	public void move(String lobbyCode, ClientInterface client) throws RemoteException;
}