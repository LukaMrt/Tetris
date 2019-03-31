package fr.luka.tetris.enums;

import com.badlogic.gdx.math.MathUtils;

/**
 * Enumeration with the different blocks.
 */
public enum BlockType {

    /**
     * Block with 4 squares.
     * --
     * --
     */
    BLOCK1,

    /**
     * Block with 1 square.
     * -
     */
    BLOCK2,

    /**
     * Block with 3 squares.
     * ---
     */
    BLOCK3,

    /**
     * Block with 4 squares.
     * --
     * -
     * -
     */
    BLOCK4,

    /**
     * An empty block.
     */
    BLOCKNULL;

    /**
     * Get a random BlockType.
     * @return a random BlockType.
     */
    public static BlockType getRandomType() {

        switch (MathUtils.random(0, 3)) {

            case 0:
                return BLOCK1;

            case 1:
                return BLOCK2;

            case 2:
                return BLOCK3;

            case 3:
                return BLOCK4;

            default:
                return BLOCKNULL;

        }

    }

}
