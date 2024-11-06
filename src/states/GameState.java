package states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gameObject.DamageIndicator;
import gameObject.Enemigo;
import gameObject.GameObject;
import gameObject.Player;
import gameObject.Shoot;
import graphics.Assets;
import math.Vector2D;

public class GameState {
	
	private Player player;
	public List<Enemigo> enemigos;
	private List<DamageIndicator> damageIndicators;
	public GameState() 
	{
		player = new Player(new Vector2D(100,100),Assets.player);
		
		enemigos = new ArrayList<>(); // Inicializar la lista de enemigos

		damageIndicators = new ArrayList<>();
		// Añadir enemigos a la lista
		enemigos.add(new Enemigo(new Vector2D(600, 400), Assets.player));
		enemigos.add(new Enemigo(new Vector2D(400, 400), Assets.player));
		enemigos.add(new Enemigo(new Vector2D(500, 500), Assets.player));
	}
	public void update() 
	{	 
		if (player.vidaPlayer>1) 
		{
		player.update();
		}
		
		//iterator hace posible eliminar los objetos de la lista sin que explote todo  
		Iterator<Enemigo> iterator = enemigos.iterator();
		while (iterator.hasNext()) {
		    Enemigo enemigo = iterator.next();
		    enemigo.update();
		    
		    // se detecta colision en jugador y enemigo
		    colition(player.getPosition(), enemigo.getPosition(), player);
		   for (Shoot shoots : player.getShoot()) {
		    colition(enemigo.getPosition(), shoots.getPosition(), enemigo);
		    };
		    // si la vida llega a 0 se elimina 
		    if (enemigo.vidaEnemigo <= 0) {
		        iterator.remove();
		        System.out.println("objeto eliminado");
		    }
		}
		
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
	public void colition(Vector2D a, Vector2D b, GameObject character) {
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
	    }
	}
	private void mostrarIndicadorDeDano(GameObject character, String dano) {
	    Vector2D damagePos = new Vector2D(character.getPosition().getX(), character.getPosition().getY() - 10);
	    DamageIndicator indicator = new DamageIndicator(damagePos, dano, 60);
	    damageIndicators.add(indicator);	
	}
}
