package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import fr.luka.tetris.model.Square;

public class Block3 extends Block {

    public Block3() {
        super();
        int x;
        do {
            x = MathUtils.random(0, 512 - 32 * 3);
        } while (x % 32 != 0);
        squares.add(new Square(x, 800),
                new Square(x + 32, 800),
                new Square(x + 32 + 32, 800));
    }
}
