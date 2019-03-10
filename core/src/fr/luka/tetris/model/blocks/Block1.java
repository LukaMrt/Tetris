package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import fr.luka.tetris.model.Square;

public class Block1 extends Block {

    public Block1() {
        super();
        int x;
        do {
            x = MathUtils.random(0, 512 - 64);
        } while (x % 32 != 0);
        squares.add(new Square(x, 800),
                new Square(x, 800 + 32),
                new Square(x + 32, 800),
                new Square(x + 32, 800 + 32));
    }

}
