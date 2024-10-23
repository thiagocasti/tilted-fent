package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{
	
	private boolean[] keys = new boolean[256];
	
	public static boolean W,S,D,A,K;
	
	
 public KeyBoard() 
 {
	 W=false;
	 S=false;
	 D=false;
	 A=false;
	 K=false;
 }

 public void update() 
 {
	 W =keys[KeyEvent.VK_W];
	 S =keys[KeyEvent.VK_S];
	 D =keys[KeyEvent.VK_D];
	 A =keys[KeyEvent.VK_A];
	 K =keys[KeyEvent.VK_K];
 }
 
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	keys[e.getKeyCode()]=true;
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	keys[e.getKeyCode()]=false;
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
 
	
}
