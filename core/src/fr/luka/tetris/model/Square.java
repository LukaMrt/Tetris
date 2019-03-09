package fr.luka.tetris.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

public class Square {

    @Getter
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

        float thisX = rectangle.getX();
        float thisY = rectangle.getY();
        float oX = square.rectangle.getX();
        float oY = square.rectangle.getY();

        return (thisX == oX) && (thisY == oY);

    }

    public void update(Array<Square> gameSquares) {

        rectangle.setY(rectangle.getY() - 32);

        for (Square square : gameSquares) {
            if (rectangle.overlaps(square.getRectangle())) {
                rectangle.setY(rectangle.getY() + 32);
            }
        }

    }
}
