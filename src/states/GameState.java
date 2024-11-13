package states;

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
import main.Window;
import math.Vector2D;

public class GameState {
	
	private Player player;
	public List<Enemigo> enemigos;
	private List<DamageIndicator> damageIndicators;
	public int Lvl=3;
	public GameState() 
	{
		player = new Player(new Vector2D(100,100),Assets.player);
		
		enemigos = new ArrayList<>(); // Inicializar la lista de enemigos

		damageIndicators = new ArrayList<>();
		// Añadir enemigos a la lista
		enemigos.add(new Enemigo(spawnRandom(), Assets.player));
		
	}
	public void update() {	 
	    if (player.vidaPlayer > 1) {
	        player.update();
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
	            colition(enemigo.getPosition(), shoot.getPosition(), enemigo, shootIterator); // Pasa el iterator a colition
	            
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

	//caracteristicas en comun de objetos 
	//como puede ser posicion
	public void draw(Graphics g) 
	{
		if (player.vidaPlayer>1) 
		{
			player.draw(g);
		}
		
		for (DamageIndicator damageIndicators : damageIndicators) {
			damageIndicators.draw(g);
		}
		for (Enemigo enemigo : enemigos) {
			enemigo.draw(g);
		}
	}
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
	    	nuevosEnemigos.add(new Enemigo(spawnRandom(), Assets.player));
	    }
	}
	
	public void Win(Graphics g) {
		BufferedImage Win= Assets.Win;
		g.drawImage(Win, 0, 0, Window.WIDTH, Window.HEIGHT, null);
	}

}
