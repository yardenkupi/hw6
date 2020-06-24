package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.BoundingSphereTree;
import edu.cg.models.IRenderable;

public class Wheel implements IRenderable {
	
	private BoundingSphereTree boundingSphereTree = new BoundingSphereTree();
	//Initialize bounding sphere with the correct dimension specified
	private BoundingSphere boundingSphere  = new BoundingSphere(Specification.TIRE_RADIUS * 1.25,new Point(0, 0 ,0));


	//create a bounding sphere for a wheel object
	public Wheel(){
		boundingSphere.setSphereColore3d(0,1,1);
		boundingSphereTree.setBoundingSphere(boundingSphere);
	}
	
	
	public BoundingSphereTree getBoundingSphereTree() {
		return boundingSphereTree;
	}


	public void setBoundingSphereTree(BoundingSphereTree boundingSphereTree) {
		this.boundingSphereTree = boundingSphereTree;
	}


	@Override
	public void render(GL2 gl) {
		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		// Render Tire:
		Materials.setMaterialTire(gl);
		gl.glPushMatrix();
		gl.glTranslated(0, 0, -1.0 * Specification.TIRE_DEPTH / 2.0);
		glu.gluCylinder(quad, Specification.TIRE_RADIUS, Specification.TIRE_RADIUS, Specification.TIRE_DEPTH, 20, 1);
		gl.glRotated(180.0, 1.0, 0.0, 0.0);
		glu.gluDisk(quad, 0.8 * Specification.TIRE_RADIUS, Specification.TIRE_RADIUS, 20, 1);
		gl.glRotated(180.0, 1.0, 0.0, 0.0);
		gl.glTranslated(0, 0, Specification.TIRE_DEPTH);
		glu.gluDisk(quad, 0.8 * Specification.TIRE_RADIUS, Specification.TIRE_RADIUS, 20, 1);
		// Render Rims:
		Materials.setMaterialRims(gl);
		glu.gluDisk(quad, 0.0, 0.3 * Specification.TIRE_RADIUS, 20, 1);
		Materials.setMaterialTire(gl);
		glu.gluDisk(quad, 0.0, 0.4 * Specification.TIRE_RADIUS, 20, 1);
		Materials.setMaterialRims(gl);
		glu.gluDisk(quad, 0.0, 0.5 * Specification.TIRE_RADIUS, 20, 1);
		Materials.setMaterialTire(gl);
		glu.gluDisk(quad, 0.0, 0.6 * Specification.TIRE_RADIUS, 20, 1);
		Materials.setMaterialRims(gl);
		glu.gluDisk(quad, 0.0, 0.7 * Specification.TIRE_RADIUS, 20, 1);
		Materials.setMaterialTire(gl);
		glu.gluDisk(quad, 0.0, 0.8 * Specification.TIRE_RADIUS, 20, 1);
		Materials.setMaterialRims(gl);
		gl.glTranslated(0.0,0.0, -1*Specification.TIRE_DEPTH);
		gl.glRotated(180.0, 1.0, 0.0, 0.0);
		glu.gluDisk(quad, 0.0, 0.8 * Specification.TIRE_RADIUS, 20, 1);
		
		gl.glPopMatrix();
		glu.gluDeleteQuadric(quad);
	}

	@Override
	public void init(GL2 gl) {
	}
	
	@Override
	public String toString() {
		return "Wheel";
	}


	@Override
	public void destroy(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

}
