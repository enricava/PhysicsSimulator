package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.SimulatorObserver;

public class Controller {

	private PhysicsSimulator simulator;
	private Factory<Body> bodies;
	private Factory<GravityLaws> laws;

	public Controller(PhysicsSimulator simulator, Factory<Body> bodies, Factory<GravityLaws> laws) {
		this.simulator = simulator;
		this.bodies = bodies;
		this.laws = laws;
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray list = jsonInput.getJSONArray("bodies");
		
		for(int i = 0; i < list.length(); ++i) {
			Body body = bodies.createInstance(list.getJSONObject(i));
			simulator.addBody(body);
		}
	}
	
	public void setGravityLaws(JSONObject info) {
		GravityLaws gravityLaws = laws.createInstance(info);
		simulator.setGravityLaws(gravityLaws);
	}

	public void run(int n, OutputStream out) throws IOException {
		out.write(("{\n\"states\": [\n").getBytes());
		for(int i = 0; i <= n; i++) {
			out.write(simulator.toString().getBytes());
			simulator.advance();
			if(i < n) out.write(",\n".getBytes());
		}
		out.write("\n]\n}\n".getBytes());
	}
	
	public void run(int n) {
		for(int i = 0; i <= n; i++) simulator.advance();
	}
	
	public void reset() {
		simulator.reset();
	}
	
	public void setDeltaTime(double dt) {
		simulator.setDeltaTime(dt);
	}
	
	public Factory<GravityLaws> getGravityLawsFactory(){
		return laws;
	}

	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}

}
