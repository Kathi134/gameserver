package view;

import model.BetResult;

public record BetResultTriple(BetResult result, int[] loosingPlayers, int lostDice)
{
	
}
