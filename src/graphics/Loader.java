package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


//Funcion que se encarga de buscar la direcciòn y cargar la imagen
public class Loader {

	public static BufferedImage ImageLoader(String path) {
		
		try {
			return ImageIO.read(Loader.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
