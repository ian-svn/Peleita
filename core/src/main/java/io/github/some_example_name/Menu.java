package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
@Setter
public class Menu {
    private final Float ANCHO = Escenario.ANCHO;
    private final Float ALTO = Escenario.ALTO;
    private Texture sprite;
    private boolean activo;
    private boolean seguro;
    private Stage stage;
    private ImageButton botonInicio;
    private Texture botonInicioSprite;
    private ImageButton botonRanking;
    private Texture botonRankingSprite;
    private ImageButton botonCerrar;
    private Texture botonCerrarSprite;
    private TextureAtlas atlas;
    private ImageButton seguroMenu;
    private Texture texturaSeguro;

    public Menu(){
        sprite = new Texture("Menu.jpg");
        activo = true;
        seguro = false;

        texturaSeguro = new Texture("Menu/botonInicio.png");
        seguroMenu = new ImageButton(new TextureRegionDrawable(texturaSeguro));
        seguroMenu.setPosition(ANCHO / 6 - 200 / 2, ALTO/3*2 - ALTO / 5*2); // Posiciona el botón


        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // El Stage maneja los inputs
        // Crea el Stage

        // Crea el botón de inicio (usando una imagen como ejemplo)
        botonInicioSprite = new Texture("Menu/botonInicio.png");
        botonInicio = new ImageButton(new TextureRegionDrawable(botonInicioSprite));
        botonInicio.setPosition(ANCHO / 6 - botonInicio.getWidth() / 2, ALTO/3*2 - ALTO / 5); // Posiciona el botón
        //300 x 125

        botonRankingSprite = new Texture("Menu/botonRanking.png");
        botonRanking = new ImageButton(new TextureRegionDrawable(botonRankingSprite));
        botonRanking.setPosition(ANCHO / 6 - botonRanking.getWidth() / 2, ALTO/3*2 - ALTO / 5*2); // Posiciona el botón

        botonCerrarSprite = new Texture("Menu/botonSalir.png");
        botonCerrar = new ImageButton(new TextureRegionDrawable(botonCerrarSprite));
        botonCerrar.setPosition(ANCHO / 6 - botonCerrar.getWidth() / 2, ALTO/3*2  - ALTO / 5*3); // Posiciona el botón

        // Agrega un ClickListener al botón
        botonInicio.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activo=false;
            }
        });

        botonRanking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String nombreArchivo = "miArchivo.txt"; // Reemplaza con la ruta de tu archivo

                try {
                    String contenido = new String(Files.readAllBytes(Paths.get(nombreArchivo)));
                    System.out.println(contenido); // Procesa el contenido del archivo
                } catch (IOException e) {

                }
            }
        });

        botonCerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        stage.addActor(botonInicio);
        stage.addActor(botonRanking);
        stage.addActor(botonCerrar);

    }

    public void paint(SpriteBatch batch){
        if(seguro) {
            batch.draw(texturaSeguro, ANCHO / 2 - texturaSeguro.getWidth() / 2, ALTO / 2 - texturaSeguro.getHeight() / 2, 200, 150);
        } else if(activo) {
            batch.draw(sprite, 0, 0, ANCHO, ALTO);
            stage.act(Gdx.graphics.getDeltaTime());
            botonInicio.draw(batch, 1);
            stage.draw();
        }
    }

    // ... (resto del código)
}
