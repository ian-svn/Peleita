package io.github.some_example_name.lwjgl3;

import java.io.File;
import java.io.IOException;

public class VideoLauncher {

    private Process videoProcess;
    private boolean isPlaying = false;

    /**
     * Reproduce un video con el reproductor predeterminado del sistema.
     *
     * @param videoPath La ruta del archivo de video.
     */
    public void playVideo(String videoPath) {
        try {
            // Crea un comando para abrir el video
            File videoFile = new File(videoPath);
            if (!videoFile.exists()) {
                throw new IOException("El archivo de video no existe: " + videoPath);
            }

            // Inicia el proceso para reproducir el video
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "start", videoFile.getAbsolutePath());
            videoProcess = builder.start();
            isPlaying = true;

            // Monitorea el proceso en un hilo separado
            new Thread(() -> {
                try {
                    // Bloquea hasta que el proceso termine
                    videoProcess.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    isPlaying = false; // El video ha terminado
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            isPlaying = false; // Error al iniciar el video
        }
    }

    /**
     * Verifica si el video se está reproduciendo.
     *
     * @return true si el video aún se está reproduciendo, false en caso contrario.
     */
    public boolean isVideoPlaying() {
        if (videoProcess == null) {
            return false;
        }
        return isPlaying;
    }
}
