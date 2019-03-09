package fr.luka.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.TimeUtils;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.Block;
import fr.luka.tetris.model.blocks.Block1;
import fr.luka.tetris.model.blocks.Block3;
import fr.luka.tetris.model.blocks.Block4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Tetris extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture square;

	private Array<Square> squares;

	private Block block;
	private long lastBlockFall;
    private long coolDownMove;
    private long coolDownFall;

	@Override
	public void create () {
		batch = new SpriteBatch();
		square = new Texture(Gdx.files.internal("core/assets/square.png"));
		squares = new Array<Square>();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBlock();
        checksRows();

		batch.begin();

		for (Square square : squares) {
			batch.draw(this.square, square.getRectangle().x, square.getRectangle().y);
		}

		for (Square square : block.getSquares()) {
			batch.draw(this.square, square.getRectangle().x, square.getRectangle().y);
		}

        if (TimeUtils.millis() >= lastBlockFall + /* 1000 */ 1000) {
            fallBlock();
            lastBlockFall = TimeUtils.millis();
        }

		batch.end();

        checkInput();

	}

    @Override
	public void dispose () {
		batch.dispose();
		square.dispose();
	}

    private void fallBlock() {
        if (block.fall(squares)) {
            for (Square square : block.getSquares()) {
                squares.add(square);
            }
            block = null;
        }
    }

    private void updateBlock() {
        if (block == null) {
            block = new Block4();
            lastBlockFall = TimeUtils.millis();
        }
    }

    private void checkInput() {

        if (TimeUtils.millis() > coolDownMove + 200) {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                block.move(Direction.LEFT, squares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                block.move(Direction.RIGHT, squares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                block.turn(squares);
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

        Sort.instance().sort(squares, (o1, o2) -> Float.compare(01, 02));

        HashMap<Float, Integer> map = new HashMap<>();

        for (Square square : squares) {

            float y = square.getRectangle().getY();

            if (!map.containsKey(y)) {
                map.put(y, 0);
            }

            map.replace(y, map.get(y) + 1);

        }

        for (Map.Entry<Float, Integer> entry : map.entrySet()) {

            if (entry.getValue().equals(16)) {

                squares.select(square -> square.getRectangle().getY() == entry.getValue()).forEach(square -> squares.removeIndex(squares.indexOf(square, false)));

                for (Square square : squares) {
                    square.update(squares);
                }

            }

        }

    }

}
