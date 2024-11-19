package graphics;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player;
	
	public static BufferedImage MoveRightPlayer;
	
	public static BufferedImage MoveLeftPlayer;
	
	public static BufferedImage SmokeRun1,SmokeRun2,SmokeRun3,SmokeRun4,SmokeRun5,SmokeRun6
	,SmokeRun7,SmokeRun8,SmokeRun9,SmokeRun10,SmokeRun11,SmokeRun12,SmokeRun13;
	
	public static BufferedImage Shoot;
	
	public static BufferedImage Win;
	
	public static BufferedImage Map;
	
	public static void init() 
	{
		player= Loader.ImageLoader("/Player/frame default 1.png");
		
		MoveRightPlayer= Loader.ImageLoader("/Player/frame adelante 1.png");
		
		MoveLeftPlayer= Loader.ImageLoader("/Player/frame atras 1.png");
		
		SmokeRun1= Loader.ImageLoader("/Player/PlayerEffects/frame effect 1.png");
		SmokeRun2= Loader.ImageLoader("/Player/PlayerEffects/frame effect 2.png");
		SmokeRun3= Loader.ImageLoader("/Player/PlayerEffects/frame effect 3.png");
		SmokeRun4= Loader.ImageLoader("/Player/PlayerEffects/frame effect 4.png");
		SmokeRun5= Loader.ImageLoader("/Player/PlayerEffects/frame effect 5.png");
		SmokeRun6= Loader.ImageLoader("/Player/PlayerEffects/frame effect 6.png");
		SmokeRun7= Loader.ImageLoader("/Player/PlayerEffects/frame effect 7.png");
		SmokeRun8= Loader.ImageLoader("/Player/PlayerEffects/frame effect 8.png");
		SmokeRun9= Loader.ImageLoader("/Player/PlayerEffects/frame effect 9.png");
		SmokeRun10= Loader.ImageLoader("/Player/PlayerEffects/frame effect 10.png");
		SmokeRun11= Loader.ImageLoader("/Player/PlayerEffects/frame effect 11.png");
		SmokeRun12= Loader.ImageLoader("/Player/PlayerEffects/frame effect 12.png");
		SmokeRun13= Loader.ImageLoader("/Player/PlayerEffects/frame effect 13.png");
		
		Shoot= Loader.ImageLoader("/Player/PlayerEffects/frame Shoot 1.png");
		
		Win= Loader.ImageLoader("/Player/GANASTE.png");
		
		Map= Loader.ImageLoader("/Player/Mapajuego.jpg");
	}
	
}
