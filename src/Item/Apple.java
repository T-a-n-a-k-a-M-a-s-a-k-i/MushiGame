package Item;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Func.ActiveObject;
import Func.ConfigSingleton;
import Func.GameMediator;
import Func.PassiveObject;

public class Apple extends Entity implements PassiveObject {
	public Apple(){
		super();
		loadImage();
	}
	
	private void loadImage(){
		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream("../images/apple.gif"));
			setImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean collisionCheck(ActiveObject anActiveObject) {
		Entity anEntity = (Entity)anActiveObject;
		
		if(this.calDistanceWithEntity(anEntity) < ConfigSingleton.getInstance().getCollisionDetectionDistance()){
			anActiveObject.hitApple();
			GameMediator.getInstance().randomPlace(this);
			return true;
		}
		
		return false;
	}
}
