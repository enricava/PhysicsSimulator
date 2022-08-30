package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {
	private static final double g = 9.81;

	public String toString() {
		return "Falling to center gravity";
	}

	public void apply(List<Body> bodies) {
		for (int i = 0; i < bodies.size(); i++) {
			double mod = bodies.get(i).getPosition().magnitude();
			if (mod != 0)
				bodies.get(i).setAcceleration(bodies.get(i).getPosition().scale(-g / mod));
		}
	}
}
