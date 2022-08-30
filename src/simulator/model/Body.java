package simulator.model;

import simulator.misc.Vector;

public class Body {
	protected Vector velocity;
	protected Vector position;
	protected Vector acceleration;
	protected double mass;
	protected String id;

	public Body(String id, double mass, Vector pos, Vector vel) {
		this.id = id;
		this.mass = mass;
		this.position = pos;
		this.velocity = vel;
		this.acceleration = new Vector(vel.dim());
	}
	
	public String toString() {
		return "{  \"id\": " + "\"" + id + "\"" +", " + "\"mass\": " + mass + ", " + "\"pos\": " + position.toString() + ", " + "\"vel\": "
				+ velocity.toString() + ", " + "\"acc\": " + acceleration.toString() + " }";
	}

	public void move(double t) {
		position = position.add((velocity.scale(t)).add(acceleration.scale(0.5).scale(t * t)));
		velocity = velocity.add(acceleration.scale(t));
	}

	public double getMass() {
		return mass;
	}

	void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	void setPosition(Vector position) {
		this.position = position;
	}

	void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public String getId() {
		return id;
	}

	public boolean equals(Body b) { //COPIAR Y PEGAR EL DE CLASE
		return b.getId().equals(this.id);
	}

}
