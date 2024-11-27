package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.mapas.Piso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Individuo {

    protected Sprite sprite;
    protected TextureAtlas atlas;
    protected Float ANCHO;
    protected Float ALTO;
    private Float x;
    private Float y;
    private Float velocidadX;
    private Float velocidadY;
    private Piso piso;

    public Individuo(Float x, Float y, Piso piso){
        this.x = x;
        this.y = y;
        this.velocidadX=0f;
        this.velocidadY=0f;
        this.piso = piso;
    }

    public abstract void paint(SpriteBatch batch);

    public abstract void moverse();

    public Rectangle getBounds(){
        return new Rectangle(x, y, ANCHO, ALTO/2);
    }

    public Rectangle getBoundsGrande(){
        return new Rectangle(x, y, ANCHO+ANCHO/4, ALTO);
    }
}
