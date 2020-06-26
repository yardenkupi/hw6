package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.*;
import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.BoundingSphereTree;
import edu.cg.models.IIntersectable;
import edu.cg.models.IRenderable;

/**
 * A F1 Racing Car.
 *
 */
public class F1Car implements IRenderable, IIntersectable {
	// Remember to include a ReadMe file specifying what you implemented.
	Center carCenter = new Center();
	
	Back carBack = new Back();
	Front carFront = new Front();
	double radius = generateObjectRadius();

	//sphere bounding entire car
	BoundingSphereTree boundingSphere = new BoundingSphereTree();
	BoundingSphere carBoundingSphere  = new BoundingSphere(radius,new Point(0.05 * Specification.C_LENGTH, 0.5 * Specification.C_HIEGHT ,0));
	


	/** Initialize the spheres surounding car - set the sphere values to be relative to the object they are encompassing
	 * Created tree data structure of bounding spheres, so this is done recursivly*/

	public F1Car() {
		BoundingSphere s1 = carBoundingSphere;
		BoundingSphereTree s2 = carFront.getBoundingSpheres();
		
		BoundingSphereTree s3 = carCenter.getBoundingSpheres();
		BoundingSphereTree s4 = carBack.getBoundingSpheres();


		carBack.getBoundingSpheres().getBoundingSphere().translateCenter(-0.51 * Specification.B_LENGTH -0.5 * Specification.C_LENGTH, 0.0, 0.0);
		if(carBack.getBoundingSpheres().getList() != null)	//initiallize location of inner spheres of back that are a subtree in it
		{
			for (BoundingSphereTree currBoundingSphereTree : carBack.getBoundingSpheres().getList()) {
				currBoundingSphereTree.getBoundingSphere().translateCenter(-0.14 * Specification.B_LENGTH -0.1 * Specification.C_LENGTH, 0.0, 0.0);;
			}
		}
		carFront.getBoundingSpheres().getBoundingSphere().translateCenter((Specification.C_LENGTH * 0.5) + (Specification.F_LENGTH * 0.5), 0.0, 0.0);
		if(carFront.getBoundingSpheres().getList() != null) //initiallize location of inner spheres of front that are a subtree in it
		{
			for (BoundingSphereTree currBoundingSphereTree : carFront.getBoundingSpheres().getList()) {
				currBoundingSphereTree.getBoundingSphere().translateCenter((Specification.C_LENGTH * 0.18) + (Specification.F_LENGTH * 0.1), 0.0, 0.0);;
			}
		}
		LinkedList<BoundingSphereTree> list = new LinkedList<BoundingSphereTree>();
		boundingSphere.setBoundingSphere(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		boundingSphere.setList(list);
	}
	@Override
	public void render(GL2 gl) {
		carCenter.render(gl);
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 - Specification.C_BASE_LENGTH / 2.0, 0.0, 0.0);
		carBack.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(Specification.F_LENGTH / 2.0 + Specification.C_BASE_LENGTH / 2.0, 0.0, 0.0);
		carFront.render(gl);		
		gl.glPopMatrix();
		
		
		
	}

	@Override
	public String toString() {
		return "F1Car";
	}

	@Override
	public void init(GL2 gl) {

	}
	private double generateObjectRadius() {
		double maxDepthSquared = Math.pow(Specification.S_DEPTH,2);
		double maxLengthSquared = Math. pow(Specification.F_LENGTH + Specification.C_LENGTH + Specification.B_LENGTH,2);
		double maxHeightSquared = Math.pow(Specification.B_HEIGHT,2);

		return 0.5 * Math.sqrt(maxDepthSquared+maxHeightSquared+maxLengthSquared);
	}
	
	@Override
	public BoundingSphereTree getBoundingSpheres() {
		return this.boundingSphere;
	}
	
	@Override
	public void destroy(GL2 gl) {
		// TODO Auto-generated method stub
		
	}
}
