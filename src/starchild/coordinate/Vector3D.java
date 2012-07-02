/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.coordinate;

/**
 *
 * @author testi
 */
public class Vector3D {
private final double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    public Vector3D scale(double s) {
    return new Vector3D(x*s,y*s,z*s);
    }
    public Vector3D add(Vector3D vector) {
    return new Vector3D(x+vector.getX(),
    y+vector.getY(),
    z+vector.getZ());
    }
    public Vector3D substract(Vector3D vector) {
    return new Vector3D(x-vector.getX(),
    y-vector.getY(),
    z-vector.getZ());
    }
    public Vector3D normalize() {
    double length = length();
    if (length == 1){return this;} //Since this is immutable
    return new Vector3D(x/length,y/length,z/length);
    }
    public double length() {
    return Math.sqrt(x*x+y*y+z*z);
    }
    public Vector3D setLength(double newLength) {
    double factor = newLength/length();
    return scale(factor);
    }

    public boolean equals(Vector3D vector) {
    return (this == vector || (vector!=null && x == vector.getX() && y == vector.getY() && z == vector.getZ()));
    }
    public String toString() {
    return "(" + x + "," + y + "," + z + ")";
    }


}
