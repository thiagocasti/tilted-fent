package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graphics.Assets;
import main.Window;
import math.Vector2D;

public class Enemigo extends GameObject{
	
	private static final int COOLDOWN_TIME = 50;
	public int vidaEnemigo = 45; // Vida del enemigo
	private int damageCooldown = 0; // Cooldown para recibir daño (0 = puede recibir daño)
	private int disparoCooldown= 0;
	private static final int MAX_COOLDOWN = 60; // 30 segundos a 60 FPS
	 public List<Shoot> shootsEnemys = new ArrayList<>();
	public Enemigo(Vector2D position, BufferedImage texture) {
		super(position, texture);
		damageCooldown = 0; 
		// TODO Auto-generated constructor stub
	}
	
	
	public void update() {
		position.add(0, true);
		for (Shoot shoot : shootsEnemys) {
            shoot.update();
        }
		if (damageCooldown > 0) {
	        damageCooldown--;
	    }
		if (disparoCooldown > 0) {
			disparoCooldown--;
	    }
		position =returnMap(position);
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
		 for (Shoot shoot : shootsEnemys) {
	            shoot.draw(g);
	        }
	};
	
	// En Enemigo.java
	public boolean DirShoot(Vector2D a, Vector2D b) {
		if(a.getX()>b.getX()) {
			return false;
		}
		if(a.getX()<b.getX()) {
			return true;
		}
		return true;
	}
	
	public void dispararEnemigo(Vector2D a, Vector2D b) {
	    if (a.getY() + 30 > b.getY() && a.getY() < b.getY() + 30 && disparoCooldown == 0) {
	        BufferedImage shootTexture = Assets.Shoot;
	        Shoot disparo = new Shoot(new Vector2D(position.getX()+ (DirShoot(a,b) ? 15:-15), position.getY()), shootTexture, DirShoot(a,b));
	        shootsEnemys.add(disparo);  // Ahora se añade a la instancia específica de Player
	        disparoCooldown =45;
	    }
	}
	public List<Shoot> getShoot() {
		 return shootsEnemys;
	 };
	public Vector2D returnMap(Vector2D enemigo) {
		double x=enemigo.getX();
		double y=enemigo.getY();
		if(enemigo.getX()>Window.WIDTH-60) {
			x= x-3;
		}else 
		if(enemigo.getX()<60) {
			x= x+3;
		}
		if(enemigo.getY()>Window.HEIGHT-100) {
			y = Window.HEIGHT-100;
		}else 
		if(enemigo.getY()<0) {
			y = 0;
		}
		return new Vector2D(x,y);
	}
}
