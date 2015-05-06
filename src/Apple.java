import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Apple extends Entity implements PassiveObject {
	public Apple(){
		super();
		loadImage();
	}
	
	public Apple(int posx, int posy){
		super(posx, posy);
		
		loadImage();
	}
	
	private void loadImage(){
		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream("images/apple.gif"));
			setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void collisionCheck(ActiveObject anActiveObject) {
	}

}
