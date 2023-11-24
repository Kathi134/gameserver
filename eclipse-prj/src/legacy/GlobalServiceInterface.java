package legacy;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.Game;

public interface GlobalServiceInterface extends Remote
{
	public int getGamePort(Game requestedGame) throws RemoteException;
}