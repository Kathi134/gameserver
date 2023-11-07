package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

import utils.request.Game;

public interface GlobalServiceInterface extends Remote
{
	public int getGamePort(Game requestedGame) throws RemoteException;
}