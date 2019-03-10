package fr.luka.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.TimeUtils;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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

            switch (MathUtils.random(0, 3)) {

                case 0:
                    block = new Block1();
                    break;

                case 1:
                    block = new Block2();
                    break;

                case 2:
                    block = new Block3();
                    break;

                case 3:
                    block = new Block4();
                    break;

            }

            lastBlockFall = TimeUtils.millis();
        }
    }

    private void checkInput() {

        if (TimeUtils.millis() > coolDownMove + 200) {

            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                block.move(Direction.LEFT, squares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                block.move(Direction.RIGHT, squares);
                coolDownMove = TimeUtils.millis();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                block.turn(squares);
                coolDownMove = TimeUtils.millis();
            }

        }

        if (TimeUtils.millis() > coolDownFall + 75) {

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                fallBlock();
                coolDownFall = TimeUtils.millis();
            }

        }

    }

    private void checksRows() {

        Sort.instance().sort(squares, (o1, o2) -> Float.compare(o1.getRectangle().getY(), o2.getRectangle().getY()));

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

                System.out.println("ok");

                for (Iterator<Square> iterator = squares.iterator(); iterator.hasNext(); ) {
                    Square square = iterator.next();
                    if (square.getRectangle().getY() == entry.getKey()) {
                        iterator.remove();
                    }
                }

                for (int i = 0; i < squares.size; i++) {
                    Square square = squares.get(i);
                    square.update(squares);
                }

            }

        }

    }

}
