package gamePackage;
import java.awt.geom.Point2D;


public class Obstacle extends Point2D {
	private double xPosition = 450;
	private double yPosition = 450;
	
	private String sprite;
	private final int OBSTACLE_SPEED = 15;
	protected HitBox obstacleBox;
	
	Obstacle(){
		this.obstacleBox = new HitBox((int)this.xPosition,(int) this.yPosition, 150,150);

	}
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.xPosition;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.yPosition;
	}
	
	public void scroll() {
		this.setLocation(this.getX() - OBSTACLE_SPEED, this.getY());
	}
	
	@Override
	public void setLocation(double x, double y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	
	public int getObstacleSpeed() {
		return this.OBSTACLE_SPEED;
	}
}
