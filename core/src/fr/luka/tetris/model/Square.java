package fr.luka.tetris.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
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

    /**
     * Compare location of an other Square.
     * @param otherSquare : an other square to compare with this.
     * @return true if the square location is same as other square, false else.
     */
    private boolean compare(final Square otherSquare) {

        return (rectangle.getX() == otherSquare.getRectangle().getX())
                && (rectangle.getY() == otherSquare.getRectangle().getY());

    }

    /**
     * Fall the square and check if it is possible, cancel if it isn't.
     * @param gameSquares : all squares in the game.
     */
    public void update(final Array<Square> gameSquares) {

        float origin = rectangle.getY();
        rectangle.setY(origin - SIZE);

        gameSquares.forEach(square -> {
            if (rectangle.overlaps(square.getRectangle())
                    && !square.compare(this)) {
                rectangle.setY(origin);
            }
        });

    }

}
