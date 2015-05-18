import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener{
	private Container contentPane = getContentPane();
	private GameMediator mediator;
	private Canvas canvas;
	
	public GameWindow(){
		super("Hungry Caterpillar");
		
		setSize(300, 200);
		setLocationRelativeTo(null);	
		setTitleComponents();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public GameWindow(GameMediator mediator){
		super("Hungry Caterpillar");
		
		this.mediator = mediator;
		
		setSize(300, 200);
		setLocationRelativeTo(null);	
		setTitleComponents();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setTitleComponents(){
		contentPane.removeAll();
		
		JLabel titleLabel = new JLabel("Caterpillar Game");
		JButton startGameButton = new JButton("Start!");
		JButton quitGameButton = new JButton("Quit");
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		startGameButton.addActionListener(this);
		quitGameButton.addActionListener(this);
		
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		
		titlePanel.add(titleLabel, BorderLayout.NORTH);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(startGameButton);
		buttonPanel.add(quitGameButton);
		
		contentPane.add(titlePanel, BorderLayout.NORTH);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
		repaint();
	}
	
	public void setGameComponents(){
		this.canvas = new Canvas(this);
		
		contentPane.removeAll();
		contentPane.add(this.canvas);
		
		this.setResizable(false);
		this.pack();

		this.setVisible(true);
		repaint();
		this.canvas.requestFocus();
	}
	
	public void setGameOverComponents(int score){
		setSize(300, 200);
		contentPane.removeAll();
		
		JLabel titleLabel = new JLabel("Game Over");
		JLabel scoreLabel = new JLabel("Score: " + Integer.toString(score));
		JButton restartGameButton = new JButton("Restart");
		JButton quitGameButton = new JButton("Quit");
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		restartGameButton.addActionListener(this);
		quitGameButton.addActionListener(this);
		
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		titlePanel.add(scoreLabel, BorderLayout.CENTER);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(restartGameButton);
		buttonPanel.add(quitGameButton);
		
		contentPane.add(titlePanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);		
		
		setVisible(true);
		repaint();
	}
	
	public Canvas getCanvas(){
		return this.canvas;
	}
	
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		JButton btn = (JButton)obj;
		String labelString = btn.getLabel();
		
		if("Start!".equals(labelString) || "Restart".equals(labelString)){
			setGameComponents();
		}
		else if("Quit".equals(labelString)){
			System.exit(0);
		}
	}
}