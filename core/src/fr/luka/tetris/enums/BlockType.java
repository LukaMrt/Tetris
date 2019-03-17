package fr.luka.tetris.enums;

import com.badlogic.gdx.math.MathUtils;

public enum BlockType {

    BLOCK1,
    BLOCK2,
    BLOCK3,
    BLOCK4;

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
                return BLOCK1;

        }

    }

}
