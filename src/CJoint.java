import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class CJoint extends Entity implements PassiveObject {
	private int aoX, aoY, aoWidth, aoHeight;

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
		aoX = anActiveObject.getCenterX();
		aoY = anActiveObject.getCenterY();
		aoWidth = anActiveObject.getWidth();
		aoHeight = anActiveObject.getHeight();

		if((Math.abs(getCenterX()-aoX)<width/8+aoWidth/8) && (Math.abs(getCenterY()-aoY)<height/8+aoHeight/8)){
			anActiveObject.hitJoint();
		}
	}
}