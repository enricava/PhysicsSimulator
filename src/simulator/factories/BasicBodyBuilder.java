package simulator.factories;

import simulator.misc.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic", "Basic body");
	}

	public JSONObject getBuilderInfo() {
		JSONObject builderInfo = new JSONObject();
		JSONObject bodyInfo = new JSONObject();
		JSONArray defaultVector = new JSONArray();

		double def = 0.0;
		defaultVector.put(def);
		defaultVector.put(def);
		bodyInfo.put("id", "id");
		bodyInfo.put("pos", defaultVector);
		bodyInfo.put("vel", defaultVector);
		bodyInfo.put("mass", def);

		builderInfo.put("desc", desc);
		builderInfo.put("info", bodyInfo);
		return builderInfo;
	}

	protected Body createTheInstance(JSONObject jsonObject) throws IllegalArgumentException {
		jsonObject = jsonObject.getJSONObject("data");

		// comparacion de los keys
		// utilizando similar puede haber errores, por ejemplo si jsonObject tiene un
		// valor "0"
		if (!jsonObject.keySet().equals(getBuilderInfo().getJSONObject("info").keySet()))
			throw new IllegalArgumentException("Invalid JSON keys");

		String id = jsonObject.getString("id");
		
		try {
			double mass = jsonObject.getDouble("mass");

			// vector posicion
			JSONArray auxJsonArray = jsonObject.getJSONArray("pos");
			double[] auxVector = new double[auxJsonArray.length()]; // vector de doubles para sacar la posicion y
																	// posteriormente la velocidad
			for (int i = 0; i < auxJsonArray.length(); ++i)
				auxVector[i] = auxJsonArray.getDouble(i);
			Vector pos = new Vector(auxVector);

			// vector velocidad
			auxJsonArray = jsonObject.getJSONArray("vel");
			auxVector = new double[auxJsonArray.length()];
			for (int i = 0; i < auxJsonArray.length(); ++i)
				auxVector[i] = auxJsonArray.getDouble(i);
			Vector vel = new Vector(auxVector);

			Body body = new Body(id, mass, pos, vel);
			return body;
		}

		catch (NumberFormatException | JSONException e) {
			throw new IllegalArgumentException("Invalid JSON parameters " + e);
		}

	}

}
