package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graphics.Assets;
import input.KeyBoard;
import main.Window;
import math.Vector2D;
import states.MenuState;

public class Player extends GameObject{
	
	 private boolean facingRight = true;
	 private boolean MovingRight = false;
	 private boolean MovingLeft = false;
	 private int FrameCount;
	 private int FrameSmoke=0;
	 public int vidaPlayer= 100;
	 //es el cooldawn de el humo, 48 es igual a 2 segundos
	 private int Cooldawn= 48;
	 private int CooldawnSpace=100;
	 private int dashDuration = 5; // Duración del dash (en fotogramas)
	 private int dashTimer = 0; // Temporizador para controlar el dash
	 public int Speed= 5;
	 
	 private static final int COOLDOWN_TIME = 50;
	 private int damageCooldown ;
	 // este es un tipo de lista para los bojetos
	 public List<Shoot> shoots = new ArrayList<>();
	 
	 public List<Shoot> getShoot() {
		 return shoots;
	 };
	 public void addShoot(Shoot shoot) {
	        shoots.add(shoot);
	    }
	 
	public Player(Vector2D position, BufferedImage texture) {
		super(position, texture);
		shoots = new ArrayList<>();
		vidaPlayer = 100;
		damageCooldown = 0; 
		// TODO Auto-generated constructor stub
	}
	
	public void shoot() {
	    // Crea un nuevo disparo en la posición actual del jugador
	MenuState.playSound("./res/Disparo.wav");
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
		if(KeyBoard.W) {position.setY(position.getY()-Speed);}
		if(KeyBoard.S) {position.setY(position.getY()+Speed);}
		if(KeyBoard.D) {position.setX(position.getX()+Speed); facingRight = true; MovingRight= true;}
		if(KeyBoard.A) {position.setX(position.getX()-Speed); facingRight = false; MovingLeft= true;}
		if(KeyBoard.SHOOT && Cooldawn >=48) { shoot(); Cooldawn=0; };
		if (KeyBoard.SPACEBAR && CooldawnSpace >= 100 && dashTimer <= 0) {
		    Speed = 15;  // Aumenta la velocidad durante el dash
		    dashTimer = dashDuration;  // Inicia el temporizador del dash
		    CooldawnSpace = 0;  // Resetea el cooldown del dash
		    MenuState.playSound("./res/Dashh.wav");
		}
		if( (int)position.getY()> Window.HEIGHT-100) {
			position.setY(Window.HEIGHT-100);
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
		if (damageCooldown > 0) {
	        damageCooldown--;
	    }
	}
	
	 public boolean canReceiveDamage() {
	        return damageCooldown <= 0; // Puede recibir daño si cooldown es 0
	    }

	    public void receiveDamage(int amount) {
	        if (canReceiveDamage()) {
	            vidaPlayer -= amount;
	            damageCooldown = COOLDOWN_TIME; // Reinicia el cooldown
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
		g.drawString("Vida jugador "+ vidaPlayer , 10, 30);	
		
		 MovingRight = false;
		 MovingLeft = false;
		 FrameCount++;
		 if(Cooldawn <= 48) {
			 Cooldawn++;
		 }
		 if (CooldawnSpace <= 100) {
		        CooldawnSpace++;
		    }
		 if (dashTimer > 0) {
			    dashTimer--;  // Cuenta atrás para el final del dash
			} else {
			    Speed = 5;  // Restablece la velocidad normal después del dash
			}
		 if(CooldawnSpace >= 15) {
			 Speed=3;
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
