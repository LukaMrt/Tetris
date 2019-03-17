package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import fr.luka.tetris.enums.BlockType;

public class BlockFactory {

    private Array<String> paths = new Array<String>();

    public BlockFactory() {
        paths.add("core/assets/blueSquare.png",
                "core/assets/graySquare.png,",
                "core/assets/greenSquare.png",
                "core/assets/redSquare.png");
    }

    public Block getBlock(BlockType blockType) {

        int index = MathUtils.random(0, paths.size - 1);

        switch (blockType) {

            case BLOCK1:
                return new Block1(paths.get(index));

            case BLOCK2:
                return new Block2(paths.get(index));

            case BLOCK3:
                return new Block3(paths.get(index));

            case BLOCK4:
                return new Block4(paths.get(index));

            default:
                return new Block1(paths.get(index));

        }

    }

}
