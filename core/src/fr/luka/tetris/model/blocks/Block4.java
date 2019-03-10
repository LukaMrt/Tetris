package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import fr.luka.tetris.model.Square;

public class Block4 extends Block {

    public Block4() {
        super();
        int x;
        do {
            x = MathUtils.random(0, 512 - 32 * 2);
        } while (x % 32 != 0);
        squares.add(new Square(x, 800),
                new Square(x + 32, 800),
                new Square(x, 800 + 32),
                new Square(x, 800 + 32 + 32));
    }
}
