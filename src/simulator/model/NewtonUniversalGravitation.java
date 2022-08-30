package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {
	private static double G = 6.67e-11;
	
	public String toString() {
		return "Newton's Universal law of Gravitation";
	}
	

	public void apply(List<Body> bodies) {
		for (int i = 0; i < bodies.size(); ++i) {
			Vector force = new Vector(2);
			if (bodies.get(i).getMass() == 0) {
				bodies.get(i).setAcceleration(force);
				bodies.get(i).setVelocity(force);
			} 
			else {
				for (int j = 0; j < bodies.size(); ++j) {
					if (j != i) {
						Vector forceAux = bodies.get(i).getPosition().minus(bodies.get(j).getPosition());
						double distance = forceAux.magnitude();
						double scalar = bodies.get(i).getMass() * bodies.get(j).getMass() * -G;
						if(distance != 0) { //por si acaso hay dos planetas en la misma posicion
							forceAux = forceAux.scale(scalar/(distance*distance*distance));
							force = force.add(forceAux);
						}
					}
				}
				bodies.get(i).setAcceleration(force.scale(1 / bodies.get(i).getMass()));
			}

		}
	}
}
