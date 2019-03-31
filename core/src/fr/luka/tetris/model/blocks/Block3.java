package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import fr.luka.tetris.model.Square;

/**
 * Block with 3 squares.
 * ---
 */
public class Block3 extends Block {

    /**
     * Constructor.
     * Init random first position and add square to superClass.
     */
    public Block3(String texturePath) {

        final int width = 3;

        int x;

        do {
            x = MathUtils.random(0, WINDOW_WIDTH - SQUARE_SIZE * width);
        } while (x % SQUARE_SIZE != 0);

        squares.addAll(
                new Square (new Rectangle(x, WINDOW_HEIGHT, SQUARE_SIZE, SQUARE_SIZE), texturePath),
                new Square (new Rectangle(x + SQUARE_SIZE, WINDOW_HEIGHT, SQUARE_SIZE, SQUARE_SIZE), texturePath),
                new Square (new Rectangle(x + SQUARE_SIZE * 2, WINDOW_HEIGHT, SQUARE_SIZE, SQUARE_SIZE), texturePath)
        );

    }
}
