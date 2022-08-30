package simulator.model;

import java.util.List;

public class NoGravity implements GravityLaws {

	public String toString() {
		return "No gravity";
	}

	public void apply(List<Body> bodies) {
	}
}
