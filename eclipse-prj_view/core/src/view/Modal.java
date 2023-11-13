package view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Modal extends Dialog
{
	public Modal(String title, Skin skin)
	{
		super(title, skin);
		initialize();
	}

	public Modal(String title, WindowStyle windowStyle)
	{
		super(title, windowStyle);
		initialize();
	}

	public Modal(String title, Skin skin, String windowStyleName)
	{
		super(title, skin, windowStyleName);
		initialize();
	}

	private void initialize()
	{
		this.setModal(true);
		this.setMovable(false);
		this.setResizable(false);
	}
}
