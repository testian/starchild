/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.coordinate;

import java.util.Set;

/**
 *
 * @author testi
 */
public interface SpaceNode {
public SpaceNode getParent();
public boolean isInRange(SpaceNode child, boolean isChild);
/**
 * Note: Transformation matrix not involved as long as parent is not changed (most times)
 * @param vector
 */
public void move(Vector3D vector);
public void setPosition(Vector3D position);
public Vector3D getPosition();
public void rotate(double degree, Vector3D axis);
public void scale(Vector3D scale);
public void setTransformationListener(TransformationListener pcl);
public void iterate(double time);

/**
 *
 * @return The transformation matrix, that transforms the parent coordinate system into the this SpaceNodess coordinate system.
 */
public Transformation getTransformation();
public Set<SpaceNode> getChildren();
public int getOrder();
public void addChild(SpaceNode child);
public void removeChild(SpaceNode child);
/**
 * Method used to display an item in perspective of the whole universe
 * @return
 */
public Transformation getAbsoluteTransformation();
}
