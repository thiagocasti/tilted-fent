package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import math.Vector2D;

public class Shoot extends GameObject {
    private boolean Dir;
    int width = texture.getWidth(null);
    int height = texture.getHeight(null);
    
	public Shoot(Vector2D playerPosition, BufferedImage texture,boolean Dir) {
        // Inicializa el disparo en la posición del jugador
        super(new Vector2D(playerPosition.getX(), playerPosition.getY()), texture);
        this.Dir= Dir;
    }

    @Override
    public void update() {
		// Actualiza la posición del disparo
        position.add(10,Dir);
    }

    @Override
    public void draw(Graphics g) {
    	if(Dir) {
    		g.drawImage(texture,(int)position.getX()+ width,(int)position.getY(), width, height, null);
    	}else {
    		g.drawImage(texture,(int)position.getX()+ width,(int)position.getY(), -width, height, null);
    	}
    }
}
