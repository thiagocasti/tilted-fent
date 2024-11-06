package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

public class Enemigo extends GameObject{
	
	private static final int COOLDOWN_TIME = 50;
	public int vidaEnemigo = 45; // Vida del enemigo
	private int damageCooldown = 0; // Cooldown para recibir daño (0 = puede recibir daño)
	private static final int MAX_COOLDOWN = 60; // 30 segundos a 60 FPS
	
	public Enemigo(Vector2D position, BufferedImage texture) {
		super(position, texture);
		damageCooldown = 0; 
		// TODO Auto-generated constructor stub
	}
	
	
	public void update() {
		position.add(0, true);
		
		if (damageCooldown > 0) {
	        damageCooldown--;
	    }
	};
	public boolean canReceiveDamage() {
        return damageCooldown == 0;
    }

	 public void receiveDamage(int amount) {
	        if (canReceiveDamage()) {
	            vidaEnemigo -= amount;
	            damageCooldown = MAX_COOLDOWN;
	            System.out.println("Vida del enemigo: " + vidaEnemigo);
	        }
	    }

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(texture,  (int) position.getX(),  (int) position.getY(), null);
		g.drawString(" vida enemigo :" + vidaEnemigo, 120, 10);
	};
	
	public void dispararEnemigo(Vector2D a,Vector2D b) {
		if(a.getX()>=b.getX()+20||a.getX()<=b.getX()-20 ) {
			
		}
	}
}
