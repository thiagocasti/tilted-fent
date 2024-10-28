package math;
//se encarga de posicion
public class Vector2D {
	private double x,y;
	public Vector2D(double x, double y) 
	{
		this.x=x;
		this.y=y;
	}
	
	public Vector2D() 
	{
		x=0;
		y=0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void add(int i,boolean Dir) {
		if(Dir) {
			  x += i; // Sumar i a x y actualizar el valor de x
		}else {
			x -= i;
		}
	   
	}
	
	
	
}
