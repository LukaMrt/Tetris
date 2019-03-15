package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class Block {

    @Getter
    protected Array<Square> squares;

    protected Block() {
        squares = new Array<>();
    }

    public void move(Direction direction, Array<Square> gameSquares) {

        boolean cantMove = false;

        for (Square square : squares) {

            square.getRectangle().setX(square.getRectangle().getX() + (direction == Direction.LEFT ? -32 : 32));

            if (cancelMove(square, gameSquares)) {
                cantMove = true;
            }

        }

        if (cantMove) {
            for (Square square : squares) {
                square.getRectangle().setX(square.getRectangle().getX() + (direction == Direction.LEFT ? 32 : -32));
            }
        }

    }

    public void turn(Array<Square> gameSquares) {

        Array<Square> rowsSort = new Array<>(squares);

        Sort.instance().sort(rowsSort, (o1, o2) -> {
            if (o1.getRectangle().getY() < o2.getRectangle().getY()) {
                return -1;
            } else {
                return 1;
            }
        });

        Array<Square> colSort = new Array<>(squares);

        Sort.instance().sort(colSort, (o1, o2) -> {
            if (o1.getRectangle().getX() < o2.getRectangle().getX()) {
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

        float originX = colSort.get(0).getRectangle().getX();
        float originY = rowsSort.get(0).getRectangle().getY();

        boolean cantTurn = false;

        Map<Square, Rectangle> map = new HashMap<>();

        for (Square square : squares) {

            map.put(square, new Rectangle(square.getRectangle()));

            float row = (square.getRectangle().getY() - originY) / 32;
            float column = (square.getRectangle().getX() - originX) / 32;

            float newRow = column;
            float newColumn = rows.size - (row + 1);

            square.getRectangle().setY(originY + (newRow * 32));
            square.getRectangle().setX(originX + (newColumn * 32));

            if (cancelMove(square, gameSquares)) {
                cantTurn = true;
            }

        }

        if (cantTurn) {

            for (Square square : squares ) {

                square.setRectangle(map.get(square));

            }

        }

    }

    public boolean fall(Array<Square> gameSquares) {

        AtomicBoolean isFallEnd = new AtomicBoolean(false);

        squares.forEach(square -> {

            square.getRectangle().setY(square.getRectangle().getY() - 32);

            if (square.getRectangle().y < 0) {
                isFallEnd.set(true);
            }

            for (Square square1 : gameSquares) {
                if (square.getRectangle().overlaps(square1.getRectangle())) {
                    isFallEnd.set(true);
                }
            }

        });

        if (isFallEnd.get()) {
            for (Square square : squares) {
                square.getRectangle().setY(square.getRectangle().getY() + 32);
            }
        }

        return isFallEnd.get();

    }

    private boolean cancelMove(Square square, Array<Square> gameSquares) {

        float x = square.getRectangle().getX();

        if (x < 0 || x >= 512) {
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
