package fr.luka.tetris.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

public class Square {

    @Getter
    @Setter
    private Rectangle rectangle;

    public Square(int x, int y) {
        rectangle = new Rectangle(x, y, 32, 32);
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Square)) {
            return false;
        }

        Square square = (Square) o;

        return (rectangle.getX() == square.getRectangle().getX())
                && (rectangle.getY() == square.getRectangle().getY());

    }

    public void update(Array<Square> gameSquares) {

        float origin = rectangle.getY();
        rectangle.setY(origin - 32);

        gameSquares.forEach(square -> {
            if (rectangle.overlaps(square.getRectangle()) && !square.equals(this)) {
                rectangle.setY(origin);
            }
        });

    }

}
