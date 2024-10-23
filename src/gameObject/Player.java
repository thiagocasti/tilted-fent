package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import input.KeyBoard;
import math.Vector2D;

public class Player extends GameObject{

	public Player(Vector2D position, BufferedImage texture) {
		super(position, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(KeyBoard.W) {position.setY(position.getY()-3);}
		if(KeyBoard.S) {position.setY(position.getY()+3);}
		if(KeyBoard.D) {position.setX(position.getX()+3);}
		if(KeyBoard.A) {position.setX(position.getX()-3);}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		g.drawImage(texture, (int)position.getX(), (int)position.getY(), null);
		
	}

}
