package Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Harsh on 03-07-2017.
 */
public class Utils {

    public static final String WEB_URL = "http://www.xyz.com/"; // Replace with your own host

    public static JSONObject getObject(String data, JSONObject jsonobj) throws JSONException{
        JSONObject jobj = jsonobj.getJSONObject(data);
        return jobj;
    }

    public static String getString(String data, JSONObject jsonobj) throws JSONException{
        return jsonobj.getString(data);
    }
}

