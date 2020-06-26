package edu.cg.models;
import java.util.LinkedList;

/** Hierarchical tree object to represent all bounding spheres of the object
 * contains bounding sphere who is the head of the tree and list of children who are the sub bounding spheres
 * */

public class BoundingSphereTree {
	private BoundingSphere boundingSphere;		//root bounding sphere of entire object
	private LinkedList<BoundingSphereTree> list = new LinkedList<BoundingSphereTree>();	//linked list of sub bounding spheres of this object

	public BoundingSphere getBoundingSphere() {
		return boundingSphere;
	}
	public void setBoundingSphere(BoundingSphere boundingSphere) {
		this.boundingSphere = boundingSphere;
	}
	public LinkedList<BoundingSphereTree> getList() {
		return list;
	}
	public void setList(LinkedList<BoundingSphereTree> list) {
		this.list = list;
	}

}
