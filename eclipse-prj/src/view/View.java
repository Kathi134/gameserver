package view;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import client.Client;
import client.ClientTest;
import utils.ArrayHelper;

public class View implements ClientListener
{
	Scanner scanner = new Scanner(System.in);
	Navigator navigator;
	Client networkClient;

	boolean online;
	String name;
	String currentLobbyCode;
	List<String> playersInLobby;

	public View(Navigator nav)
	{
		navigator = nav;
		networkClient = ClientTest.createLocalhostClient();
		networkClient.addListener(this);
	}

	public void sayGoodbye()
	{
		System.out.println("See you soon!");
	}

	public void welcome()
	{
		System.out.println("Welcome to KP-Games!");
		System.out.println("What's your name?");
		name = scanner.nextLine();
		networkClient.setPlayerName(name);
		mainMenu();
	}

	public void mainMenu()
	{
		System.out.println("Choose a game to play:");
		System.out.println("\t[c] Cascadia, [b] Bluff");
		System.out.println("[q] exit");

		online = false;
		char input = awaitUserInput('c', 'b', 'q');

		if(input == 'q')
			navigator.endProgram();
		else
			navigator.openGame(Game.get(input));
	}

	public void openCascadiaView()
	{
		System.out.println("Alert: Cascadia is not available yet.");
		navigator.toMainMenu();
	}

	public void openBluffGameConfigurationView()
	{
		System.out.println("BLUFF - Gut geblufft ist halb gewonnen!");
		System.out.println("Choose game mode:");
		System.out.println("\t[l] local, [o] online");
		System.out.println("[m] main menu");
		System.out.println("[q] exit");

		char input = awaitUserInput('l', 'o', 'm', 'q');
		if(input == 'q')
			navigator.endProgram();
		else if(input == 'm')
			navigator.toMainMenu();
		else if(input == 'l')
		{
			online = false;
			System.out.println("Alert: Local is not available for Bluff.");
			navigator.toMainMenu();
		}
		else if(input == 'o')
		{
			online = true;
			currentLobbyCode = openOnlineConfigView();
			System.out.println("You're in lobby: " + currentLobbyCode);
		}

		System.out.println("\t[s] start game, [l] leave game");
		input = awaitUserInput('s', 'l');
		if(input == 's')
		{
			try
			{
				if(online)
					// disable other players to start lobby
					networkClient.startLobbyOnServer(currentLobbyCode);
				// gameController.start()
				// -> determine on the client who started, who's turn it is
				// showGameView();
				// networkClient.notifyStartingPlayer() -> if its you -> enable moving, if its
				// another: show name
				// if your turn:
				// gameController.play stuff
				// game Controller.finishMove
				// networkClient makemove(gameController.getnextPLayerIndex);
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
				System.out.println("an error occurred starting the lobby.");
			}
		}
		else if(input == 'l')
		{
			if(online)
			{
				try
				{
					networkClient.leaveLobbyOnServer(currentLobbyCode);
				}
				catch (RemoteException e)
				{
					System.out.println("An error occurred notifying your lobby mates that you left.");
				}
			}
			mainMenu();
		}
	}

	public String openOnlineConfigView()
	{
		System.out.println("\t[j] join Lobby, [c] create Lobby");
		char input = awaitUserInput('j', 'c');
		String lobbyCode = "";
		if(input == 'c')
		{
			try
			{
				lobbyCode = networkClient.openLobbyOnServer();
			}
			catch (RemoteException e)
			{
				System.out.println("failed to create a lobby.");
				return "";
			}
		}
		else if(input == 'j')
		{
			System.out.println("enter Lobbycode:");
			scanner.nextLine();
			lobbyCode = scanner.nextLine();
			try
			{
				networkClient.joinLobbyOnServer(lobbyCode);
			}
			catch (RemoteException e)
			{
				System.out.println("failed to join lobby " + lobbyCode);
				return "";
			}
		}
		return lobbyCode;
	}

	public char awaitUserInput(char... options)
	{
		char input = scanner.next().charAt(0);
		while (!ArrayHelper.elementIn(input, options))
		{
			System.out.println("Select a valid option:");
			input = scanner.next().charAt(0);
		}
		return input;
	}

	@Override
	public void lobbyPlayersChanged(List<String> newplayerState)
	{
		playersInLobby = newplayerState;
		System.out.println(playersInLobby.stream().reduce("Players in lobby:", (a, b) -> a + "\n\t" + b));
	}

	@Override
	public void lobbyStarted(String playerTurn)
	{
		System.out.println("the lobby started. it's " + playerTurn + "'s turn.");
	}

	@Override
	public void moveMade(String byPlayer, String move)
	{
		System.out.println(byPlayer + " moved: " + move);
	}

}
