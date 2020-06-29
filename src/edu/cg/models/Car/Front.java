package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;
import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.BoundingSphereTree;
import edu.cg.models.IIntersectable;
import edu.cg.models.IRenderable;

public class Front implements IRenderable, IIntersectable {

	private FrontHood hood = new FrontHood();
	private PairOfWheels wheels = new PairOfWheels();
	private FrontBumber bumper = new FrontBumber();


	/** Initialize new bounding sphere tree to contain sub bounding spheres inside the object
	 * - namely the spheres of the two wheels*/
	private BoundingSphere boundingSphere  = new BoundingSphere(Specification.F_LENGTH*0.6,new Point(0.0 ,Specification.F_HEIGHT * 0.5,0));
	BoundingSphereTree boundingSphereTree = new BoundingSphereTree();


	/** Initialize two wheel spheres that suround them */
	public Front() {
		wheels.getrWheel().getBoundingSphereTree().getBoundingSphere().translateCenter(0.8 * Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS, 0.5 * Specification.B_DEPTH - 1.1 * Specification.TIRE_DEPTH);
		wheels.getlWheel().getBoundingSphereTree().getBoundingSphere().translateCenter(0.8 * Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS, -0.5 * Specification.B_DEPTH + 1.1 * Specification.TIRE_DEPTH);
	
	
		wheels.getrWheel().getBoundingSphereTree().getBoundingSphere().setOriginalCenter(wheels.getrWheel().getBoundingSphereTree().getBoundingSphere().getCenter());
		wheels.getlWheel().getBoundingSphereTree().getBoundingSphere().setOriginalCenter(wheels.getlWheel().getBoundingSphereTree().getBoundingSphere().getCenter());
	}
	
	
	@Override
	public void render(GL2 gl) {
		// the car.
		gl.glPushMatrix();
		// Render hood - Use Red Material.
		gl.glTranslated(-Specification.F_LENGTH / 2.0 + Specification.F_HOOD_LENGTH / 2.0, 0.0, 0.0);

		hood.render(gl);
		// Render the wheels.
		gl.glTranslated(Specification.F_HOOD_LENGTH / 2.0 - 1.25 * Specification.TIRE_RADIUS,
				0.5 * Specification.TIRE_RADIUS, 0.0);
		wheels.render(gl);
		gl.glTranslated( 2.25 * Specification.TIRE_RADIUS ,-0.5 * Specification.TIRE_RADIUS ,0.0);

		bumper.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
	}


	/** Generate all spheres of the bounding sphere and color them green
	 * each wheel has a bounding sphere, and the entire back has a bounding sphere*/
	@Override
	public BoundingSphereTree getBoundingSpheres() {
		this.boundingSphere.setSphereColore3d(1,0,0);
		boundingSphereTree.setBoundingSphere(this.boundingSphere);
		boundingSphereTree.getList().add(wheels.getlWheel().getBoundingSphereTree());
		boundingSphereTree.getList().add(wheels.getrWheel().getBoundingSphereTree());
		return boundingSphereTree;
	}

	@Override
	public String toString() {
		return "CarFront";
	}


	@Override
	public void destroy(GL2 gl) {
		// TODO Auto-generated method stub
		
	}



}
