public class HungryCaterpillar {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println("halo world");
		
//		Canvas aCanvas = new Canvas();
//
//		JFrame window = new JFrame("HungryCaterpillar");
//
//		window.add(aCanvas);
//
//		window.setResizable(false);
//		window.pack();
//
//		window.setLocationRelativeTo(null);
//		window.setVisible(true);
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameWindow win = new GameWindow();
		win.show();
		
	}
}
