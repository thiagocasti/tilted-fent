package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import math.Vector2D;
import states.GameState;

public abstract class GameObject {
	public List<GameState> objects;
	public BufferedImage texture;
	 private List<Enemigo> Enemys;
	protected Vector2D position;
	//aca pasa la posicion, desde vector2d importado
	// y se pasa la imagen de bufferedimage que es la que se carga desde asstes
	//esto existe para que se genere de manera directa y resumida en ventana los objetos
	//esto es general y todo objeto tiene estas 2 caracteristicas
	public GameObject(Vector2D position, BufferedImage texture) 
	{
		this.position= position;
		this.texture= texture;
	}
	
	public Vector2D getCenter(){
		return new Vector2D(position.getX()- texture.getWidth(null)/2,position.getY()- texture.getHeight(null)/2);
	}
	
	public abstract void update() ;
	
	public abstract void draw(Graphics g);

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	
}
