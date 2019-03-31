package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import fr.luka.tetris.model.Square;

/**
 * Block with 4 squares.
 * --
 * --
 */
public class Block1 extends Block {

    /**
     * Constructor.
     * Init random first position and add square to superClass.
     *
     * @param texturePath : path to the texture.
     */
    public Block1(String texturePath) {

        int x;

        do {
            x = MathUtils.random(0, WINDOW_WIDTH - SQUARE_SIZE * 2);
        } while (x % SQUARE_SIZE != 0);

        squares.addAll(new Square(new Rectangle(x, WINDOW_HEIGHT, SQUARE_SIZE, SQUARE_SIZE), texturePath),
                new Square(new Rectangle(x, WINDOW_HEIGHT + SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE), texturePath),
                new Square(new Rectangle(x + SQUARE_SIZE, WINDOW_HEIGHT, SQUARE_SIZE, SQUARE_SIZE), texturePath),
                new Square(new Rectangle(x + SQUARE_SIZE, WINDOW_HEIGHT + SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE), texturePath)
        );

    }

}
