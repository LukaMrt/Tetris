package fr.luka.tetris.model;

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
     * Rectangle with size 32/32.
     * Represents the location of the square.
     */
    @Getter @Setter
    private Rectangle rectangle;

    /**
     * Height of a square.
     */
    public static final int HEIGHT = 32;

    /**
     * Width of a square.
     */
    public static final int WIDTH = 32;

    /**
     * Constructor.
     * @param x : x location of the square.
     * @param y : y location of the square.
     */
    public Square(final int x, final int y) {
        rectangle = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    /**
     * Equals method override.
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
        rectangle.setY(origin - HEIGHT);

        gameSquares.forEach(square -> {
            if (rectangle.overlaps(square.getRectangle())
                    && !square.compare(this)) {
                rectangle.setY(origin);
            }
        });

    }

}
