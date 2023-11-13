package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameConfigurationScreen extends AbstractScreen implements Screen
{
	private static final float START_BUTTON_HEIGHT = 20f;
	private static final float START_BUTTON_WIDTH = 200f;
	
	private HorizontalGroup playerNameGroup = new HorizontalGroup();
	private TextButton startButton;
	private int players;
	
	protected GameConfigurationScreen(Navigator controller)
	{
		super(controller);
	}

	private void initializeConfigMenu()
	{
		startButton.setWidth(START_BUTTON_WIDTH);
		startButton.setHeight(START_BUTTON_HEIGHT);
		startButton.setPosition(Gdx.graphics.getWidth() - START_BUTTON_WIDTH, START_BUTTON_HEIGHT / 2);

		startButton.addListener(new ClickListener()
		{
			

			private String[] collectPlayerNames()
			{
				String[] playerNames = new String[players];

				for(int i = 0; i < players; i++)
				{
					VerticalGroup field = (VerticalGroup) playerNameGroup.getChild(i);
					TextField text = (TextField) field.getChild(1);
					playerNames[i] = text.getText();
				}

				return playerNames;
			}
		});

		stage.addActor(startButton);
	}

}
