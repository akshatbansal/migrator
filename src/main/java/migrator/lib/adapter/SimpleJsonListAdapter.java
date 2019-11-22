package migrator.lib.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleJsonListAdapter<T> implements Adapter<Collection<T>, String> {
    protected Adapter<T, JSONObject> adapter;

    public SimpleJsonListAdapter(Adapter<T, JSONObject> adapter) {
        this.adapter = adapter;
    }

    public Collection<T> concretize(String items) {
        List<T> list = new ArrayList<>();
        if (items == null) {
            return list;
        }
        try {
            JSONArray json = new JSONArray(items);
            for (int i = 0; i < json.length(); i++) {
                list.add(
                    this.adapter.concretize(json.getJSONObject(i))
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generalize(Collection<T> items) {
        JSONArray json = new JSONArray();
        if (items == null) {
            return json.toString();
        }
        for (T item : items) {
            json.put(this.adapter.generalize(item));
        }
        return json.toString();
    }
}