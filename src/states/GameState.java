package states;

import java.awt.Graphics;

import gameObject.Enemigo;
import gameObject.Player;
import graphics.Assets;
import math.Vector2D;

public class GameState {
	
	private Player player;
	
	private Enemigo enemigo;
	
	public GameState() 
	{
		player = new Player(new Vector2D(100,100),Assets.player);
		
		enemigo = new Enemigo(new Vector2D(600,400),Assets.player);
	}
	public void update() 
	{
		player.update();
		enemigo.update();
	}
	//caracteristicas en comun de objetos 
	//como puede ser posicion
	public void draw(Graphics g) 
	{
		player.draw(g);
		enemigo.draw(g);
	}

}
