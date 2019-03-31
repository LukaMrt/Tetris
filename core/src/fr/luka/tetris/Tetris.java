package fr.luka.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import fr.luka.tetris.enums.BlockType;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.*;
import lombok.Getter;

import java.util.*;

/**
 * Main class of programme.
 */
public class Tetris extends ApplicationAdapter {

    /**
     * Height of the window.
     */
    @Getter
    private static int windowHeight;

    /**
     * Width of the window.
     */
    @Getter
    private static int windowWidth;

    /**
     * Game batch.
     */
    private SpriteBatch batch;

    /**
     * List of game squares.
     */
    private Array<Square> gameSquares;

    /**
     * Current block falling.
     */
    private Block fallBlock;

    /**
     * Last time block fell.
     */
    private long lastBlockFall;

    /**
     * Last time down pressed.
     */
    private long coolDownMove;

    /**
     * Last time right/left pressed.
     */
    private long coolDownFall;

    /**
     * Block factory which create blocks.
     */
    private BlockFactory blockFactory;

    /**
     * Time at game start in millis.
     */
    private final long timeStart;

    /**
     * Constructor.
     *
     * @param height : height of the window.
     * @param width  : width of the window.
     */
    public Tetris(final int height, final int width) {
        windowHeight = height;
        windowWidth = width;
        timeStart = TimeUtils.millis();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameSquares = new Array<>();
        blockFactory = new BlockFactory();
        lastBlockFall = TimeUtils.millis();
    }

    @Override
    public void render() {
        final float red = 0.8f;
        final float green = 0.8f;
        final float blue = 0.8f;
        Gdx.gl.glClearColor(red, green, blue, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBlock();

        double fallCoolDown = -0.005 * (TimeUtils.millis() - timeStart) + 1_000;
        fallCoolDown = fallCoolDown < 300 ? 300 : fallCoolDown;

        if (TimeUtils.millis() >= lastBlockFall + fallCoolDown ) {
            fallBlock();
            lastBlockFall = TimeUtils.millis();
        }

        updateBlock();

        batch.begin();

        gameSquares.forEach(square
                -> batch.draw(square.getTexture(),
                square.getRectangle().x,
                square.getRectangle().y));

        fallBlock.getSquares().forEach(square
                -> batch.draw(square.getTexture(),
                square.getRectangle().x,
                square.getRectangle().y));

        batch.end();

        checksRows();

        checkInput();

    }

    @Override
    public void dispose() {
        batch.dispose();
        gameSquares.forEach(square -> square.getTexture().dispose());
        fallBlock.getSquares().forEach(square -> square.getTexture().dispose());
    }

    /**
     * Update the fallBlock with randomBlock.
     */
    private void updateBlock() {

        if (fallBlock == null) {

            fallBlock = blockFactory.getBlock(BlockType.getRandomType());

            lastBlockFall = TimeUtils.millis();

        }

    }

    /**
     * Fall the Block.
     */
    private void fallBlock() {

        if (fallBlock.fall(gameSquares)) {

            fallBlock.getSquares().forEach(square -> gameSquares.add(square));

            fallBlock = null;

        }

    }

    /**
     * Check if some keys or pressed and do actions.
     */
    private void checkInput() {

        final int moveCoolDown = 200;

        if (TimeUtils.millis() > coolDownMove + moveCoolDown) {

            if (Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                fallBlock.move(Direction.LEFT, gameSquares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                fallBlock.move(Direction.RIGHT, gameSquares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                fallBlock.turn(gameSquares);
                coolDownMove = TimeUtils.millis();
            }

        }

        final int fallCoolDown = 75;

        if (TimeUtils.millis() > coolDownFall + fallCoolDown) {

            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                fallBlock();
                coolDownFall = TimeUtils.millis();
            }

        }

    }

    /**
     * Check if rows are full and remove them.
     */
    private void checksRows() {

        Map<Float, Block> map = new TreeMap<>(Float::compare);

        // Put rows with number of square in map
        gameSquares.forEach(square -> {
            float y = square.getRectangle().getY();

            if (!map.containsKey(y)) {
                map.put(y, blockFactory.getBlock(BlockType.BLOCKNULL));
            }

            map.get(y).getSquares().add(square);
        });

        map.forEach((y, block) -> {


            if (block.getSquares().size != windowWidth / Square.SIZE) return;

            block.getSquares().forEach(square -> square.getTexture().dispose());
            gameSquares.removeAll(block.getSquares(), true);

            map.keySet().stream().filter(key -> !key.equals(y)).forEach(key -> {
                boolean fallEnd;
                do {
                    fallEnd = map.get(key).fall(gameSquares);
                } while (!fallEnd);
            } );

        });

    }

}
