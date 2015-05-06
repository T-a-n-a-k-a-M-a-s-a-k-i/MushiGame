import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class CJoint extends Entity implements PassiveObject {
	public CJoint(){
		super();
		loadImage();
	}
	
	public CJoint(int posx, int posy){
		super(posx, posy);
		
		loadImage();
	}
	
	private void loadImage(){
		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream("images/joint.gif"));
			setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void collisionCheck(ActiveObject anActiveObject) {
	}

}
