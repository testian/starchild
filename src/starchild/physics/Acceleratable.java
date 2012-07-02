/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.physics;

import starchild.coordinate.Vector3D;
//import starchild.coordinate.TransformationListener;
/**
 *
 * Objects that can travel through space by acceleration must implement this interface. Note that the speed of the object is relative to the parents coordinate system, which means that the Velocity is subject to be transformed upon parent change.
 * @author testi
 */
public interface Acceleratable {
/**
 * 
 * @return the relative speed to the parent.
 */
public Vector3D getVelocity();
}
