package fr.luka.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.TimeUtils;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tetris extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture square;

	private Array<Square> gameSquares;

	private Block fallBlock;

	private long lastBlockFall;
    private long coolDownMove;
    private long coolDownFall;

	@Override
	public void create () {
		batch = new SpriteBatch();
		square = new Texture(Gdx.files.internal("core/assets/square.png"));
		gameSquares = new Array<>();
        lastBlockFall = TimeUtils.millis();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBlock();

        if (TimeUtils.millis() >= lastBlockFall + 1000) {
            fallBlock();
            lastBlockFall = TimeUtils.millis();
        }

        updateBlock();

        checksRows();

		batch.begin();

		for (Square square : gameSquares) {
			batch.draw(this.square, square.getRectangle().x, square.getRectangle().y);
		}

		for (Square square : fallBlock.getSquares()) {
			batch.draw(this.square, square.getRectangle().x, square.getRectangle().y);
		}

		batch.end();

        checkInput();

	}

    private void updateBlock() {
        if (fallBlock == null) {

            switch (MathUtils.random(0, 3)) {

                case 0:
                    fallBlock = new Block1();
                    break;

                case 1:
                    fallBlock = new Block2();
                    break;

                case 2:
                    fallBlock = new Block3();
                    break;

                case 3:
                    fallBlock = new Block4();
                    break;

            }

            lastBlockFall = TimeUtils.millis();

        }

    }

    @Override
	public void dispose () {
		batch.dispose();
		square.dispose();
	}

    private void fallBlock() {

        if (fallBlock.fall(gameSquares)) {

            for (Square square : fallBlock.getSquares()) {
                gameSquares.add(square);
            }

            fallBlock = null;

        }

    }

    private void checkInput() {

        if (TimeUtils.millis() > coolDownMove + 200) {

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

        if (TimeUtils.millis() > coolDownFall + 75) {

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                fallBlock();
                coolDownFall = TimeUtils.millis();
            }

        }

    }

    private void checksRows() {

	    // Sort to have same rows next
        Sort.instance().sort(gameSquares, (o1, o2) -> Float.compare(o1.getRectangle().getY(), o2.getRectangle().getY()));

        HashMap<Float, Integer> map = new HashMap<>();

        // Put rows with number of square in map
        for (Square square : gameSquares) {

            float y = square.getRectangle().getY();

            if (!map.containsKey(y)) {
                map.put(y, 0);
            }

            map.replace(y, map.get(y) + 1);

        }

        for (Map.Entry<Float, Integer> entry : map.entrySet()) {

            // If line full, remove each square of this line
            if (entry.getValue().equals(16)) {

                for (Iterator<Square> iterator = gameSquares.iterator(); iterator.hasNext(); ) {
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
