package states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameObject.DamageIndicator;
import gameObject.Enemigo;
import gameObject.Player;
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
		for (Enemigo enemigos : enemigos) {
			enemigos.update();
			colition(player.getPosition(),enemigos.getPosition());
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
	public void colition(Vector2D a, Vector2D b) {
	    int width = player.texture.getWidth();
	    int height = player.texture.getHeight();

	    if (a.getX() + (width / 2) > b.getX() && a.getX() < b.getX() + (width / 2) &&
	        a.getY() + (height / 2) > b.getY() && a.getY() < b.getY() + (height / 2)) {

	        //System.out.println("colision");

	        if (player.canReceiveDamage()) {
	            player.receiveDamage(1);
	            System.out.println("Vida del jugador: " + player.vidaPlayer);

	            // Mostrar el indicador de daño
	            Vector2D damagePos = new Vector2D(player.getPosition().getX(), player.getPosition().getY() - 10);
	            DamageIndicator indicator = new DamageIndicator(damagePos, "-1", 60);
	            damageIndicators.add(indicator);
	        } else {
	            System.out.println(player.vidaPlayer);
	        }
	    }
	}

}
