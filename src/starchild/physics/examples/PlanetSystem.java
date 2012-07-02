/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.physics.examples;

import starchild.physics.*;
import starchild.coordinate.SpaceNode;
import starchild.coordinate.Transformation;
import starchild.coordinate.Vector3D;
import starchild.physics.Acceleratable;

/**
 *
 * @author testi
 */
public class PlanetSystem extends GravityPointSystem implements Acceleratable {
    private Vector3D velocity;
    public PlanetSystem(SpaceNode parent, double gravityRadius, double mass, Vector3D velocity) {
        super(parent, 11, gravityRadius, mass);
        this.velocity = velocity;
    }

    public Vector3D getVelocity() {
        return velocity;
    }

    @Override
    protected void onTransformation(Transformation transformation) {
    //Transformation t = transformation.setPosition(new Vector3D(0,0,0)).invert(); //Ignore translation and invert
    //Transformation t = transformation;
        velocity = transformation.multiply(Transformation.AsTranslateTransformation(velocity)).getScaleParameters();
    }

    @Override
    public void doIterate(double time) {
        super.doIterate(time);

        move(velocity.scale(time));
    }




}
