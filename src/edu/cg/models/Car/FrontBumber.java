package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class FrontBumber implements IRenderable {

	//initialize the skewed box according to the values provided to us in the specifications of the car
	private static final SkewedBox bumperBase = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_HEIGHT_1, Specification.F_BUMPER_HEIGHT_2,
			Specification.F_BUMPER_DEPTH,
			Specification.F_BUMPER_DEPTH);
	private static final SkewedBox bumperWings = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_WINGS_HEIGHT_1,
			Specification.F_BUMPER_WINGS_HEIGHT_2, Specification.F_BUMPER_WINGS_DEPTH, Specification.F_BUMPER_WINGS_DEPTH);





	@Override
	public void render(GL2 gl) {

		// Remember the dimensions of the bumper, this is important when you
		// combine the bumper with the hood.
		gl.glPushMatrix();
		Materials.SetDarkGreyMetalMaterial(gl);
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();


		Materials.SetDarkRedMetalMaterial(gl);
		bumperBase.render(gl);
		//translate to one of the ends of the base and create first wing
		gl.glTranslated(0.0, 0.0, 0.5*Specification.F_BUMPER_DEPTH + 0.5 * Specification.F_BUMPER_WINGS_DEPTH);
		Materials.SetBlackMetalMaterial(gl);
		bumperWings.render(gl);
		//translate to create first light and create
		gl.glTranslated(0.0, 0.2 * Specification.F_BUMPER_LENGTH , 0.0);
		Materials.SetRedMetalMaterial(gl);
		glu.gluSphere(q, 0.35*Specification.F_BUMPER_WINGS_DEPTH, 	36, 18);
		Materials.SetBlackMetalMaterial(gl);

		//translate to other end of base and create second wing and light
		gl.glTranslated(0.0, -0.2 * Specification.F_BUMPER_LENGTH, -Specification.F_BUMPER_DEPTH -Specification.F_BUMPER_WINGS_DEPTH);
		bumperWings.render(gl);
		gl.glTranslated(0.0, 0.2 * Specification.F_BUMPER_LENGTH , 0.0);
		Materials.SetRedMetalMaterial(gl);
		glu.gluSphere(q, 0.35*Specification.F_BUMPER_WINGS_DEPTH, 	36, 18);
		gl.glPopMatrix();

	}

	@Override
	public void init(GL2 gl) {
	}

	@Override
	public String toString() {
		return "FrontBumper";
	}

	@Override
	public void destroy(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

}
