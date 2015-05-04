import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener{
	private Mediator mediator;
	
	public Input(Mediator mediator){
		this.mediator = mediator;
		addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_SPACE){
			this.mediator.turnRight();
		}
		else if(keyCode == KeyEvent.VK_ESCAPE){
			this.mediator.pause();
		}
	}
}
