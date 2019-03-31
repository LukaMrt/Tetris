package fr.luka.tetris.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

/**
 * Square class.
 * Represents au square in the game.
 */
public class Square {

    /**
     * Width of a square.
     */
    public static final int SIZE = 32;

    /**
     * Rectangle with size 32/32.
     * Represents the location of the square.
     */
    @Getter @Setter
    private Rectangle rectangle;

    /**
     * Textures of the square
     */
    @Getter
    private final Texture texture;

    /**
     * Constructor.
     * @param rectangle : the rectangle of the square.
     */
    public Square(Rectangle rectangle, String texturePath) {
        this.rectangle = rectangle;
        this.texture = new Texture(Gdx.files.internal(texturePath));
    }


}
