package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import lombok.Getter;


public abstract class Block {

    @Getter
    protected Array<Square> squares;

    protected Square center;

    public Block() {
        squares = new Array<Square>();
    }

    public boolean fall(Array<Square> gameSquares) {

        boolean isFallEnd = false;

        for (Square square : squares) {

            square.getRectangle().setY(square.getRectangle().getY() - 32);

            if (square.getRectangle().y < 0) {
                isFallEnd = true;
            }

            for (Square square1 : gameSquares) {
                if (square.getRectangle().overlaps(square1.getRectangle())) {
                    isFallEnd = true;
                }
            }

        }

        if (isFallEnd) {
            for (Square square : squares) {
                square.getRectangle().setY(square.getRectangle().getY() + 32);
            }
        }

        return isFallEnd;

    }

    public void move(Direction direction) {

        boolean cantMove = false;

        for (Square square : squares) {

            square.getRectangle().setX(square.getRectangle().getX() + (direction == Direction.LEFT ? -32 : 32));

            float x = square.getRectangle().getX();

            if (x < 0 || x > 512 - 32) {
                cantMove = true;
            }

        }

        if (cantMove) {
            for (Square square : squares) {
                square.getRectangle().setX(square.getRectangle().getX() + (direction == Direction.LEFT ? 32 : -32));
            }
        }

    }

    public void turn() {

        boolean cantTurn = false;

        for (Square square : squares) {



        }

    }

}
