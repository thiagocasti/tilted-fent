package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

public class Enemigo extends GameObject{
	
	public int vidaEnemy= 100;
	private static final int COOLDOWN_TIME = 50;
	private int damageCooldown ;

	public Enemigo(Vector2D position, BufferedImage texture) {
		super(position, texture);
		// TODO Auto-generated constructor stub
	}
	
	
	public void update() {
		position.add(0, true);
	};
	

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(texture,  (int) position.getX(),  (int) position.getY(), null);
	};

}
