package io.github.some_example_name.Enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.some_example_name.Enemigo;
import io.github.some_example_name.Luchador;
import io.github.some_example_name.mapas.Piso;

public class Leon extends Enemigo {
    private Sprite sprite;
    private TextureAtlas atlas;
    public Leon(Float x, Float y, Luchador luchador, Piso piso) {
        super(x, y, luchador, piso);
        atlas = new TextureAtlas("Enemigos/Leon.atlas");
        sprite = atlas.createSprite("Leon");
    }

}
