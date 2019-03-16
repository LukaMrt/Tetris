package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
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
    public Block3() {

        final int width = 3;

        int x;

        do {
            x = MathUtils.random(0, WINDOW_WIDTH - SQUARE_WIDTH * width);
        } while (x % SQUARE_WIDTH != 0);

        squares.add(
                new Square(x, WINDOW_HEIGHT),
                new Square(x + SQUARE_WIDTH, WINDOW_HEIGHT),
                new Square(x + SQUARE_WIDTH * 2, WINDOW_HEIGHT)
        );
    }
}
