package io.github.some_example_name.Enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.some_example_name.Enemigo;
import io.github.some_example_name.Luchador;
import io.github.some_example_name.mapas.Piso;


public class Lobo extends Enemigo {
    private Sprite sprite;
    private TextureAtlas atlas;
    private final Float velocidad = 15F;
    private Float velocidadX;
    private Float velocidadY;

    public Lobo(Float x, Float y, Luchador luchador, Piso piso) {
        super(x, y, luchador, piso);
        atlas = new TextureAtlas("Enemigos/Lobo.atlas");
        sprite = atlas.createSprite("Lobo");
    }

    public void moverse(){
        velocidadX = (getX()>getLuchador().getX()) ? velocidad + (getX()-getLuchador().getX()) / 50 :
            velocidad + (getLuchador().getX() - getX()) / 50;
    }

}
