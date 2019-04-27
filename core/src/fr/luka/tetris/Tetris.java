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
import fr.luka.tetris.model.RowsChecker;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.*;
import lombok.Getter;

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
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        double actualCoolDown = -0.005 * (TimeUtils.millis() - timeStart) + 1_000;
        actualCoolDown = actualCoolDown < 300 ? 300 : actualCoolDown;

        if (this.fallBlock != null && TimeUtils.millis() >= this.fallBlock.getLastFall() + actualCoolDown ) {
            this.fallBlock = this.fallBlock.fall(this.gameSquares);
        }

        if (fallBlock == null) {
            fallBlock = blockFactory.getBlock(BlockType.getRandomType());
        }

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

        new RowsChecker(this.gameSquares).check();

        checkInput();

    }

    @Override
    public void dispose() {
        batch.dispose();
        gameSquares.forEach(square -> square.getTexture().dispose());
        fallBlock.getSquares().forEach(square -> square.getTexture().dispose());
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

        if (TimeUtils.millis() > coolDownFall + 75) {

            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.fallBlock = this.fallBlock.fall(this.gameSquares);
                coolDownFall = TimeUtils.millis();
            }

        }

    }

}
