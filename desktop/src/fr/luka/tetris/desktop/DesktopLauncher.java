package fr.luka.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.luka.tetris.Tetris;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = false;
		config.height = 800;
		config.width = 512;
		config.resizable = false;
		config.title = "Tetris";
		new LwjglApplication(new Tetris(), config);
	}

}
