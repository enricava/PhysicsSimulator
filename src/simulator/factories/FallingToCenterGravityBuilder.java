package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

/*
 * FallingToCenterGravityBuilder que extiende a Builder<GravityLaws>, para crear objetos de la clase FallingToCenterGravity

 */
public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {

	public FallingToCenterGravityBuilder() {
		super("ftcg", "Falling to center gravity");
	}

	public JSONObject getBuilderInfo() {
		JSONObject builderInfo = new JSONObject();
		builderInfo.put("desc", desc);
		builderInfo.put("type", typeTag);
		return builderInfo;
	}

	protected FallingToCenterGravity createTheInstance(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return new FallingToCenterGravity();
	}
}
