package fr.luka.tetris.model;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;

public class Square {

    @Getter
    private Rectangle rectangle;

    public Square(int x) {
        rectangle = new Rectangle(x, 800, 32, 32);
    }

}
