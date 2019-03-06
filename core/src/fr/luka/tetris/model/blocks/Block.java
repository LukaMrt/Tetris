package fr.luka.tetris.model.blocks;

import com.badlogic.gdx.utils.Array;
import fr.luka.tetris.model.Square;
import lombok.Getter;

public abstract class Block {

    @Getter
    private Array<Square> squares;


}
