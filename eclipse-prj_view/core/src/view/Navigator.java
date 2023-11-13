package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Navigator extends Game
{
	@Override
	public void create()
	{
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void closeApplication()
	{
		Gdx.app.exit();
	}
}
