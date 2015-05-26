package Func;
public class ConfigSingleton {
	private static ConfigSingleton instance = null;

	private final int collisionDetectionDistance = -5;
	private final int obstacleMinDistance = 32;
	private final int obstacleTreeNumber = 5;
	private final int initialJointNumber = 3;
	private final int canvasHeight = 240;			//15
	private final int canvasWidth = 320;			//20
	private final int entityHeight = 16;
	private final int entityWidth = 16;
	private final int speedIncrement = 1;
	private final int speedInitial = 1;
	private final int scale = 2;
	private final int fps = 30;
	
	public ConfigSingleton(){}
	
	public static ConfigSingleton getInstance(){
		if(null == instance){
			instance = new ConfigSingleton();
		}
		
		return instance;
	}
	
	public int getObstacleTreeNumber(){
		return obstacleTreeNumber;
	}
	
	public int getObstacleMinDistance(){
		return obstacleMinDistance;
	}

	public int getEntityWidth() {
		return entityWidth;
	}

	public int getEntityHeight() {
		return entityHeight;
	}

	public int getCanvasWidth(){
		return canvasWidth;
	}
	
	public int getCanvasHeight(){
		return canvasHeight;
	}
	
	public int getCanvasHeightWithMenu(){
		return canvasHeight + 16;
	}
	
	public int getScale(){
		return scale;
	}
	
	public int getTargetTime(){
		return 1000 / fps;
	}
	
	public int getSpeedInitial(){
		return speedInitial;
	}
	
	public int getSpeedIncrement(){
		return speedIncrement;
	}
	
	public int getInitialJointNumber(){
		return initialJointNumber;
	}
	
	public int getCollisionDetectionDistance(){
		return collisionDetectionDistance;
	}
}
