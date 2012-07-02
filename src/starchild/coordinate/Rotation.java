/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.coordinate;

/**
 *
 * @author testi
 */
public class Rotation {
private Vector3D rotationAxis;
double rotation;

    public Rotation(Vector3D rotationAxis, double rotation) {
        this.rotationAxis = rotationAxis;
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public Vector3D getRotationAxis() {
        return rotationAxis;
    }

}
