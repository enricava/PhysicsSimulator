package simulator.model;

import simulator.misc.Vector;

public class MassLosingBody extends Body {
	private double lossFrequency;
	private double lossFactor;
	private double c; //contador de tiempo

	public MassLosingBody(String id, double mass, Vector pos, Vector vel, double lossFrequency, double lossFactor) {
		super(id, mass, pos, vel);
		this.lossFrequency = lossFrequency;
		this.lossFactor = lossFactor;
	}

	public void move(double t) {
		super.move(t);
		c += t;
		if (c >= lossFrequency) {
			mass = mass * (1 - lossFactor);
			c = 0.0;
		}
	}

}
