package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import fr.luka.tetris.enums.BlockType;

/**
 * Block Factory.
 * Class which create .locks.
 */
public class BlockFactory {

    /**
     * Array which contains the textures paths.
     */
    private Array<String> paths = new Array<>();

    /**
     * Constructor.
     * Init the textures paths.
     */
    public BlockFactory() {
        paths.add("blueSquare.png",
                "graySquare.png",
                "greenSquare.png",
                "redSquare.png");
    }

    /**
     * Get a block from a blockType.
     * @param blockType the type of the block
     * @return Block, the block associated with blockType.
     */
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
                return new EmptyBlock();

        }

    }

}
