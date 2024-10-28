package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graphics.Assets;
import input.KeyBoard;
import main.Window;
import math.Vector2D;

public class Player extends GameObject{
	
	 private boolean facingRight = true;
	 private boolean MovingRight = false;
	 private boolean MovingLeft = false;
	 private int FrameCount;
	 private int FrameSmoke=0;
	 //es el cooldawn de el humo, 48 es igual a 2 segundos
	 private int Cooldawn= 48;
	 // este es un tipo de lista para los bojetos
	 private List<Shoot> shoots;
	 
	public Player(Vector2D position, BufferedImage texture) {
		super(position, texture);
		shoots = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}
	
	public void shoot() {
	    // Crea un nuevo disparo en la posición actual del jugador
	BufferedImage shootTexture = Assets.Shoot;
	Shoot newShoot = new Shoot(new Vector2D(position.getX(), position.getY()), shootTexture,facingRight); 
	    // Añade el disparo a la lista de disparos (suponiendo que existe una lista en el juego)
	 shoots.add(newShoot);
	}
	
	public BufferedImage MoveRight= Assets.MoveRightPlayer;
	public BufferedImage MoveLeft= Assets.MoveLeftPlayer;
	public BufferedImage SmokeRun[]= {Assets.SmokeRun1,Assets.SmokeRun2,Assets.SmokeRun3,Assets.SmokeRun4,Assets.SmokeRun5,Assets.SmokeRun6,
	Assets.SmokeRun7,Assets.SmokeRun8,Assets.SmokeRun9,Assets.SmokeRun10,Assets.SmokeRun11,Assets.SmokeRun12,Assets.SmokeRun13};
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(KeyBoard.W) {position.setY(position.getY()-3);}
		if(KeyBoard.S) {position.setY(position.getY()+3);}
		if(KeyBoard.D) {position.setX(position.getX()+5); facingRight = true; MovingRight= true;}
		if(KeyBoard.A) {position.setX(position.getX()-3); facingRight = false; MovingLeft= true;}
		if(KeyBoard.SHOOT && Cooldawn >=48) { shoot(); Cooldawn=0; };
		
		if( (int)position.getY()> Window.HEIGHT-212) {
			position.setY(Window.HEIGHT-212);
		}
		if( (int)position.getY()< 0) {
			position.setY(0);
		}	
		
		if( (int)position.getX()> Window.WIDTH-127) {
			position.setX(0);
		}
		if( (int)position.getX()< 0) {
			position.setX(Window.WIDTH-127);
		}	
		
		for (Shoot shoot : shoots) {
            shoot.update();
        }
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		 	int posX = (int) position.getX();
	        int posY = (int) position.getY();
	        int width = texture.getWidth(null);
	        int height = texture.getHeight(null);

	        if (facingRight) {
	            // Dibuja la imagen normalmente
	        	if(MovingRight) {
	        		g.drawImage(MoveRight, posX, posY, width, height, null);
	        		//en este cada frame del humo
	        		g.drawImage(SmokeRun[FrameSmoke], posX-(width), posY, width, height, null);
	        	}else {
	        		g.drawImage(texture, posX, posY, width, height, null);
	        	}
	        
	        } else {
	            // Dibuja la imagen reflejada
	        		
	        	if(MovingLeft) {
	        			//este carga la posicion animada del personaje
		        		g.drawImage(MoveLeft, posX, posY, width, height, null);
		        		//en este cada frame del humo
		        		//-width es para que esté dado vuelta 
		        		g.drawImage(SmokeRun[FrameSmoke], posX+(width*2), posY, -width, height, null);
		        }else {
		        		g.drawImage(texture, posX + width, posY, -width, height, null);
		        		
		        }
	        }
		g.drawString("X:"+ (int)position.getX() +" Y: "+(int)position.getY() , 30, 10);	
		
		 MovingRight = false;
		 MovingLeft = false;
		 FrameCount++;
		 if(Cooldawn <= 48) {
			 Cooldawn++;
		 }
		 if (FrameCount>5) {
			 FrameSmoke++;
		 }
		 if (FrameSmoke>12) {
			 FrameSmoke=0;
		 }
		 
		 if (FrameCount>60) {
			 FrameCount=0;
		 }
		 for (Shoot shoot : shoots) {
	            shoot.draw(g);
	        }
	}

}
