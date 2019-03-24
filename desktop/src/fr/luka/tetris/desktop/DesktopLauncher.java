package fr.luka.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.luka.tetris.Tetris;

/**
 * Main class.
 */
public final class DesktopLauncher {

    /**
     * Height of the window.
     */
    private static final int HEIGHT = 800;

    /**
     * Width of the window.
     */
    private static final int WIDTH = 512;

    /**
     * Main method.
     * init properties of the window
     * @param args args
     */
    public static void main(final String[] args) {

        LwjglApplicationConfiguration config;

        config = new LwjglApplicationConfiguration();

        config.useGL30 = false;
        config.height = HEIGHT;
        config.width = WIDTH;
        config.resizable = false;
        config.title = "Tetris";

        new LwjglApplication(new Tetris(HEIGHT, WIDTH), config);

    }

    /**
     * Private constructor.
     */
    private DesktopLauncher() { }

}
