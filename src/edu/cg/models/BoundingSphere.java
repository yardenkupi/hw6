package edu.cg.models;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.Materials;
import edu.cg.models.Car.Specification;

public class BoundingSphere implements IRenderable {
	private double radius = 0.0;
	private Point center;
	private double color[];

	public BoundingSphere(double radius, Point center) {
		color = new double[3];
		this.setRadius(radius);
		this.setCenter(new Point(center.x, center.y, center.z));
	}

	public void setSphereColore3d(double r, double g, double b) {
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
	}

	/**
	 * Given a sphere s - check if this sphere and the given sphere intersect.
	 * 
	 * @return true if the spheres intersects, and false otherwise
	 */
	public boolean checkIntersection(BoundingSphere s) {
		//create a vector from the two centres
//		Vec distanceOfCentres = new Vec(s.center.sub(this.center));
		float checkDistSqr = this.center.distSqr(s.center);
		float check = (float)(this.radius + s.radius);

		//if the vector length is larger or equal to the sum of the two radiuses
		return this.center.distSqr(s.center) <= (float)(this.radius + s.radius);

	}


	/** Provided the translation matrix, we need to add the points to the current center and generate the new center according
	 * to the value provided
	 * @param dx - x value to translate
	 * @param dy - y value to translate
	 * @param dz - z value to translate
	 * @return void, but change bounding sphere center by the values recieved*/
	public void translateCenter(double dx, double dy, double dz) {
		double tranlateX = dx + this.center.x;
		double tranlateY = dy + this.center.y;
		double tranlateZ= dz+ this.center.z;
		this.center = new Point(tranlateX,tranlateY,tranlateZ);
	}

	@Override
	public void render(GL2 gl) {

		//initilize gl object
		gl.glPushMatrix();
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
		//we had to convert the color to float values
		Materials.SetMetalMaterial(gl,convertToFloat(this.color));
		gl.glTranslated(center.x,center.y,center.z);
		//use the built in gluSphere function in the openGL API
		glu.gluSphere(q, this.radius, 	10, 10);
		gl.glPopMatrix();

	}


	/**Helper function to convert color values
	 * @param color - color array of dimension (1,3)
	 * @return - color arry of float values*/
	private float[] convertToFloat(double[] color) {
		float[] floatArray = new float[color.length];
		for (int i = 0 ; i < color.length; i++)
		{
			floatArray[i] = (float) color[i];
		}
		return floatArray;
	}

	@Override
	public void init(GL2 gl) {
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	@Override
	public void destroy(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

}
