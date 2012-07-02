/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.physics.examples;

import starchild.coordinate.SpaceNode;
import starchild.coordinate.Transformation;
import starchild.core.IterationListener;
/**
 *
 * @author testi
 */
public class SunSystem extends GravityPointSystem {

    public SunSystem(SpaceNode parent, double gravityRadius, double mass) {
        super(parent, 10, gravityRadius, mass);
    }


}
