package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gameObject.DamageIndicator;
import gameObject.Enemigo;
import gameObject.GameObject;
import gameObject.Player;
import gameObject.Shoot;
import graphics.Assets;
import input.KeyBoard;
import main.Window;
import math.Vector2D;

public class GameState {

    private Player player;
    public List<Enemigo> enemigos;
    private List<DamageIndicator> damageIndicators;
    private boolean showRespawnMenu = false; // Bandera para mostrar el menú de respawn
    private int selectedOption = 0; // Opción seleccionada en el mini menú
    public int Lvl = 3;

    public GameState() {
        player = new Player(new Vector2D((Window.WIDTH/2),(Window.HEIGHT/2)), Assets.player);

        enemigos = new ArrayList<>(); // Inicializar la lista de enemigos
        damageIndicators = new ArrayList<>();

        // Añadir enemigos a la lista
        enemigos.add(new Enemigo(spawnRandom(), Assets.Enemigo));
    }

    public void update() {
        if (showRespawnMenu) {
            handleRespawnMenuInput();
            return; // No actualizamos el resto del juego mientras se muestra el menú
        }

        if (player.vidaPlayer > 1) {
            player.update();
        } else {
            showRespawnMenu = true; // Activar el mini menú
            MenuState.playSound("./res/Muerte.wav");
        }

        // Lista para los nuevos enemigos en la siguiente ronda
        List<Enemigo> nuevosEnemigos = new ArrayList<>();

        // Usamos iterator para eliminar enemigos de forma segura
        Iterator<Enemigo> iterator = enemigos.iterator();
        while (iterator.hasNext()) {
            Enemigo enemigo = iterator.next();
            enemigo.update();

            // Detecta colisión entre jugador y enemigo
            colition(player.getPosition(), enemigo.getPosition(), player, null);

            // Usa un iterator para los disparos del jugador
            Iterator<Shoot> shootIterator = player.getShoot().iterator();
            while (shootIterator.hasNext()) {
                Shoot shoot = shootIterator.next();
                colition(enemigo.getPosition(), shoot.getPosition(), enemigo, shootIterator);

                // Si la vida del enemigo llega a 0, se elimina
                if (enemigo.vidaEnemigo <= 0) {
                    iterator.remove();
                    System.out.println("Enemigo eliminado");

                    if (siguienteRonda()) {
                        System.out.println("Se terminó la ronda");
                        iniciaRondaNueva(nuevosEnemigos);
                    }
                    break; // Sale del bucle de disparos si el enemigo fue eliminado
                }
            }

            // Disparos del enemigo hacia el jugador
            enemigo.dispararEnemigo(enemigo.getPosition(), player.getPosition());

            for (Shoot shootsEnemys : enemigo.getShoot()) {
                colition(player.getPosition(), shootsEnemys.getPosition(), player, null);
            }

            // Movimiento del enemigo
            enemigo.Move(player.getPosition(), enemigo.getPosition());
        }

        enemigos.addAll(nuevosEnemigos);

        // Actualiza y elimina los indicadores de daño expirados
        damageIndicators.removeIf(DamageIndicator::isExpired);
        for (DamageIndicator indicator : damageIndicators) {
            indicator.update();
        }
    }

    public void draw(Graphics g) {
        BufferedImage Map = Assets.Map;
        g.drawImage(Map, 0, 0, Window.WIDTH, Window.HEIGHT, null);

        if (player.vidaPlayer > 1) {
            player.draw(g);
        }

        for (DamageIndicator damageIndicators : damageIndicators) {
            damageIndicators.draw(g);
        }
        for (Enemigo enemigo : enemigos) {
            enemigo.draw(g);
        }

        if (showRespawnMenu) {
            drawRespawnMenu(g);
        }
    }

    private void handleRespawnMenuInput() {
        if (KeyBoard.W) {
            selectedOption = (selectedOption - 1 + 2) % 2; // Cambiar opción (circular)
        } else if (KeyBoard.S) {
            selectedOption = (selectedOption + 1) % 2; // Cambiar opción (circular)
        } else if (KeyBoard.SPACEBAR) {
            if (selectedOption == 0) {
                // Opción "Revivir"
            	enemigos.clear();
            	Lvl=3;
            	enemigos.add(new Enemigo(spawnRandom(), Assets.Enemigo));
                player.vidaPlayer = 100; // Restablecer la vida del jugador
                showRespawnMenu = false; // Salir del menú
            } else if (selectedOption == 1) {
                // Opción "Salir"
                System.exit(0); // Salir del juego
            }
        }
    }

    private void drawRespawnMenu(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150)); // Fondo semitransparente
        g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString("¡Has muerto!", Window.WIDTH / 2 - 150, Window.HEIGHT / 2 - 100);

        g.setColor(selectedOption == 0 ? Color.YELLOW : Color.WHITE);
        g.drawString("Revivir", Window.WIDTH / 2 - 100, Window.HEIGHT / 2);

        g.setColor(selectedOption == 1 ? Color.YELLOW : Color.WHITE);
        g.drawString("Salir", Window.WIDTH / 2 - 100, Window.HEIGHT / 2 + 100);
    }

    // Métodos auxiliares...
    // (se mantienen iguales)

	public void colition(Vector2D a, Vector2D b, GameObject character,Iterator<Shoot> shootIterator) {
	    int width = character.texture.getWidth();
	    int height = character.texture.getHeight();

	    if (a.getX() + (width / 2) > b.getX() && a.getX() < b.getX() + (width / 2) &&
	        a.getY() + (height / 2) > b.getY() && a.getY() < b.getY() + (height / 2)) {

	        if (character instanceof Player) {
	            Player player = (Player) character;
	            if (player.canReceiveDamage()) {
	                player.receiveDamage(5);
	                mostrarIndicadorDeDano(player, "-5");
	            }
	        } else if (character instanceof Enemigo) {
	            Enemigo enemigo = (Enemigo) character;
	            if (enemigo.canReceiveDamage()) {
	                enemigo.receiveDamage(10);
	                mostrarIndicadorDeDano(enemigo, "-10");
	                
	            }
	        }
	        if (shootIterator != null) {
                shootIterator.remove();
            }
	    }
	}
	private void mostrarIndicadorDeDano(GameObject character, String dano) {
	    Vector2D damagePos = new Vector2D(character.getPosition().getX(), character.getPosition().getY() - 10);
	    DamageIndicator indicator = new DamageIndicator(damagePos, dano, 60);
	    damageIndicators.add(indicator);	
	}
	
	private Vector2D spawnRandom() {
		double x; 
		double y= Math.random() *Window.HEIGHT;
		if (Math.random() > 0.5) {
	       
	        x = Math.random() * 15 - 15; 
	    } else {
	       
	        x = Math.random() * 15 + Window.WIDTH;  
	    }
		return new Vector2D(x,y);
	}
	public boolean siguienteRonda() {
	    return enemigos.isEmpty();  // Devuelve true si no hay enemigos
	}
	public void iniciaRondaNueva(List<Enemigo> nuevosEnemigos) {
		Lvl=Lvl*2;
	    for (int i = 0; i < Lvl; i++) {
	    	nuevosEnemigos.add(new Enemigo(spawnRandom(), Assets.Enemigo));
	    }
	}
	
	public void Win(Graphics g) {
		BufferedImage Win= Assets.Win;
		g.drawImage(Win, 0, 0, Window.WIDTH, Window.HEIGHT, null);
	}

}
