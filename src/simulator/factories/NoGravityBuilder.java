package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {

	public NoGravityBuilder() {
		super("ng", "No gravity");
	}

	public JSONObject getBuilderInfo() {
		JSONObject builderInfo = new JSONObject();
		builderInfo.put("desc", desc);
		builderInfo.put("type", typeTag);
		return builderInfo;
	}

	protected NoGravity createTheInstance(JSONObject jsonObject) {
		return new NoGravity();
	}
}
