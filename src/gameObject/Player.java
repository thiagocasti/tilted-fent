package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

public class Player extends GameObject{

	public Player(Vector2D position, BufferedImage texture) {
		super(position, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		g.drawImage(texture, (int)position.getX(), (int)position.getY(), null);
		
	}

}
