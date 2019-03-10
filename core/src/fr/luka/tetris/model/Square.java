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

        float thisX = rectangle.getX();
        float thisY = rectangle.getY();
        float oX = square.rectangle.getX();
        float oY = square.rectangle.getY();

        return (thisX == oX) && (thisY == oY);

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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

}
