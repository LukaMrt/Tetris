package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import fr.luka.tetris.model.Square;

/**
 * Block with 1 square.
 * -
 */
public class Block2 extends Block {

    /**
     * Constructor.
     * Init random first position and add square to superClass.
     */
    public Block2() {
        int x;

        do {
            x = MathUtils.random(0, WINDOW_WIDTH - SQUARE_WIDTH);
        } while (x % SQUARE_WIDTH != 0);

        squares.add(new Square(x, WINDOW_HEIGHT));
    }

}
