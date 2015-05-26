package Item;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Func.ActiveObject;
import Func.PassiveObject;

public class Joint extends Entity implements PassiveObject {
	private static BufferedImage jointImage = null;
	
	public Joint(){
		super();
		loadImage();
	}
	
	private void loadImage(){
		try {
			if(null == jointImage){
				jointImage = ImageIO.read(getClass().getResourceAsStream("../images/joint.gif"));
			}
			setImage(jointImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean collisionCheck(ActiveObject anActiveObject) {
		Entity anEntity = (Entity)anActiveObject;
		
		int aoX = anEntity.getCenterX();
		int aoY = anEntity.getCenterY();
		int aoWidth = anEntity.getWidth();
		int aoHeight = anEntity.getHeight();

		if((Math.abs(getCenterX()-aoX)<this.getWidth()/8+aoWidth/8) 
				&& (Math.abs(getCenterY()-aoY)<this.getHeight()/8+aoHeight/8)){
			anActiveObject.hitJoint();
			return true;
		}
		
		return false;
	}
}