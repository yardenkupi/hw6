package edu.cg;

import java.awt.Component;
import java.util.List;

import javax.swing.JOptionPane;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import edu.cg.algebra.Vec;
import edu.cg.models.BoundingSphere;
import edu.cg.models.BoundingSphereTree;
import edu.cg.models.Track;
import edu.cg.models.TrackSegment;
import edu.cg.models.Car.F1Car;
import edu.cg.models.Car.Specification;
import edu.cg.algebra.Point;
/**
 * An OpenGL 3D Game.
 *
 */
public class NeedForSpeed implements GLEventListener {
	private GameState gameState = null; // Tracks the car movement and orientation
	private F1Car car = null; // The F1 car we want to render
	private Vec carCameraTranslation = null; // The accumulated translation that should be applied on the car, camera
												// and light sources
	private Track gameTrack = null; // The game track we want to render
	private FPSAnimator ani; // This object is responsible to redraw the model with a constant FPS
	private Component glPanel; // The canvas we draw on.
	private boolean isModelInitialized = false; // Whether model.init() was called.
	private boolean isDayMode = true; // Indicates whether the lighting mode is day/night.
	private boolean isBirdseyeView = false; // Indicates whether the camera is looking from above on the scene or
											// looking
	private Point cameraInitialPositionThirdPerson;
	private Point cameraInitialPositionBirdsEye;
	// towards the car direction.
	// - Car initial position (should be fixed).
	// - Camera initial position (should be fixed)
	// - Different camera settings
	// - Light colors
	// Or in short anything reusable - this make it easier for your to keep track of your implementation.
	
	public NeedForSpeed(Component glPanel) {
		this.glPanel = glPanel;
		gameState = new GameState();
		gameTrack = new Track();
		carCameraTranslation = new Vec(0.0);
		car = new F1Car();
		cameraInitialPositionThirdPerson = new Point(0,2,4);
		cameraInitialPositionBirdsEye = new Point(0,50,22);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		if (!isModelInitialized) {
			initModel(gl);
		}
		if (isDayMode) {
			// use gl.glClearColor() function.
			gl.glClearColor(0.52f,0.85f,1,0);

		} else {

			gl.glClearColor(0,0,0.4f,0);
		}
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		// Step (1) Update the accumulated translation that needs to be
		// applied on the car, camera and light sources.
		updateCarCameraTranslation(gl);
		// Step (2) Position the camera and setup its orientation
		setupCamera(gl);
		// Step (3) setup the lights.
		setupLights(gl);
		// Step (4) render the car.
		renderCar(gl);
		// Step (5) render the track.
		renderTrack(gl);
		// Step (6) check collision. Note this has nothing to do with OpenGL.
		if (checkCollision()) {
			JOptionPane.showMessageDialog(this.glPanel, "Game is Over");
			this.gameState.resetGameState();
			this.carCameraTranslation = new Vec(0.0);
		}

	}

	/**
	 * @return Checks if the car intersects the one of the boxes on the track.
	 */
	private boolean checkCollision() {
		// TODO: Implement this function to check if the car collides into one of the boxes.
		// You can get the bounding spheres of the track by invoking: 
//		 List<BoundingSphereTree> trackBoundingSpheres = gameTrack.getBoundingSpheres();
		return false;
	}

	private void updateCarCameraTranslation(GL2 gl) {
		// Update the car and camera translation values (not the ModelView-Matrix).
		// - Always keep track of the car offset relative to the starting
		// point.
		// - Change the track segments here.
		Vec ret = gameState.getNextTranslation();
		carCameraTranslation = carCameraTranslation.add(ret);
		double dx = Math.max(carCameraTranslation.x, -TrackSegment.ASPHALT_TEXTURE_DEPTH / 2.0 - 2);
		carCameraTranslation.x = (float) Math.min(dx, TrackSegment.ASPHALT_TEXTURE_DEPTH / 2.0 + 2);
		if (Math.abs(carCameraTranslation.z) >= TrackSegment.TRACK_LENGTH + 10.0) {
			carCameraTranslation.z = -(float) (Math.abs(carCameraTranslation.z) % TrackSegment.TRACK_LENGTH);
			gameTrack.changeTrack(gl);
		}
	}

	private void setupCamera(GL2 gl) {
			 GLU glu = new GLU();
		if (isBirdseyeView) {
			glu.gluLookAt(this.carCameraTranslation.x,this.carCameraTranslation.y+50,(this.carCameraTranslation.z)-39, this.carCameraTranslation.x, this.carCameraTranslation.y, this.carCameraTranslation.z-39, 0,0,-1);
		} else {
			glu.gluLookAt(this.carCameraTranslation.x,2.0+ this.carCameraTranslation.y, 4 + this.carCameraTranslation.z, this.carCameraTranslation.x,this.carCameraTranslation.y-2, this.carCameraTranslation.z-10.0,0,1,0);
		}
	}

	private void setupLights(GL2 gl) {
		if (this.isDayMode) {
			gl.glDisable(GL2.GL_LIGHT1);
			gl.glDisable(GL2.GL_LIGHT2);
			Vec dir = new Vec(0.0, 1.0, 1.0).normalize();
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,new float[]{0f, 0f, 0f, 0f}, 0);
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[]{dir.x, dir.y, dir.z, 0.0f}, 0);
			gl.glEnable(GL2.GL_LIGHT0);

		} else {
			gl.glDisable(GL2.GL_LIGHT0);
			gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[]{0.2f, 0.2f, 0.2f, 1.0f}, 0);
			float[] position1 = {this.carCameraTranslation.x, this.carCameraTranslation.y + 1+ (float)Specification.TIRE_RADIUS, -6.5f+this.carCameraTranslation.z+(float)(Specification.F_BUMPER_DEPTH /2), 1.0f};
			this.spotLight(gl, GL2.GL_LIGHT2, position1);
			float[] position2 = {this.carCameraTranslation.x, this.carCameraTranslation.y +1 +(float)Specification.TIRE_RADIUS, -6.5f+this.carCameraTranslation.z - (float)(Specification.F_BUMPER_DEPTH /2), 1.0f};
			this.spotLight(gl, GL2.GL_LIGHT1, position2);
		}
	}

	private void spotLight(GL2 gl, int light, float[] position) {
		gl.glLightfv(light, GL2.GL_DIFFUSE, new float[]{0.5f, 0.5f, 0.5f, 1.0f}, 0);
		gl.glLightfv(light, GL2.GL_SPECULAR, new float[]{0.5f, 0.5f, 0.5f, 1.0f}, 0);
		gl.glLightfv(light, GL2.GL_SPOT_DIRECTION, new float[]{((float)Math.sin(this.gameState.getCarRotation())), 0f, -(float)Math.cos((this.gameState.getCarRotation()))}, 0);
		gl.glLightfv(light, GL2.GL_POSITION, position, 0);
		gl.glLightf(light, GL2.GL_SPOT_CUTOFF, 90.0f);
		gl.glEnable(light);
	}

	private void renderTrack(GL2 gl) {
		// * Note: the track is not translated. It should be fixed.
		gl.glPushMatrix();
		gl.glTranslated(0,0,100);
		gameTrack.render(gl);
		gl.glPopMatrix();
	}

	private void renderCar(GL2 gl) {
		// * Remember: the car position should be the initial position + the accumulated translation.
		//             This will simulate the car movement.
		// * Remember: the car was modeled locally, you may need to rotate/scale and translate the car appropriately.
		// * Recommendation: it is recommended to define fields (such as car initial position) that can be used during rendering.
		gl.glPushMatrix();
		gl.glTranslated(this.carCameraTranslation.x,0.2 + this.carCameraTranslation.y, -1+ this.carCameraTranslation.z);
		gl.glScaled(4,4,4);
		gl.glRotated(90,0,1,0);
		gl.glRotated(-this.gameState.getCarRotation(),0,1,0);
		car.render(gl);
		gl.glPopMatrix();
	}

	public GameState getGameState() {
		return gameState;
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// Initialize display callback timer
		ani = new FPSAnimator(30, true);
		ani.add(drawable);
		glPanel.repaint();

		initModel(gl);
		ani.start();
	}

	public void initModel(GL2 gl) {
		gl.glCullFace(GL2.GL_BACK);
		gl.glEnable(GL2.GL_CULL_FACE);

		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_SMOOTH);

		car.init(gl);
		gameTrack.init(gl);
		isModelInitialized = true;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GLU glu = new GLU();
		double ratio = (double) width / (double) height;
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		if(isBirdseyeView){
			glu.gluPerspective(60.0, ratio, 2.0, 26.0);
		}else{
			glu.gluPerspective(80.0, ratio, 2.0, 1000.0);
		}
	}

	/**
	 * Start redrawing the scene with 30 FPS
	 */
	public void startAnimation() {
		if (!ani.isAnimating())
			ani.start();
	}

	/**
	 * Stop redrawing the scene with 30 FPS
	 */
	public void stopAnimation() {
		if (ani.isAnimating())
			ani.stop();
	}

	public void toggleNightMode() {
		isDayMode = !isDayMode;
	}

	public void changeViewMode() {
		isBirdseyeView = !isBirdseyeView;
	}

}
