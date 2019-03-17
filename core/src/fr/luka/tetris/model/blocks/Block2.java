package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
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
    public Block2(String texturePath) {
        int x;

        do {
            x = MathUtils.random(0, WINDOW_WIDTH - SQUARE_SIZE);
        } while (x % SQUARE_SIZE != 0);

        Array<Rectangle> array = new Array<>();

        array.add(new Rectangle(x, WINDOW_HEIGHT, SQUARE_SIZE, SQUARE_SIZE));

        super.create(array, texturePath);
    }

}
