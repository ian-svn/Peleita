
package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.mapas.Piso;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Enemigo extends Individuo {
    private Luchador luchador;
    private Piso piso;
    private Boolean vivo = true;

    public Enemigo(Float x, Float y, Luchador luchador, Piso piso) {
        super(x, y, piso);
        this.luchador = luchador;
        this.piso = piso;
    }

    @Override
    public void paint(SpriteBatch batch) {
        batch.draw(sprite, getX(), getY(), getANCHO(), getALTO());
    }

    @Override
    public void moverse() {

    }


}
