package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simulator.view.SimulatorObserver;

public class PhysicsSimulator {

	private static double dt;
	private GravityLaws gravityLaws;
	private double currentTime;
	private List<Body> bodies;
	private List<SimulatorObserver> observers;

	public PhysicsSimulator(double dt, GravityLaws laws) throws IllegalArgumentException {
		if (dt > 0)
			PhysicsSimulator.dt = dt;
		else
			throw new IllegalArgumentException();

		if (laws == null)
			throw new IllegalArgumentException();

		gravityLaws = laws;
		currentTime = 0.0;

		bodies = new ArrayList<Body>();
		observers = new ArrayList<SimulatorObserver>();
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		Iterator<Body> it = bodies.iterator();
		s.append("{ \"time\": " + currentTime + ", \"bodies\": [ ");
		while (it.hasNext()) {
			s.append(it.next().toString());
			if (it.hasNext())
				s.append(", ");
		}
		s.append(" ] }");
		return s.toString();
	}

	public void addBody(Body b) throws IllegalArgumentException {
		boolean ok = true;
		if (bodies.isEmpty()) {
			bodies.add(b);
		}
		else {
			Iterator<Body> it = bodies.iterator();
			while (it.hasNext() && ok) {
				if (it.next().equals(b))
					ok = false;
			}
			if (!ok)
				throw new IllegalArgumentException("Duplicate bodies");
			bodies.add(b);
		}
		for(SimulatorObserver o: observers) o.onBodyAdded(bodies, b);
	}

	public double getTimePerStep() {
		return dt;
	}

	public void advance() {
		gravityLaws.apply(bodies);
		Iterator<Body> it = bodies.iterator();
		while (it.hasNext()) {
			it.next().move(dt);
		}
		currentTime += dt;
		for(SimulatorObserver o: observers) o.onAdvance(bodies, currentTime);
	}

	public void reset() {
		bodies.clear();
		currentTime = 0;
		for(SimulatorObserver o: observers) o.onReset(bodies, currentTime, dt, gravityLaws.toString());
	}

	public void setDeltaTime(double dt) {
		if (dt > 0) {
			PhysicsSimulator.dt = dt;
			for(SimulatorObserver o: observers) o.onDeltaTimeChanged(PhysicsSimulator.dt);
		}
		else
			throw new IllegalArgumentException("Invaild delta-time");
	}

	public void setGravityLaws(GravityLaws gravityLaws) {
		if (gravityLaws != null) {
			this.gravityLaws = gravityLaws;
			for(SimulatorObserver o: observers) o.onGravityLawChanged(gravityLaws.toString());
		}
		else
			throw new IllegalArgumentException("Invalid gravity law");
	}

	public void addObserver(SimulatorObserver o) {
		if(!observers.contains(o)) {
			observers.add(o);
		}
	}

}
