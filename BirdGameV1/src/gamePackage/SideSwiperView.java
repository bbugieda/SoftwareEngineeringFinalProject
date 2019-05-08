package gamePackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class SideSwiperView extends View {
	
	private Image g1;
	private Image map;
	BufferedImage[] migrationMap;
	
	public int imgVelX = 0;
	private int scaledImageWidth = Model.scaledImageWidth;
	private int scaledImageHeight = Model.scaledImageHeight;
	
	private BufferedImage airplaneImg;
	private BufferedImage healthImg;
	private BufferedImage healthIcon;
	private BufferedImage cloudQuestionBox;
	private BufferedImage thunderCloud;
			
	double birdX, birdY, planeX, planeY, cloudQuestionX, cloudQuestionY, fishX, fishY, thunderCloudX, thunderCloudY;
	
	private int imgWidth = 150;
	private String birdImagePath = "src/images/osprey_frames.png";
	private int birdFrameCount = 22;
	private BufferedImage[] bird_imagesBufferedImage;
	private BufferedImage[] fishFrames;
	private int picNum = 0;
	private short picNumFish = 0;
	private int picNumMap = 0;
	private int tick = 0;
	
	private int health;
	private int healthCount;
	
	private Bird b;
	private GameObject plane;
	private GameObject thunderCloudObj;
	private GameObject cloudQuestionBoxObj;
	private GameObject food;
	
	private final int MIGRATION_MAP_SUBIMAGES = 9;
	private final int BIRD_IMG_SIZE = 150;
	private final int FISH_IMG_WIDTH = 100;
	private final int FISH_IMG_HEIGHT = 65;
	private final int NUMBER_FISH_FRAMES = 4;
	private final int MAP_FRAME_COUNT = 30;
	private final int HEALTH_BIRD_OFFSET = 30;
	private final int HEALTH_IMG_X = scaledImageWidth - 350;
	private final int HEALTH_IMG_Y = 20;
	private final int MAP_X = 0;
	private final int MAP_Y = 0;
	
	public SideSwiperView() {
		super();
		this.loadImage();
	}
	
	/**
	 * loadImage()
	 * Loads in the images for the objects used for the SideSwiper mini-game. 
	 */
	private void loadImage() {
		ImageIcon grassyBackground = new ImageIcon("src/images/grass3.png");
        
        migrationMap = new BufferedImage[MIGRATION_MAP_SUBIMAGES];
        migrationMap[0] = super.createImage("src/images/migrationMiniMap1.png");
        migrationMap[1] =  super.createImage("src/images/migrationMiniMap2.png");
        migrationMap[2] =  super.createImage("src/images/migrationMiniMap3.png");
        migrationMap[3] =  super.createImage("src/images/migrationMiniMap4.png");
        migrationMap[4] =  super.createImage("src/images/migrationMiniMap5.png");
        migrationMap[5] =  super.createImage("src/images/migrationMiniMap6.png");
        migrationMap[6] = super.createImage("src/images/migrationMiniMap7.png");
        migrationMap[7] =  super.createImage("src/images/migrationMiniMap8.png");
        migrationMap[8] = super.createImage("src/images/migrationMiniMap9.png");
        
        
		airplaneImg = super.createImage("src/images/airplane.png");
		healthImg = super.createImage("src/images/health.png");
		healthIcon = super.createImage("src/images/birdHealth.png");
		cloudQuestionBox = super.createImage("src/images/cloudQuestionMark.png");
		thunderCloud = super.createImage("src/images/thunderCloud.png");
		
		g1 = grassyBackground.getImage().getScaledInstance(scaledImageWidth, scaledImageHeight, Image.SCALE_DEFAULT);
		
		BufferedImage birdFrames = super.createImage(birdImagePath);
		BufferedImage fishAnimation = super.createImage("src/images/fishFrames.png");
		bird_imagesBufferedImage = new BufferedImage[birdFrameCount];
		fishFrames = new BufferedImage[NUMBER_FISH_FRAMES];
		
		for (int i = 0; i < birdFrameCount; i++)
			bird_imagesBufferedImage[i] = birdFrames.getSubimage(imgWidth * i, 0, imgWidth, BIRD_IMG_SIZE);
		
		for (int i = 0; i < NUMBER_FISH_FRAMES; i++)
			fishFrames[i] = fishAnimation.getSubimage(FISH_IMG_WIDTH * i, 0, FISH_IMG_WIDTH, FISH_IMG_HEIGHT);
		
		setDoubleBuffered(true);
	}
	
	/**
	 * paintComponent()
	 * This method overrides the View's paintComponent(). It draws all of our images on the screen and sets them
	 * to be the appropriate starting locations that we have defined using constants.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		Graphics2D g3 = (Graphics2D) g.create();
		
		picNumFish = (short) ((picNumFish + 1) % NUMBER_FISH_FRAMES);
		picNum = (picNum + 1) % birdFrameCount;
		imgVelX-=1;
		
		if (imgVelX % scaledImageWidth == 0) {
			imgVelX = 0;
		}
		
		tick = (tick+1) % MAP_FRAME_COUNT;
		
		if (tick == 0) {
			picNumMap = (picNumMap + 1) % MIGRATION_MAP_SUBIMAGES;
		}
		g2.drawImage(g1, (imgVelX % scaledImageWidth), 0, null); // draws image in the window
		g2.drawImage(g1, ((imgVelX % scaledImageWidth)+scaledImageWidth), 0, null); // draws image in the window, had to make second image the same as the first for continuity
	    
		g2.drawImage(bird_imagesBufferedImage[picNum], (int)birdX, (int)birdY, null);
		
		g2.drawImage(this.thunderCloud, (int)thunderCloudX, (int)thunderCloudY, null);
		g2.drawImage(this.cloudQuestionBox, (int)cloudQuestionX, (int)cloudQuestionY, null);

		g2.drawImage(airplaneImg, (int)planeX, (int)planeY, null);
		g2.drawImage(fishFrames[picNumFish], (int)fishX, (int)fishY, null);
		
		g3.drawImage(healthImg, HEALTH_IMG_X, HEALTH_IMG_Y, null);
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		SAVE THIS CODE FOR TESTING PURPOSES - DRAWS THE HIT BOXES ON THE OBJECTS
//		g3.drawRect((int)this.b.getBirdBox().getX(), (int)this.b.getBirdBox().getY(), (int)this.b.getBirdBox().getWidth(), (int)this.b.getBirdBox().getHeight());
//		g3.drawRect((int)this.plane.GameObjectBox.getX(), (int)this.plane.GameObjectBox.getY(), (int)this.plane.GameObjectBox.getWidth(), (int)this.plane.GameObjectBox.getHeight());
//		g3.drawRect((int)this.thunderCloudObj.GameObjectBox.getX(), (int)this.thunderCloudObj.GameObjectBox.getY(), (int)this.thunderCloudObj.GameObjectBox.getWidth(), (int)this.thunderCloudObj.GameObjectBox.getHeight());
//		g3.drawRect((int)this.cloudQuestionBoxObj.GameObjectBox.getX(), (int)this.cloudQuestionBoxObj.GameObjectBox.getY(), (int)this.cloudQuestionBoxObj.GameObjectBox.getWidth(), (int)this.cloudQuestionBoxObj.GameObjectBox.getHeight());
//		g3.drawRect((int)this.food.GameObjectBox.getX(), (int)this.food.GameObjectBox.getY(), (int)this.food.GameObjectBox.getWidth(), (int)this.food.GameObjectBox.getHeight());
//		-----------------------------------------------------------------------------------------------------------------------------
		
		g3.drawImage(migrationMap[picNumMap],MAP_X, MAP_Y, null);
		for (int i = 0; i < healthCount; i++) {
			g2.drawImage(healthIcon, HEALTH_IMG_X + (HEALTH_BIRD_OFFSET * i), HEALTH_IMG_Y, null);
		}
	}

	/**
	 * update()
	 * Updates the x-position and y-position of the game objects using getters.
	 * This is method is called from the Controller.
	 */
	@Override
	public void update(ArrayList<GameObject> list) {
		
		this.b = (Bird) list.get(0);
		this.plane = list.get(1);
		this.thunderCloudObj = list.get(2);
		this.cloudQuestionBoxObj = list.get(3);
		this.food = list.get(4);
		
		this.birdX = b.getX();
		this.birdY = b.getY();
		
		this.planeX = plane.getX();
		this.planeY = plane.getY();
		
		this.cloudQuestionX = cloudQuestionBoxObj.getX();
		this.cloudQuestionY = cloudQuestionBoxObj.getY();
		
		this.thunderCloudX = thunderCloudObj.getX();
		this.thunderCloudY = thunderCloudObj.getY();
		
		this.fishX = food.getX();
		this.fishY = food.getY();

		this.healthCount = b.getHealthCount();
	}
}