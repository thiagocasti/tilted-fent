package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import input.KeyBoard;

public class MenuState {
    private int Option = 0; // Almacena la opción seleccionada (0 = Jugar, 1 = Controles, 2 = Salir).
    public int selectedOption = -1; // -1 indica que no se ha confirmado ninguna opción.
    private long lastInputTime = 0; // Para el cooldown de las teclas.
    private final int INPUT_COOLDOWN = 200; // Cooldown en milisegundos (200 ms).

    private boolean animating = false; // Indica si se está ejecutando la animación.
    private float animationAlpha = 1.0f; // Transparencia de las letras durante la animación.

    private boolean showingControls = false; // Indica si se está mostrando la pantalla de controles.

    public void update() {
        long currentTime = System.currentTimeMillis();

        // Si se están mostrando los controles, permite salir al menú principal.
        if (showingControls) {
            if (KeyBoard.SPACEBAR && currentTime - lastInputTime > INPUT_COOLDOWN) {
                playSound("./res/Seleccionado.wav"); // Reproduce sonido al volver.
                showingControls = false; // Vuelve al menú principal.
                animationAlpha = 1.0f; // Reinicia la transparencia para evitar errores.
                lastInputTime = currentTime;
            }
            return; // No procesamos más entradas mientras se muestran los controles.
        }

        // Si hay animación, reducimos la opacidad de las letras.
        if (animating) {
            animationAlpha -= 0.05f; // Reduce la opacidad gradualmente.
            if (animationAlpha <= 0) {
                // Finaliza la animación y procede con la acción seleccionada.
                if (Option == 0) {
                    System.out.println("Iniciando juego...");
                    selectedOption = Option;
                } else if (Option == 1) {
                    System.out.println("Mostrando controles...");
                    showingControls = true; // Cambia al modo de controles.
                    animating = false; // Detenemos la animación para evitar conflictos.
                } else if (Option == 2) {
                    System.out.println("Saliendo del juego...");
                    System.exit(0); // Finaliza el programa.
                }
            }
            return; // No procesamos entradas mientras hay animación.
        }

        // Detecta si se presiona SPACEBAR y comienza la animación.
        if (KeyBoard.SPACEBAR && selectedOption == -1 && currentTime - lastInputTime > INPUT_COOLDOWN) {
            playSound("./res/Seleccionado.wav"); // Reproduce sonido de selección.
            animating = true;
            lastInputTime = currentTime;
        }

        // Control del cooldown de las teclas.
        if (currentTime - lastInputTime > INPUT_COOLDOWN) {
            if (KeyBoard.W) {
                playSound("./res/CambioSeleccion.wav"); // Reproduce sonido de navegación.
                Option -= 1; // Mueve la selección hacia arriba.
                lastInputTime = currentTime;
            } else if (KeyBoard.S) {
                playSound("./res/CambioSeleccion.wav"); // Reproduce sonido de navegación.
                Option += 1; // Mueve la selección hacia abajo.
                lastInputTime = currentTime;
            }

            // Ajusta el índice de selección para mantenerlo dentro de los límites.
            if (Option < 0) {
                Option = 2; // Regresa al último elemento ("Salir").
            }
            if (Option > 2) {
                Option = 0; // Vuelve al primer elemento ("Jugar").
            }
        }
    }


    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Si se están mostrando los controles, dibuja la pantalla de controles.
        if (showingControls) {
            drawControlsScreen(g2d);
            return;
        }

        // Fondo negro.
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 1920, 1080);

        // Si hay animación, aplicamos transparencia.
        if (animating) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, animationAlpha));
        }

        // Configuración de la fuente.
        g2d.setFont(new Font("Arial", Font.BOLD, 50));

        // Opción "Jugar"
        g2d.setColor(Option == 0 ? Color.YELLOW : Color.WHITE);
        g2d.drawString("Jugar", 850, 400);

        // Opción "Controles"
        g2d.setColor(Option == 1 ? Color.YELLOW : Color.WHITE);
        g2d.drawString("Controles", 800, 500);

        // Opción "Salir"
        g2d.setColor(Option == 2 ? Color.YELLOW : Color.WHITE);
        g2d.drawString("Salir", 850, 600);

        // Restaura la transparencia al final del dibujo.
        if (animating) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    // Método para dibujar la pantalla de controles.
    private void drawControlsScreen(Graphics2D g2d) {
        // Fondo negro.
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 1920, 1080);

        // Configuración de la fuente.
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(Color.WHITE);

        // Título.
        g2d.drawString("Controles del juego", 700, 200);

        // Lista de controles.
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g2d.drawString("W - Mover hacia arriba", 750, 300);
        g2d.drawString("A - Mover hacia la izquierda", 750, 350);
        g2d.drawString("S - Mover hacia abajo", 750, 400);
        g2d.drawString("D - Mover hacia la derecha", 750, 450);
        g2d.drawString("K - Disparar", 750, 500);
        g2d.drawString("Espacio - Dash", 750, 550);

        // Instrucción para volver.
        g2d.drawString("Presiona ESPACIO para volver al menú", 650, 650);
    }

    // Método para reproducir sonidos.
    public static void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSelectedOption() {
        return selectedOption;
    }
}
