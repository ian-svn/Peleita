package io.github.some_example_name.Enemigos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.Bloque;
import io.github.some_example_name.Enemigo;
import io.github.some_example_name.Individuo;
import io.github.some_example_name.Luchador;
import io.github.some_example_name.mapas.Piso;

import java.util.ArrayList;
import java.util.List;

public class Demonio extends Enemigo {

    private TextureAtlas atlas;
    private TextureRegion frameActual;
    public static final Float ANCHO = 60F;
    public static final Float ALTO = 80F;
    private Float vida;
    private Float velocidadX;
    private Float velocidadY;
    private Luchador luchador;
    private List<TextureRegion> frames = new ArrayList<>();
    private int countCambio=8, countCambioAux=countCambio;
    private int countDaniado=15, countDaniadoAux=countDaniado;
    private int frameIndex=0;
    private Boolean vivo;
    private Boolean daniado;
    private Boolean daniandose;
    private int ultimoLadoAux;
    private Piso piso;
    private boolean choqueBloque = false;
    private boolean arr = false;
    private boolean aba = false;
    private boolean izq = false;
    private boolean der = false;

    public Demonio(Float x, Float y, Luchador luchador, Piso piso){
        super(x, y, luchador, piso);
        setX(x);
        setY(y);
        setANCHO(60F);
        setALTO(80F);
        vida=25F;
        this.luchador = luchador;
        inicializarFrames();
        velocidadX=0F;
        velocidadY=0F;
        daniado = false;
        daniandose = false;
        frameActual = frames.get(0);
        this.vivo = true;
        this.piso = piso;
        setAtlas(new TextureAtlas("Enemigos/Demonio.atlas"));
        setSprite(getAtlas().createSprite("Demonio"));
    }

    @Override
    public void paint(SpriteBatch batch) {
        if(vivo) {
            if (velocidadX > 0f) {
                if(daniado){
                    batch.setColor(Color.valueOf("ff4a4a"));
                }
                batch.draw(frameActual, getX(), getY(), getANCHO(), getALTO());
                batch.setColor(Color.WHITE);
            } else {
                if(daniado){
                    batch.setColor(Color.valueOf("ff4a4a"));
                }
                batch.draw(frameActual, getX() + getANCHO(), getY(), -getANCHO(), getALTO());
                batch.setColor(Color.WHITE);
            }

            moverse();
            colisionar();
        }
    }

    @Override
    public void moverse() {
        if (vida <= 0) {
            vivo = false;
        }
        if(vivo&&!choqueBloque) {

            if (daniado) {
                countDaniadoAux--;
                if(ultimoLadoAux==0||ultimoLadoAux==1){
                    velocidadX = (ultimoLadoAux==1) ? -2f : 2f;
                    velocidadY = 0F;
                } else if(ultimoLadoAux==2||ultimoLadoAux==3){
                    velocidadX = 0F;
                    velocidadY = (ultimoLadoAux==3) ? -2f : 2f;
                }
                if (countDaniadoAux <= 0) {
                    countDaniadoAux = countDaniado;
                    daniado = false;
                }
            } else if (!daniado) {
                float disX = (getX()>luchador.getX()) ? getX()-luchador.getX() : luchador.getX()-getX();
                float disY = (getY()>luchador.getY()) ? getY()-luchador.getY() : luchador.getY()-getY();

                velocidadX = (luchador.getX() >= getX()) ? disX/100 : -disX/100;
                velocidadY = (luchador.getY() >= getY()) ? disY/100 : -disY/100;

                countCambioAux--;
                if (countCambioAux <= 0) {
                    countCambioAux = countCambio;
                    if (getVelocidadX() >= 0f) {
                        frameIndex = (frameIndex + 1) % frames.size();  // Avanza al siguiente frame y hace loop
                    } else if (getVelocidadX() < 0f) {
                        frameIndex = (frameIndex - 1 + frames.size()) % frames.size();  // Retrocede al frame anterior
                    }
                    frameActual = frames.get(frameIndex);  // Actualiza el frame actual
                }
                ultimoLadoAux=luchador.getUltimoLado();
            }

        } else if(vivo&&choqueBloque){
            if (daniado) {
                countDaniadoAux--;
                if (countDaniadoAux <= 0) {
                    countDaniadoAux = countDaniado;
                    daniado = false;
                }
            }
            if(arr){
                velocidadY=2F;
                velocidadX=0F;
            } else if(aba){
                velocidadY=-2F;
                velocidadX=0F;
            } else if(izq){
                velocidadY=0F;
                velocidadX=-2F;
            } else if(der){
                velocidadY=0F;
                velocidadX=2F;
            }
        }
        setX(getX() + velocidadX);
        setY(getY() + velocidadY);
    }

    public void inicializarFrames(){
        frames.add(atlas.findRegion("zombie1"));
        frames.add(atlas.findRegion("zombie2"));
        frames.add(atlas.findRegion("zombie3"));
        frames.add(atlas.findRegion("zombie4"));
    }

    public void colisionar(){
        if(!daniado&&vivo) {
            Rectangle reactEspada = luchador.getEspada().getBounds();
            if (reactEspada.overlaps(getBounds())&&luchador.getEspada().isZarpazo()) {
                daniar();
                vida -= luchador.getEspada().getDanio();
            }
        }
        if(vivo&&luchador.getVivo()){
            Rectangle reactLuchador = luchador.getBounds();
            if (reactLuchador.overlaps(getBounds())&&!luchador.getDaniado()) {
                luchador.daniar();
            }
        }

        if(vivo){
            boolean aux = true;
            for(Bloque bloque : piso.buscarHab().getBloques()) {
                if(bloque.getBounds().overlaps(getBoundsGrande())){
                    choqueBloque = true;
                    aux = false;
                }
            }
            if(aux){
                choqueBloque = false;
                arr=false;
                aba=false;
                izq=false;
                arr=false;
            }
        }

        if(vivo&&choqueBloque){
            for(Bloque bloque : piso.buscarHab().getBloques()){

                Rectangle reactBloque = bloque.getBounds();
                Rectangle reactEnemigoArr = new Rectangle(getX(), getY()+10F, ANCHO, ALTO);
                Rectangle reactEnemigoAba = new Rectangle(getX(), getY()-10F, ANCHO, ALTO);
                Rectangle reactEnemigoDer = new Rectangle(getX()+10F, getY(), ANCHO, ALTO);
                Rectangle reactEnemigoIzq = new Rectangle(getX()-10F, getY(), ANCHO, ALTO);

                if(reactBloque.overlaps(reactEnemigoArr)){
                    if(getX() >= luchador.getX()){
                        der=true;
                    } else {
                        izq=true;
                    }
                } else if(reactBloque.overlaps(reactEnemigoAba)) {
                    if(getX() >= luchador.getX()){
                        izq=true;
                    } else {
                        der=true;
                    }
                }
                if(reactBloque.overlaps(reactEnemigoIzq)){
                    if(getY() >= luchador.getY()){
                        arr=true;
                    } else {
                        aba=true;
                    }
                } else if(reactBloque.overlaps(reactEnemigoDer)){
                    if(getY() >= luchador.getY()){
                        aba=true;
                    } else {
                        arr=true;
                    }
                }
            }
        }
    }

    public void daniar(){
        daniado=true;
    }

}
