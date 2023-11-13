package view;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;



public class MainMenuScreen extends AbstractScreen implements Screen
{
	public MainMenuScreen(Navigator navigator)
	{
		super(navigator);
		initializeButtons();
	}
	
	private void initializeButtons()
	{
		List<TextButton> buttons = createButtons();
        for(int i = 0; i < buttons.size(); i++)
        {
        	TextButton btn = buttons.get(i);
        	btn.setWidth(300);
        	btn.setHeight(50);
        	btn.getLabel().setFontScale(2);
        	btn.setPosition(stage.getWidth()/2 + 520, i>=2 ? 500 + 100 * i : 200 + 100 * i);
        	btn.sizeBy(20);
        	stage.addActor(btn);
        }
        
        Gdx.input.setInputProcessor(stage);
	}
	
	private List<TextButton> createButtons()
	{
		TextButton btnNewGame = new TextButton("Bluff", skin);
        btnNewGame.addListener(new ChangeListener()
        {
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				nextScreen(new GameConfigurationScreen(navigator));
			}
		});

		TextButton btnExit = new TextButton("KP-Games verlassen", skin);
		btnExit.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				showGameCloseDialog();
			}
		});
        
        TextButton[] tmp = new TextButton[] {btnExit, btnNewGame};
        return Arrays.asList(tmp);
	}
	
	private void showGameCloseDialog()
	{
		Modal modal = new Modal("Wirklich beenden", skin)
		{
			@Override
			protected void result(Object object)
			{
				if((boolean) object)
				{
					navigator.closeApplication();
				}
			}
		};
		modal.text("Möchtest du das Spiel wirklich verlassen?");
		modal.button("Ja", true);
		modal.button("Nein", false);
		modal.show(stage);
	}
}
