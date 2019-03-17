package fr.luka.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.TimeUtils;
import fr.luka.tetris.enums.BlockType;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
     * Constructor.
     *
     * @param height : height of the window.
     * @param width  : width of the window.
     */
    public Tetris(final int height, final int width) {
        windowHeight = height;
        windowWidth = width;
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

        final int fallCoolDown = 1000;

        if (TimeUtils.millis() >= lastBlockFall + fallCoolDown) {
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

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                fallBlock.move(Direction.LEFT, gameSquares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                fallBlock.move(Direction.RIGHT, gameSquares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                fallBlock.turn(gameSquares);
                coolDownMove = TimeUtils.millis();
            }

        }

        final int fallCoolDown = 75;

        if (TimeUtils.millis() > coolDownFall + fallCoolDown) {

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                fallBlock();
                coolDownFall = TimeUtils.millis();
            }

        }

    }

    /**
     * Check if rows are full and remove them.
     */
    private void checksRows() {

        // Sort to have same rows next
        Sort.instance().sort(gameSquares, (o1, o2)
                -> Float.compare(o1.getRectangle().getY(),
                o2.getRectangle().getY()));

        HashMap<Float, Integer> map = new HashMap<>();

        // Put rows with number of square in map
        gameSquares.forEach(square -> {
            float y = square.getRectangle().getY();

            if (!map.containsKey(y)) {
                map.put(y, 0);
            }

            map.replace(y, map.get(y) + 1);
        });

        for (Map.Entry<Float, Integer> entry : map.entrySet()) {

            if (entry.getValue().equals(windowWidth / Square.SIZE)) {

                for (Iterator<Square> iterator = gameSquares.iterator(); iterator.hasNext();) {
                    Square square = iterator.next();
                    if (square.getRectangle().getY() == entry.getKey()) {
                        iterator.remove();
                    }
                }

                for (int i = 0; i < gameSquares.size; i++) {
                    gameSquares.get(i).update(gameSquares);
                }

                checksRows();
                break;

            }

        }

    }

}
