package view;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.nio.file.InvalidPathException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public abstract class AbstractScreen implements Screen
{
	private static final String SKIN = "default/skin/uiskin.json";
	private static final String FONT = "default/skin/enhanced.fnt";
	private static final String MISSING_NAME_WARNING = "Kein Rundenname vergeben";
	private static final String NAME_GENERATION_WARNING = "Wenn kein Name angegeben wird, wird ein Spielstandname generiert";
	private static final String UNSAVED_WARNING = "Alle ungespeicherten Änderungen gehen verloren!";
	private static final String LEAVE_DIALOG_TITLE = "Wirklich verlassen?";
	private static final String RENAME_ROUND_TITLE = "Runde umbenennen";
	private static final String SAVE_FAILURE_MESSAGE = "Speichern fehlgeschlagen";
	private static final String SAVE_SUCCESS_MESSAGE = "Speichern erfolgreich";
	private static final String FILENAME_INVALID_MESSAGE = "Der Dateiname kann nicht verarbeitet werden";
	private static final String SOUND_FILE_ERROR_MES = "Sound File Error";
	private static final int INITIAL_WIDTH = 1920;
	private static final int INITIAL_HEIGHT = 1080;

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm")
			.withZone(ZoneId.systemDefault());

	private enum SaveDialogResult
	{
		ABORT, RENAME, OVERWRITE
	}

	protected Navigator navigator;

	protected Viewport viewport;
	protected OrthographicCamera camera;

	protected SpriteBatch batch;

	protected BitmapFont font;
	protected Skin skin;

	protected Stage stage;

	protected Screen previousScreen;

	private boolean isDisposed;

	protected AbstractScreen(Navigator controller)
	{
		this.navigator = controller;
		initializeSkin();
		initializeGeneralRenderingTools();
		initializeSkin();
		previousScreen = controller.getScreen();
	}

	private void initializeGeneralRenderingTools()
	{
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal(FONT));
		font.getData().setScale(2, 2);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, INITIAL_WIDTH, INITIAL_HEIGHT);
		viewport = new FitViewport(INITIAL_WIDTH, INITIAL_HEIGHT, camera);
		stage = new Stage(viewport);
	}

	private void initializeSkin()
	{
		skin = new Skin(Gdx.files.internal(SKIN));
	}

	private Screen createInstanceFromScreen(Class<? extends Screen> screenType)
	{
		if(screenType == null)
		{
			return null;
		}

		Constructor<? extends Screen> ctor;
		try
		{
			ctor = screenType.getConstructor(Navigator.class);
		}
		catch (Exception e)
		{
			try
			{
				ctor = screenType.getConstructor();
			}
			catch (Exception ex)
			{
				ctor = null;
			}
		}

		if(ctor == null)
		{
			return null;
		}

		try
		{
			if(ctor.getParameterCount() == 1)
			{
				return ctor.newInstance(navigator);
			}
			return ctor.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	protected Screen getPreviousScreen()
	{
		if(previousScreen == null || previousScreen.getClass().equals(this.getClass()))
		{
//			if(controller.getRound() != null)
//			{
//				return new PauseMenuScreen(controller);
//			}
		}
		if(previousScreen instanceof AbstractScreen)
		{
			if(!((AbstractScreen) previousScreen).isDisposed())
			{
				return previousScreen;
			}
			Screen instance = createInstanceFromScreen(previousScreen.getClass());
			if(instance != null)
			{
				return instance;
			}
		}
		return new MainMenuScreen(navigator);
	}

	public void setPreviousScreen(Screen previousScreen)
	{
		this.previousScreen = previousScreen;
	}

	protected void onBackButtonClick()
	{
		try
		{
//			controller.setAccount(null);
			nextScreen(previousScreen.getClass().getConstructor(Navigator.class).newInstance(navigator));
		}
		catch (Exception e)
		{
		}
		dispose();
	}

	protected void initializeNavigation()
	{
		TextButton button = new TextButton("Zurück", skin);
		button.setWidth(200f);
		button.setHeight(20f);
		button.setPosition(10, 10);

		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
//				playClickButtonSound();
				onBackButtonClick();
			}
		});

		stage.addActor(button);

		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Show the next screen. If the asset manager has pending assets, a loading
	 * screen will be shown.
	 * 
	 * @param nextScreen the next screen
	 */
	protected void nextScreen(Screen nextScreen)
	{
		navigator.setScreen(nextScreen);
		disposeUtils();
		dispose();
	}


//	protected void saveRound()
//	{
//		saveRound(this.stage);
//	}

//	protected void saveRound(Stage target)
//	{
//		saveRound(target, false, false);
//	}

//	protected void saveRound(Stage target, boolean force, boolean allowBlankRoundName)
//	{
//		try
//		{
//			String roundName = controller.getRound().getName();
//			if(!allowBlankRoundName && (roundName == null || roundName.trim().length() == 0))
//			{
//				handleMissingRoundName(stage);
//				return;
//			}
//			if(!force && Persister.instance().roundExists(roundName))
//			{
//				handleRoundNameConflict(stage);
//				return;
//			}
//			Persister.instance().saveRound(roundName, controller.getRound());
//			Modal modal = new Modal(SAVE_SUCCESS_MESSAGE, skin)
//			{
//				@Override
//				protected void result(Object object)
//				{
//					playClickButton();
//				}
//			};
//			modal.button("Ok");
//			modal.show(stage);
//		}
//		catch (Exception e)
//		{
//			handleRoundSaveException(target, e);
//		}
//		allowBlankRoundName = false;
//	}

//	protected void handleRoundSaveException(Stage target, Exception e)
//	{
//		Modal modal = new Modal(SAVE_FAILURE_MESSAGE, skin)
//		{
//			@Override
//			protected void result(Object object)
//			{
//				playClickButton();
//				if((boolean) object)
//				{
//					handleRoundRename(target);
//				}
//			};
//		};
//		modal.text(getSaveFailureReason(e));
//		modal.button("Ok", false);
//		modal.button("Umbenennen", true);
//		modal.show(target);
//	}

	protected String getSaveFailureReason(Exception e)
	{
		if(e instanceof InvalidPathException || e instanceof FileNotFoundException)
		{
			return FILENAME_INVALID_MESSAGE;
		}
		return e.toString();
	}

//	protected void handleRoundNameConflict(Stage target)
//	{
//		Modal modal = new Modal(SAVE_FAILURE_MESSAGE, skin)
//		{
//			@Override
//			protected void result(Object object)
//			{
//				playClickButton();
//				SaveDialogResult result = (SaveDialogResult) object;
//				switch (result)
//				{
//					case RENAME:
//						handleRoundRename(stage);
//						break;
//					case OVERWRITE:
//						saveRound(stage, true, false);
//						break;
//
//					case ABORT:
//					default:
//						break;
//				}
//			}
//		};
//		modal.text(createRenameInfoMessage());
//		modal.text("wie soll mit dem Problem umgegangen werden?");
//		modal.button("Überschreiben", SaveDialogResult.OVERWRITE);
//		modal.button("Umbenennen", SaveDialogResult.RENAME);
//		modal.button("Abbrechen", SaveDialogResult.ABORT);
//		modal.show(target);
//	}

//	private String createRenameInfoMessage()
//	{
//		return "Eine Runde mit dem Namen " + controller.getRound().getName() + " existiert bereits.";
//	}

//	protected void handleRoundRename(Stage target)
//	{
//		TextField roundNameField = new TextField("", skin);
//		Modal dialog = new Modal(RENAME_ROUND_TITLE, skin)
//		{
//			@Override
//			protected void result(Object object)
//			{
//				playClickButton();
//				if((boolean) object)
//				{
//					controller.getRound().setName(roundNameField.getText());
//					saveRound(target);
//				}
//			};
//		};
//		dialog.add().row();
//		dialog.getContentTable().add(new Label("Neuer Rundenname", skin));
//		dialog.getContentTable().add(roundNameField).align(Align.center).expandX().row();
//		dialog.button("Umbenennen", true);
//		dialog.button("Abbrechen", false);
//		dialog.show(target);
//	}

//	protected void handleMissingRoundName(Stage target)
//	{
//		TextField roundNameField = new TextField("", skin);
//		Modal modal = new Modal(MISSING_NAME_WARNING, skin)
//		{
//			@Override
//			protected void result(Object object)
//			{
//				playClickButton();
//				String roundName = roundNameField.getText();
//				if(roundName == null || roundName.isEmpty())
//				{
//					roundName = DATE_FORMATTER.format(LocalDateTime.now()) + "_";
//					if(controller.getRound().isTutorial())
//					{
//						roundName += "tutorial_";
//					}
//					else if(controller.getRound().isChallenge())
//					{
//						roundName += "challenge-"
//								+ Challenge.getChallengeName(controller.getChallenge().getClass(), true) + "-"
//								+ (controller.getChallenge().getId() + 1) + "_";
//					}
//					for(int i = 0; i < controller.getRound().getPlayerAmount(); i++)
//					{
//						roundName += controller.getRound().getPlayerNameAtId(i);
//						if(i != controller.getRound().getPlayerAmount() - 1)
//						{
//							roundName += "-";
//						}
//					}
//				}
//				controller.getRound().setName(roundName);
//				saveRound();
//			}
//		};
//		modal.getContentTable().add(NAME_GENERATION_WARNING).row();
//		HorizontalGroup nameGroup = new HorizontalGroup();
//		nameGroup.addActor(new Label("Rundenname: ", skin));
//		nameGroup.addActor(roundNameField);
//		modal.getContentTable().add(nameGroup).expandX().row();
//		modal.button("Ok");
//		modal.show(target);
//	}

//	protected void handleBackToScreenReturnRequest(Stage target, Class<? extends AbstractScreen> screenType)
//	{
//		Modal modal = new Modal(LEAVE_DIALOG_TITLE, skin)
//		{
//			@Override
//			protected void result(Object object)
//			{
//				playClickButton();
//				if((boolean) object)
//				{
//					try
//					{
//						AbstractScreen nextScreen = screenType.getConstructor(Navigator.class).newInstance(controller);
//						nextScreen(nextScreen);
//						if(screenType == MainMenuScreen.class)
//						{
//							controller.resetAccount();
//						}
//						controller.disposeRound();
//					}
//					catch (Exception e)
//					{
//					}
//				}
//			}
//		};
//		modal.text(UNSAVED_WARNING);
//		modal.button("Ja", true);
//		modal.button("Nein", false);
//		modal.show(target);
//	}

	protected void setFullscreen()
	{
		setFullscreen(Gdx.graphics.getDisplayMode());
	}

	protected void setFullscreen(DisplayMode mode)
	{
		Gdx.graphics.setFullscreenMode(mode);
	}

	protected void setWindowedScreen()
	{
		setWindowedScreen(1280, 720);
	}

	protected void setWindowedScreen(int width, int height)
	{
		Gdx.graphics.setWindowedMode(width, height);
	}

	@Override
	public void render(float delta)
	{
		renderUtils();
	}

	protected void renderShortcuts()
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.F))
		{
			setFullscreen();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			setWindowedScreen();
		}
	}

	protected void renderUtils()
	{
		renderShortcuts();

		viewport.apply();
		ScreenUtils.clear(Color.BLACK);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);

		if(stage != null)
		{
			stage.getViewport().update(width, height, true);
		}
	}

	private void disposeUtils()
	{
		font.dispose();
		batch.dispose();
		stage.dispose();
		isDisposed = true;
	}

	public boolean isDisposed()
	{
		return isDisposed;
	}

	@Override
	public void dispose()
	{
		/* unnecessary as disposeUtils always gets called */}

	@Override
	public void show()
	{
		/* inherited, implementation not neccessary */}

	@Override
	public void pause()
	{
		/* inherited, implementation not neccessary */}

	@Override
	public void resume()
	{
		/* inherited, implementation not neccessary */}

	@Override
	public void hide()
	{
		/* inherited, implementation not neccessary */}

//	public void playClickButtonSound()
//	{
//		try 
//		{
//			MusicPlayer.instance().playClickButton();
//		}
//		catch (IOException e)
//		{
//			System.err.println(SOUND_FILE_ERROR_MES);
//		}
//	}
//	
//	public void playPlaceSound()
//	{
//		try 
//		{
//			MusicPlayer.instance().playClickPlacement();
//		}
//		catch (IOException e)
//		{
//			System.err.println(SOUND_FILE_ERROR_MES);
//		}
//	}

	public Stage getStage()
	{
		return stage;
	}

	public Navigator getController()
	{
		return navigator;
	}

	public Skin getSkin()
	{
		return skin;
	}

	public BitmapFont getFont()
	{
		return font;
	}

}
