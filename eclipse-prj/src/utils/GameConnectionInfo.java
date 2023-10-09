package utils;

import java.net.InetAddress;

public class GameConnectionInfo extends ConnectionInfo
{
	protected int sessionId;
	protected String lobbyCode;
	
	public GameConnectionInfo(InetAddress host, int gamePort, int sessionId, String lobbyCode)
	{
		super(host, gamePort);
		this.sessionId = sessionId;
		this.lobbyCode = lobbyCode;
	}
}