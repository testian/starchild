/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.coordinate;
import java.util.Set;
import java.util.HashSet;
/**
 *
 * @author testi
 */
public class Universe implements SpaceNode {
    private HashSet<SpaceNode> children;
    final Transformation identity;
    public SpaceNode getParent() {
        return null;
    }
    public Universe() {
    identity = Transformation.Identity();
    children = new HashSet<SpaceNode>();
    }
    public boolean isInRange(SpaceNode child, boolean isChild) {
        return true;
    }
    public void iterate(double time) {
    for (SpaceNode sn : getChildren()) {
    sn.iterate(time);
    }
    }

    public void scale(Vector3D scale) {
        
    }

    public void move(Vector3D vector) {
    }
    public void setPosition(Vector3D position) {
        
    }

    public Vector3D getPosition() {
        return getTransformation().getPosition();
    }

    public Transformation getTransformation() {
        return identity;
    }

    public Set<SpaceNode> getChildren() {
        return (Set<SpaceNode>)children.clone();
    }

    public int getOrder() {
        return 0;
    }

    public void addChild(SpaceNode child) {
        children.add(child);
    }

    public void removeChild(SpaceNode child) {
        children.remove(child);
    }

    public void rotate(double degree, Vector3D axis) {
    }

    public void setTransformationListener(TransformationListener pcl) {
        //I won't ever have parents..
    }

    public Transformation getAbsoluteTransformation() {
        return getTransformation();
    }




}
