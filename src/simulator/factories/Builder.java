package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String typeTag;
	protected String desc;

	public Builder(String typeTag, String desc){
		this.typeTag = typeTag;
		this.desc = desc;
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T elem = null;
		if (info.getString("type").equals(typeTag)) {
			//System.out.println(info.toString());
			//elem = createTheInstance(info.getJSONObject("data"));
			elem = createTheInstance(info);
		}
		return elem;
	}

	public abstract JSONObject getBuilderInfo();

	protected abstract T createTheInstance(JSONObject jsonObject);
}
