package src;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import view.Navigator;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("KP-Games");
		new Lwjgl3Application(new Navigator(), config);
	}
}
