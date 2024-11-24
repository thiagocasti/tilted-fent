package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import graphics.Assets;
import input.KeyBoard;
import states.GameState;
import states.MenuState;
import states.State;

public class Window extends JFrame implements Runnable {

    public static final int WIDTH = 1400, HEIGHT = 800;
    private Canvas canvas;
    private Thread thread;

    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double TARGETTIME = 1000000000 / FPS;
    private double delta = 0;
    private int AVERAGEFPS = FPS;

    private GameState gameState;
    private MenuState menuState;
    private KeyBoard keyBoard;
    private State currentState;

    public Window() {
        setTitle("Tilted Fent");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        canvas = new Canvas();
        keyBoard = new KeyBoard();

        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);

        add(canvas);
        canvas.addKeyListener(keyBoard);
    }

    public static void main(String[] args) {
        new Window().start();
    }

    private void update() {
        keyBoard.update();
        if (currentState == State.MENU) {
            menuState.update();
            if (menuState.selectedOption==0) {
                currentState = State.GAME;
            } else if (menuState.selectedOption==1) {
                System.exit(0);
            }
        } else if (currentState == State.GAME) {
            gameState.update();
        }
    }

    private void draw() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (currentState == State.MENU) {
            menuState.draw(g);
        } else if (currentState == State.GAME) {
            gameState.draw(g);
            if (gameState.Lvl >= 30) {
                gameState.Win(g);
            }
        }

        g.dispose();
        bs.show();
    }

    private void init() {
        Assets.init();
        gameState = new GameState();
        menuState = new MenuState();
        currentState = State.MENU;
    }

    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / TARGETTIME;
            time += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000) {
                AVERAGEFPS = frames;
                frames = 0;
                time = 0;
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
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}