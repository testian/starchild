/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.physics.examples;
import starchild.coordinate.Base;
import starchild.coordinate.SpaceNode;
import starchild.coordinate.Transformation;
import starchild.coordinate.Vector3D;
/**
 *
 * @author testi
 */
public class GravityPointSystem extends Base {
    private double gravityRadius;
    private Vector3D initScale;
    double mass;
    public GravityPointSystem(SpaceNode parent, int order, double gravityRadius, double mass) {
        super(parent, order);
        this.gravityRadius = gravityRadius;
        initScale = new Vector3D(1,1,1).setLength(gravityRadius);
        this.mass = mass;
    }

    @Override
    protected void onTransformation(Transformation transformation) {
    //Transformation t = transformation.setPosition(new Vector3D(0,0,0)).invert(); //Ignore translation and invert
    //Transformation t = transformation;
    initScale = transformation.multiply(Transformation.AsTranslateTransformation(initScale)).getScaleParameters();
    //Vector3D scale = transformation.getScaleParameters();
    //initScale = new Vector3D(initScale.getX()/scale.getX(),initScale.getY()/scale.getY(),initScale.getZ()/scale.getZ());
    //gravityRadius = initScale.length();
    }


    @Override
    public boolean isInRange(SpaceNode child, boolean isChild) {
        Vector3D distanceVector;
        if (!isChild) {
        distanceVector = getPosition().substract(child.getPosition());
        } else {
        distanceVector = child.getPosition();
        return distanceVector.length()<gravityRadius;
        }
        //System.out.println(distanceVector.length() + " " + gravityRadius);
        return distanceVector.length()<initScale.length();
    }

    
    public void doIterate(double time) {
    for (SpaceNode sn : getChildren()) {
    Vector3D vector = getPosition().substract(sn.getPosition());
    double distance = vector.length();
    double gravity = mass/(distance*distance);

    sn.move(vector.setLength(time*gravity));

    }
    }





}
