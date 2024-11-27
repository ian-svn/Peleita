package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.mapas.Mapa;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.mapas.Piso;
import lombok.Getter;
import lombok.Setter;

import static io.github.some_example_name.mapas.Piso.*;

@Getter
@Setter
public class Puerta {
    private Float x;
    private Float y;
    public static final Float ANCHO = 165F;
    public static final Float ALTO = 117F;
    private Luchador luchador;
    private Texture texture;
    private Sprite sprite;
    private EPosPuerta posPuerta;
    private boolean entrar;
    private Habitacion habitacion;
    private Escenario escenario;
    private Texture spriteRect;
    private TextureAtlas atlas;

    public Puerta(Luchador luchador, Habitacion habitacion, Escenario escenario){
        this.luchador = luchador;
        this.habitacion = habitacion;
        this.escenario = escenario;
        entrar = false;
        posPuerta = EPosPuerta.ESCOTILLA;
        atlas = new TextureAtlas("puertas.atlas");
        texture = new Texture(Gdx.files.internal("assets/Escotilla.png"));
        sprite = new Sprite(texture);
        x = (Mapa.DIM_X * Bloque.ANCHO) / 2;
        y = (Mapa.DIM_Y * Bloque.ALTO) / 2;
        sprite.setBounds(x,y,ANCHO,ALTO);
    }

    public Puerta(Luchador luchador, EPosPuerta posPuerta, Habitacion habitacion, int nivelActual){
        this.luchador = luchador;
        this.posPuerta = posPuerta;
        this.habitacion = habitacion;
        entrar = false;
        atlas = new TextureAtlas("puertas.atlas");
        sprite = atlas.createSprite("infierno");
        switch (nivelActual){
            case 2:
                sprite = atlas.createSprite("tierra");
                break;
            case 3:
                sprite = atlas.createSprite("cielo");
                break;
        }

        switch(posPuerta){
            case ARRIBA:
                //System.out.println("dymX: " + Mapa.DIM_X + "dymY: "+ Mapa.DIM_Y);
                x = (Mapa.DIM_X * Bloque.ANCHO) + Bloque.ANCHO - Bloque.ANCHO/6;
                y = (Mapa.DIM_X * Habitacion.CASILLA) + Bloque.ALTO*4 - Habitacion.BORDE_PARED;
                //System.out.println("x: " + x);
                break;
            case ABAJO:
                sprite.flip(false,true);
                x = (Mapa.DIM_X * Bloque.ANCHO) + Bloque.ANCHO - Bloque.ANCHO/6;
                y = Habitacion.BORDE_PARED/2 + Habitacion.BORDE_PARED/5;
                break;
            case DERECHA:
                sprite.flip(true,true);
                sprite.setRotation(90);
                x = (Mapa.DIM_X * Habitacion.CASILLA) * 2.12f;
                y = (Mapa.DIM_X * Habitacion.CASILLA) / 2 + Habitacion.CASILLA*1.9F;
                break;
            case IZQUIERDA:
                sprite.setRotation(90);
                x = 0F + Habitacion.BORDE_PARED;
                y = (Mapa.DIM_X * Habitacion.CASILLA) / 2 + Habitacion.CASILLA*1.9F;
                break;
        }
        sprite.setBounds(x,y,ANCHO,ALTO);
        //spriteRect = new Texture("assets/white.png");
    }

    public void paint(SpriteBatch batch){
        sprite.draw(batch);
        //spriteRect = new Texture("assets/white.png");
        //batch.draw(spriteRect,x,y,ANCHO-ANCHO/4,ALTO-ALTO/15);
        colision();
    }

    public void colision() {

        //Gdx.input.isKeyJustPressed(Input.Keys.E)&&
        if (entrar && luchador.getVivo() && habitacion.nivelPasado() && habitacion.getPiso().getCouldown()) {
            switch (posPuerta) {
                case ARRIBA:
                    luchadorArriba();
                    break;
                case ABAJO:
                    luchadorAbajo();
                    break;
                case DERECHA:
                    luchadorDerecha();
                    break;
                case IZQUIERDA:
                    luchadorIzquierda();
                    break;
                case ESCOTILLA:
                    escenario.pasarNivel();
                    break;
            }
            getHabitacion().getPiso().resetCouldown();
            //System.out.println("lucX: " + posX_Luchador + " lucY: " + posY_Luchador);
        }

        if (getBounds().overlaps(luchador.getBoundsGrande())) {
            entrar = true;
        } else {
            entrar = false;
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,ANCHO-ANCHO/4,ALTO);
    }

}