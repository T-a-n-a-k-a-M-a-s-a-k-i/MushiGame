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

		if((Math.abs(x-aoX)<width/2+aoWidth/2) && (Math.abs(y-aoY)<height/2+aoHeight/2)){
			anActiveObject.hitJoint();
		}
	}
}