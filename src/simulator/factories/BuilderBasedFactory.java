package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> list;

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.list = builders;
	}

	public List<JSONObject> getInfo() {
		List<JSONObject> l = new ArrayList<JSONObject>();
		Iterator<Builder<T>> it = list.iterator();
		while(it.hasNext()) {
			l.add(it.next().getBuilderInfo());
		}
		return l;
	}

	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T type = null;
		boolean ok = false;
		Iterator<Builder<T>> it = list.iterator();
		while (it.hasNext() && !ok) {
			type = it.next().createInstance(info);
			if (type != null)
				ok = true;
		}
		if(!ok) throw new IllegalArgumentException("Nombre no identificado");
		return type;
	}
}
