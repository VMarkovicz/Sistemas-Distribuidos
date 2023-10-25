package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GoogleJson {
    private static final Gson googleJson = new GsonBuilder().create();

    public static <T> String codify(T object){
        return googleJson.toJson(object);
    }

    public static <T> T decode(final String json, Class<T> objectclass) throws JsonSyntaxException {
        return googleJson.fromJson(json, objectclass);
    }
}
