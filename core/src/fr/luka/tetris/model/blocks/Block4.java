package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import fr.luka.tetris.model.Square;

/**
 * Block with 4 squares.
 * --
 * -
 * -
 */
public class Block4 extends Block {

    /**
     * Constructor.
     * Init random first position and add square to superClass.
     */
    public Block4() {
        int x;

        do {
            x = MathUtils.random(0, WINDOW_WIDTH - SQUARE_WIDTH * 2);
        } while (x % SQUARE_WIDTH != 0);

        squares.add(
                new Square(x, WINDOW_HEIGHT),
                new Square(x + SQUARE_WIDTH, WINDOW_HEIGHT),
                new Square(x, WINDOW_HEIGHT + SQUARE_WIDTH),
                new Square(x, WINDOW_HEIGHT + SQUARE_WIDTH * 2)
        );
    }
}
