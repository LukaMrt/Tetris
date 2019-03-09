package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import lombok.Getter;

import java.util.Comparator;


public abstract class Block {

    @Getter
    protected Array<Square> squares;

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

    public void move(Direction direction, Array<Square> gameSquares) {

        boolean cantMove = false;

        for (Square square : squares) {

            square.getRectangle().setX(square.getRectangle().getX() + (direction == Direction.LEFT ? -32 : 32));

            cantMove = cancelMove(square, gameSquares);

        }

        if (cantMove) {
            for (Square square : squares) {
                square.getRectangle().setX(square.getRectangle().getX() + (direction == Direction.LEFT ? 32 : -32));
            }
        }

    }

    public void turn(Array<Square> gameSquares) {

        Sort.instance().sort(squares, (o1, o2) -> {
            if (o1.getRectangle().getX() < o2.getRectangle().getX() && o1.getRectangle().getY() < o2.getRectangle().getY()) {
                return -1;
            } else {
                return 1;
            }
        });

        Array<Float> rows = new Array<>();

        for (Square square : squares) {
            float y = square.getRectangle().getY();
            if (!rows.contains(y, false)) {
                rows.add(square.getRectangle().getY());
            }
        }

        Array<Float> columns = new Array<>();

        for (Square square : squares) {
            float x = square.getRectangle().getX();
            if (!rows.contains(x, false)) {
                rows.add(square.getRectangle().getX());
            }
        }

        float moyenX = columns.size % 2 == 0 ? columns.size / 2 : columns.size / 2 - 0.5f;
        float moyenY = rows.size % 2 == 0 ? rows.size / 2 : rows.size / 2 - 0.5f;

        float originX = squares.get(0).getRectangle().getX() + (moyenX * 32);
        float originY = squares.get(0).getRectangle().getY() + (moyenY * 32);

        boolean cantTurn = false;

        for (Square square : squares) {

            float row = (square.getRectangle().getY() - originY) / 32;
            float column = (square.getRectangle().getX() - originX) / 32;

            float newRow = (column * 32 + originY);
            float newColumn = ((columns.size - (row + 1)) * 32 + originX);

            square.getRectangle().setY(newRow);
            square.getRectangle().setX(newColumn);

            cantTurn = cancelMove(square, gameSquares);

        }

        if (cantTurn) {

            for (Square square : squares ) {



            }

        }

    }

    private boolean cancelMove(Square square, Array<Square> gameSquares) {

        float x = square.getRectangle().getX();

        if (x < 0 || x > 512 - 32) {
            return true;
        }

        for (Square square1 : gameSquares) {
            if (square.getRectangle().overlaps(square1.getRectangle())) {
                return true;
            }
        }

        return false;

    }

}
