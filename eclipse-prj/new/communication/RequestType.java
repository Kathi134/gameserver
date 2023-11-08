package communication;

import java.util.EnumMap;

import main.Game;

public enum RequestType
{
	PORT_REQUEST, JOIN_LOBBY, START_LOBBY, CREATE_LOBBY, MOVE, VOID, TEST;
	
	public static EnumMap<RequestType, Class<?>> map = new EnumMap<>(RequestType.class) 
	{
		private static final long serialVersionUID = 1L;
		{
			put(CREATE_LOBBY, null);
			put(JOIN_LOBBY, String.class);
			put(MOVE, GameState.class);
			put(PORT_REQUEST, Game.class);
			put(START_LOBBY, null);
			put(VOID, null);
			put(TEST, String.class);
		}
	};
}

class GameState{}

//request the port for the game played
//join/create lobby
//start lobby
//make move -> payload