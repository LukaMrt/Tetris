package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.TimeUtils;
import fr.luka.tetris.Tetris;
import fr.luka.tetris.enums.Direction;
import fr.luka.tetris.model.Square;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract class of a block.
 */
public abstract class Block {

    /**
     * Height of the Window.
     */
    protected final int WINDOW_HEIGHT = Tetris.getWindowHeight();

    /**
     * Width of the Window.
     */
    protected final int WINDOW_WIDTH = Tetris.getWindowWidth();

    /**
     * Height of a square.
     */
    protected final int SQUARE_SIZE = Square.SIZE;

    /**
     * Array of squares which compose the block.
     */
    @Getter
    protected Array<Square> squares = new Array<>();

    @Getter
    private double lastFall;

    /**
     * Move the block.
     * @param direction : RIGHT or LEFT, direction of the move.
     * @param gameSquares : list of all squares in the game.
     */
    public void move(final Direction direction, final Array<Square> gameSquares) {

        AtomicBoolean cantMove = new AtomicBoolean(false);

        squares.forEach(square -> {

            square.getRectangle().setX(
                    square.getRectangle().getX()
                            + (direction == Direction.LEFT
                            ? -SQUARE_SIZE
                            : SQUARE_SIZE));

            if (cancelMove(square, gameSquares)) {
                cantMove.set(true);
            }

        });

        if (cantMove.get()) {
            squares.forEach(square -> square.getRectangle().setX(
                    square.getRectangle().getX()
                            + (direction == Direction.LEFT
                            ? SQUARE_SIZE
                            : -SQUARE_SIZE)));
        }

    }

    /**
     * Turn the block.
     * @param gameSquares : list of al squares in the game.
     */
    public void turn(final Array<Square> gameSquares) {

        Array<Square> rowsSort = new Array<>(squares);

        Sort.instance().sort(rowsSort, (o1, o2)
                -> Float.compare(o1.getRectangle().getY(), o2.getRectangle().getY()));

        Array<Square> colSort = new Array<>(squares);

        Sort.instance().sort(colSort, (o1, o2)
                -> Float.compare(o1.getRectangle().getX(), o2.getRectangle().getX()));

        Array<Float> rows = new Array<>();

        squares.forEach(square -> {
            float y = square.getRectangle().getY();
            if (!rows.contains(y, false)) {
                rows.add(square.getRectangle().getY());
            }
        });

        float originX = colSort.get(0).getRectangle().getX();
        float originY = rowsSort.get(0).getRectangle().getY();

        AtomicBoolean cantTurn = new AtomicBoolean(false);

        Map<Square, Rectangle> map = new HashMap<>();

        squares.forEach(square -> {
            map.put(square, new Rectangle(square.getRectangle()));

            float row = (square.getRectangle().getY() - originY) / SQUARE_SIZE;

            float newRow = (square.getRectangle().getX() - originX) / SQUARE_SIZE;
            float newColumn = rows.size - (row + 1);

            square.getRectangle().setY(originY + (newRow * SQUARE_SIZE));
            square.getRectangle().setX(originX + (newColumn * SQUARE_SIZE));

            if (cancelMove(square, gameSquares)) {
                cantTurn.set(true);
            }
        });

        if (cantTurn.get()) {
            squares.forEach(square -> square.setRectangle(map.get(square)));
        }

    }

    /**
     * Fall the block of 1 square height.
     * @param gameSquares : list of all squares in the game;
     * @return true if cant fall, false else.
     */
    public Block fall(final Array<Square> gameSquares) {

        this.lastFall = TimeUtils.millis();

        AtomicBoolean isFallEnd = new AtomicBoolean(false);

        squares.forEach(square -> {

            square.getRectangle().setY(square.getRectangle().getY() - SQUARE_SIZE);

            if (square.getRectangle().getY() < 0) {
                isFallEnd.set(true);
            }

            gameSquares.forEach(square1 -> {
                if (square != square1 && square.getRectangle().overlaps(square1.getRectangle())) {
                    isFallEnd.set(true);
                }
            });

        });

        if (isFallEnd.get()) {

            Sort.instance().sort(squares, (o1, o2) -> Float.compare(o1.getRectangle().getY(), o2.getRectangle().getY()));

            if (squares.get(squares.size - 1).getRectangle().getY() + SQUARE_SIZE > 800) {
                Gdx.app.exit();
            }

            squares.forEach(
                    square -> square.getRectangle().setY(square.getRectangle().getY() + SQUARE_SIZE));
        }

        if (isFallEnd.get()) {
            gameSquares.addAll(this.squares);
            return null;
        }

        return this;

    }

    /**
     * Check if the move is possible (move or turn).
     * @param square : the current square.
     * @param gameSquares : list of all squares in the game.
     * @return true if the move is impossible, false else.
     */
    private boolean cancelMove(final Square square, final Array<Square> gameSquares) {

        float x = square.getRectangle().getX();

        if (x < 0 || WINDOW_WIDTH <= x) {
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
