package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;

public interface GameServiceInterface extends Remote
{
	public String openLobby(ClientInterface client) throws RemoteException;
	
	public void joinLobby(String lobbyCode, ClientInterface client) throws RemoteException;
	
//	public void joinLobby(String lobbyCode) throws RemoteException;
	
	public void move(String lobbyCode) throws RemoteException;
}