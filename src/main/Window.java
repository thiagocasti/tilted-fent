package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import graphics.Assets;
import input.KeyBoard;
import states.GameState;


public class Window extends JFrame implements Runnable {
	
	public static final int WIDTH=1920, HEIGHT=1080;
	private Canvas canvas;
	private Thread thread;
	
	private boolean running= false;
	//VARIABLES PARA GRAFICO
	private BufferStrategy bs;
	private Graphics g;
	
	private final int FPS= 60;
	private double TARGETTIME = 1000000000/FPS;
	//delta almacena tiempo
	private double delta =0;
	private int AVERAGEFPS=	FPS;
	
	private GameState gameState;
	private KeyBoard keyBoard;
	public Window()
	{
		setTitle("Tilted Fent");
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		canvas= new Canvas();
		keyBoard= new KeyBoard();
		
		canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		canvas.setFocusable(true);
		
		add(canvas);
		canvas.addKeyListener(keyBoard);
	}
	
	public static void main(String[] args) 
	{
		new Window().start();
	}
	
	private void update() 
	{
		keyBoard.update();
		gameState.update();
	}
	private void draw() 
	{
		bs= canvas.getBufferStrategy();
		
		if(bs==null) 
		{
			canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		//empieza draw
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawString(""+ AVERAGEFPS, 10, 10);
		//dibuja el estado
		gameState.draw(g);
		
		
		
		//termina draw
		
		g.dispose();
		bs.show();
	}
	//carga los recursos
	private void init() 
	{
		Assets.init();
		gameState= new GameState();
	}
	
	@Override
	
	//bucle principal
	public void run() {
		
		long now=0;
		long lastTime = System.nanoTime();
		int frames =0;
		long time =0;
		
		init();
		//se encarga de actualizar SOLO cuando pasa un fotograma
		while(running) 
		{
			now= System.nanoTime();
			delta += (now-lastTime)/TARGETTIME;
			time +=(now-lastTime);
			lastTime=now;
			
			if(delta>=1) {
				update();
				draw();
				delta--;
				frames++;
			}
			if(time>= 1000000000) 
			{
				AVERAGEFPS =frames;
				frames =0;
				time=0;
			}
			
		}
		
		stop();
	}
	
	private void start() {
		
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	private void stop() {
		try {
			thread.join();
			running=false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}