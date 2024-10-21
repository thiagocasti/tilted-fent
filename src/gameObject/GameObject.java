package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

public abstract class GameObject {

	protected BufferedImage texture;
	
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
	
	public abstract void update() ;
	
	public abstract void draw(Graphics g);

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	
}
