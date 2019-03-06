package fr.luka.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import fr.luka.tetris.model.Square;
import fr.luka.tetris.model.blocks.Block;

public class Tetris extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture square;

	private Array<Square> squares;
	private Block block;

	@Override
	public void create () {
		batch = new SpriteBatch();
		square = new Texture(Gdx.files.internal("square.png"));
		squares = new Array<Square>();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for (Square square : squares) {
			batch.draw(this.square, square.getRectangle().x, square.getRectangle().y);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		square.dispose();
	}

}
