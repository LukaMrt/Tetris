package fr.luka.tetris.model;

import com.badlogic.gdx.utils.Array;
import fr.luka.tetris.Tetris;
import fr.luka.tetris.enums.BlockType;
import fr.luka.tetris.model.blocks.Block;
import fr.luka.tetris.model.blocks.BlockFactory;

import java.util.Map;
import java.util.TreeMap;

public class RowsChecker {

    private final Array<Square> gameSquares;

    public RowsChecker(Array<Square> gameSquares) {
        this.gameSquares = gameSquares;
    }

    public void check() {

        Map<Float, Block> map = new TreeMap<>(Float::compare);

        // Put rows with number of square in map
        gameSquares.forEach(square -> {
            float y = square.getRectangle().getY();

            if (!map.containsKey(y)) {
                map.put(y, new BlockFactory().getBlock(BlockType.BLOCKNULL));
            }

            map.get(y).getSquares().add(square);
        });

        map.forEach((y, block) -> {


            if (block.getSquares().size != Tetris.getWindowWidth() / Square.SIZE) return;

            block.getSquares().forEach(square -> square.getTexture().dispose());
            gameSquares.removeAll(block.getSquares(), true);

            map.keySet().stream().filter(key -> !key.equals(y)).forEach(key -> {
                Block fallEnd;
                do {
                    fallEnd = map.get(key).fall(gameSquares);
                } while (fallEnd != null);
            } );

        });

    }

}
