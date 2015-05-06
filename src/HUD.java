import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class HUD {
	private static BufferedImage hudImage;
	
	public HUD(){
		try {
			if(null == hudImage){
				BufferedImage tileSet = ImageIO.read(getClass().getResourceAsStream("images/bar.png"));
				hudImage = tileSet.getSubimage(0, 0, 320, 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d){
		if(null != hudImage){
			g2d.drawImage(hudImage, 0, Canvas.HEIGHT, null);
		}
	}
}
