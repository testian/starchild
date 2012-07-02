/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.coordinate;
import java.util.Set;
import starchild.coordinate.SpaceNode;
//import starchild.core.Acceleratable;
import starchild.coordinate.Vector3D;
import starchild.coordinate.Transformation;

import java.util.Set;
import java.util.HashSet;
/**
 *
 * Default SpaceNode implementation. Class eventually going to be abstract, because isInRange method can vary in code, but even then there is the possibility that this class is going to be non abstract by delegating method.
 * @author testi
 */
abstract public class Base implements SpaceNode {
    private SpaceNode parent;
    private Transformation transformation;
    int order;
    HashSet<SpaceNode> children;
    TransformationListener pcl;

    public Base(SpaceNode parent, int order) {
        this.parent = parent;
        this.transformation = Transformation.Identity();
        this.order = order;
        children = new HashSet<SpaceNode>();
        parent.addChild(this);
        pcl = null;
    }
    public void setTransformationListener(TransformationListener pcl) {
    this.pcl = pcl;
    }
    public SpaceNode getParent() {
        return parent;
    }

    public Vector3D getPosition() {
    return transformation.getPosition();
    }

    //abstract public boolean isInRange(SpaceNode child);
    abstract protected void onTransformation(Transformation transformation);

    public void move(Vector3D vector) {
        
        transformation = transformation.translate(vector);
        
        if (checkParent()) {

        //System.out.print(this.getClass().getSimpleName() + ".move" + vector);
        //System.out.println(" {\nOld position = " + getPosition());
        //System.out.println("New position = " + getPosition());
        //System.out.println("Translated position = " + getPosition());
        //System.out.println("Absolute position = " + getAbsoluteTransformation().getPosition() + "\n}");
        }
    }
    public void iterate(double time) {
    doIterate(time);
    for (SpaceNode sn : getChildren()) {
    sn.iterate(time);
    }
    }
    abstract public void doIterate(double time);
    private boolean checkParent() {
        //if (exhale<1){return;}
    for (SpaceNode pchild : parent.getChildren()) {
    if (getOrder()>pchild.getOrder() && pchild.isInRange(this,false)) {
    changeParent(pchild,true);
    checkParent();
    return true;
    }
    }
    if (parent.isInRange(this,true)) return false; //We are still in range of our parent, so do not disconnect of it

    //We are no longer in range of our parent, so disconnect and connect to parents parent (grandparent)
    changeParent(parent.getParent(),false); //Note that this code is never entered when our parent is the universe, so it won't return null
    checkParent();
    return true;
    }
    public void setPosition(Vector3D position) {
        transformation = transformation.setPosition(position);
        checkParent();
    }
    /**
     *
     * @param newParent
     * @param down true means we go into one of our current parents child
     */
    private void changeParent(SpaceNode newParent, boolean down) {
    //TODO: Position must be relatively transformed to the coordinate system of the new parent.
    //This also affects: Velocity, Transformation Matrix
    System.out.println(this.getClass().getSimpleName() + ".changeParent(" + down + ")");

        //Mathematics
        Transformation apply;
        if (down) {

            apply = newParent.getTransformation().invert();
        }
        else { //We go up
        apply = parent.getTransformation(); //Since we now have to live without our parent, who made the transformation for us, we now have to do it on our own
        }

        transformation = apply.multiply(transformation);


        //Administrative
        parent.removeChild(this);
        onTransformation(apply);
        if (pcl != null) {
        pcl.onTransformation(apply);
        }
        

        parent = newParent;
        newParent.addChild(this);

    }

    public Set<SpaceNode> getChildren() {
        return (Set<SpaceNode>)children.clone();
    }

    public int getOrder() {
        return order;
    }
    public void rotate(double degree, Vector3D axis) {
    Vector3D translation = transformation.getPosition();
    Transformation r = Transformation.Rotation(degree, axis);
    transformation = r.multiply(transformation.setPosition(new Vector3D(0,0,0))).setPosition(translation);
    onTransformation(r);
    }
    public void scale(Vector3D scale) {
    Vector3D translation = transformation.getPosition();
    transformation = transformation.setPosition(new Vector3D(0,0,0)).scale(scale).setPosition(translation);
    onTransformation(Transformation.AsScaleTransformation(scale));

    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void addChild(SpaceNode child) {
        children.add(child);
    }

    public void removeChild(SpaceNode child) {
        children.remove(child);
    }

    public Transformation getAbsoluteTransformation() {
        return parent.getAbsoluteTransformation().multiply(transformation);
    }





}
