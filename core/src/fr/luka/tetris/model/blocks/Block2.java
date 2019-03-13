package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import fr.luka.tetris.model.Square;

public class Block2 extends Block {

    public Block2() {
        super();
        int x;
        do {
            x = MathUtils.random(0, 512 - 32);
        } while (x % 32 != 0);
        squares.add(new Square(x, 800));
    }

}
