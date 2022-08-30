package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	public MassLosingBodyBuilder() {
		super("mlb", "Body on a diet");
	}

	public JSONObject getBuilderInfo() {
		// creamos un JSONObject con el formato correcto para comparar
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
		bodyInfo.put("freq", def);
		bodyInfo.put("factor", def);

		builderInfo.put("desc", desc);
		builderInfo.put("info", bodyInfo);
		return builderInfo;
	}

	protected MassLosingBody createTheInstance(JSONObject jsonObject) {
		jsonObject = jsonObject.getJSONObject("data");

		if (!jsonObject.keySet().equals(getBuilderInfo().getJSONObject("info").keySet()))
			throw new IllegalArgumentException("Invalid JSON keys");

		String id = jsonObject.getString("id");
		try {
			double mass = jsonObject.getDouble("mass");
			double freq = jsonObject.getDouble("freq");
			double factor = jsonObject.getDouble("factor");

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

			MassLosingBody body = new MassLosingBody(id, mass, pos, vel, freq, factor);
			return body;
		}

		catch (NumberFormatException | JSONException e) {
			throw new IllegalArgumentException("Invalid JSON parameters " + e);
		}
	}
}
