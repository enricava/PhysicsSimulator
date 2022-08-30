package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newtons law of universal gravitation");
	}

	public JSONObject getBuilderInfo() {
		JSONObject builderInfo = new JSONObject();
		builderInfo.put("desc", desc);
		builderInfo.put("type", typeTag);
		return builderInfo;
	}

	protected NewtonUniversalGravitation createTheInstance(JSONObject jsonObject) {
		return new NewtonUniversalGravitation();
	}

}
