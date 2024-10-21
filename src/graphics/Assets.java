package graphics;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player;
	
	public static void init() 
	{
		player= Loader.ImageLoader("/Player/battletoad__rash_ver03_by_omegachaino_dd1t1wq.png");
	}
	
}
