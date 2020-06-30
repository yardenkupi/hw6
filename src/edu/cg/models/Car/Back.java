package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.BoundingSphereTree;
import edu.cg.models.IIntersectable;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Back implements IRenderable, IIntersectable {
	private SkewedBox baseBox = new SkewedBox(Specification.B_BASE_LENGTH, Specification.B_BASE_HEIGHT,
			Specification.B_BASE_HEIGHT, Specification.B_BASE_DEPTH, Specification.B_BASE_DEPTH);
	private SkewedBox backBox = new SkewedBox(Specification.B_LENGTH, Specification.B_HEIGHT_1,
			Specification.B_HEIGHT_2, Specification.B_DEPTH_1, Specification.B_DEPTH_2);
	private PairOfWheels wheels = new PairOfWheels();
	private Spolier spoiler = new Spolier();

	/** initialize new bounding sphere tree to contain sub bounding spheres inside the object
	 * - namely the spheres of the two wheels*/

	private BoundingSphere boundingSphere  = new BoundingSphere(Specification.B_LENGTH * 0.6 + 0.2 * Specification.B_HEIGHT,new Point(0.0, 0.6 * Specification.B_HEIGHT,0));
	BoundingSphereTree boundingSphereTree = new BoundingSphereTree();


	public Back() {
		wheels.getrWheel().getBoundingSphereTree().getBoundingSphere().translateCenter(-Specification.B_LENGTH / 2.0 + Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS, 0.5 * Specification.B_DEPTH - 1.1 * Specification.TIRE_DEPTH);
		wheels.getlWheel().getBoundingSphereTree().getBoundingSphere().translateCenter(-Specification.B_LENGTH / 2.0 + Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS, -0.5 * Specification.B_DEPTH + 1.1 * Specification.TIRE_DEPTH);
	}
	
	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		Materials.SetBlackMetalMaterial(gl);
		gl.glTranslated(Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0, 0.0, 0.0);
		baseBox.render(gl);
		Materials.SetRedMetalMaterial(gl);
		gl.glTranslated(-1.0 * (Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0),
				Specification.B_BASE_HEIGHT, 0.0);
		backBox.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 + Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS,
				0.0);
		wheels.render(gl);
		
		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		gl.glTranslated(-Specification.B_LENGTH / 5.0 , 0,-2 * Specification.PAIR_OF_WHEELS_ROD_RADIUS);
		gl.glRotated(90, 0, 1, 0);

		//BONUS - added to exhaust pipes!
		glu.gluCylinder(quad, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS * 4, 20, 1);
		gl.glTranslated(-4 * Specification.PAIR_OF_WHEELS_ROD_RADIUS , 0, 0);
		glu.gluCylinder(quad, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS * 4, 20, 1);
		gl.glPopMatrix();
		gl.glPushMatrix();
		
		
		gl.glTranslated(-Specification.B_LENGTH / 2.0 + 0.5 * Specification.S_LENGTH,
				0.5 * (Specification.B_HEIGHT_1 + Specification.B_HEIGHT_2), 0.0);
		spoiler.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {

	}


	/** Generate all spheres of the bounding sphere and color them green
	 * each wheel has a bounding sphere, and the entire back has a bounding sphere*/
	@Override
	public BoundingSphereTree getBoundingSpheres() {

		this.boundingSphere.setSphereColore3d(0,0,1);
		boundingSphereTree.setBoundingSphere(this.boundingSphere);
		boundingSphereTree.getList().add(wheels.getlWheel().getBoundingSphereTree());
		boundingSphereTree.getList().add(wheels.getrWheel().getBoundingSphereTree());
		return boundingSphereTree;
	}

	@Override
	public void destroy(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

}
