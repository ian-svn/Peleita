
package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.mapas.Piso;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static io.github.some_example_name.mapas.Piso.posX_Luchador;
import static io.github.some_example_name.mapas.Piso.posY_Luchador;
import static java.lang.Math.round;

@Getter
@Setter
/** {@link ApplicationListener} implementation shared by all platforms. */
public class
Escenario extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Luchador luchador;
    private OrthographicCamera camera;
    public static final Float ANCHO = 1400f;
    public static final Float ALTO = 900f;
    private int niveles = 3;
    private int nivelActual = 0;
    private BitmapFont font;
    private BitmapFont fontTitulo;
    private List<Habitacion> habitaciones;
    private Texture spriteRect;
    private Piso piso;
    private int countReset = 60, countResetAux = countReset;
    private boolean ganar = false;
    private Sprite fondo;
    private TextureAtlas fondos;
    private boolean menu;

    @Override
    public void create() {
        menu = true;
        batch = new SpriteBatch();
        atlas = new TextureAtlas("caballero.atlas");

        fondos = new TextureAtlas("escenarios.atlas");
        fondo = fondos.createSprite("infierno");
        fondo.setBounds(0, 0, ANCHO, ALTO);

        font = new BitmapFont(); // Usa la fuente predeterminada
        fontTitulo = new BitmapFont(); // Usa la fuente predeterminada
        font.setColor(Color.WHITE);

        fontTitulo.getData().setScale(6f);
        fontTitulo.setColor(Color.OLIVE);
        //spriteRect = new Texture("assets/white.png");
        luchador = new Luchador(ANCHO/2-Luchador.ANCHO/2, ALTO/2-Luchador.ALTO/2, null);
        piso = new Piso(nivelActual, luchador,this);
        cargarNivel();
        luchador.setPiso(piso);
        habitaciones = piso.getHabitaciones();
    }

    @Override
    public void render() {
        // Limpiar la pantalla antes de dibujar
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();


        // Comenzar a dibujar
        batch.draw(fondo,0, 0, ANCHO, ALTO);

        piso.paint(batch);
        if(!ganar) {
            luchador.paint(batch); // Dibujar al luchador
        } else {
            fontTitulo.draw(batch, "¡Felicidades, te has ", ANCHO/6, ALTO/2+ALTO/6);
            fontTitulo.draw(batch, " convertido en dios!", ANCHO/6, ALTO/2);
        }

        //drawRectangle(batch,luchador.getBoundsGrande(), spriteRect);

        font.draw(batch, " Nivel: " + nivelActual, 20, 20);
        font.draw(batch, " Vidas: " + luchador.getVidas(), 80, ALTO - 20);

        //System.out.println("posx: " + posX_Luchador + " posy: " + posY_Luchador);

        piso.paintMapa(batch);

        // isJuegoPasado(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void pasarNivel() {
        cargarNivel();
        piso = new Piso(nivelActual, luchador,this);
        luchador.setPiso(piso);
        habitaciones = piso.getHabitaciones();
    }
/*
    public void isJuegoPasado(SpriteBatch batch){
        if(!luchador.getVivo()){
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Lobster.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 50; // font size
            BitmapFont lobster = generator.generateFont(parameter);
            generator.dispose();
            lobster.setColor(com.badlogic.gdx.graphics.Color.RED);
            lobster.draw(batch, "Perdiste", ANCHO/2-lobster.getRegion().getRegionWidth()/2,350);
        }
    }*/

    public void cargarNivel() {
        nivelActual++;
        if(nivelActual>3){
            nivelActual--;
            ganar = true;
            luchador.setVivo(false);
        }
        switch (nivelActual){
            case 2:
                fondo = fondos.createSprite("tierra");
                break;
            case 3:
                fondo = fondos.createSprite("cielo");
                break;
        }
    }

    public static void drawRectangle(SpriteBatch batch, Rectangle rect, Texture sprite) {
        batch.draw(sprite, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    /*public static float redondearFloat(float numero, int decimales) {
        float factor = (float) Math.pow(10, decimales);
        return Math.round(numero * factor) / factor;
    }*/

}
