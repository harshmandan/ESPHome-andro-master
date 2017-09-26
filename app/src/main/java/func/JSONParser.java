package func;

import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;
import data.Settings;

/**
 * Created by Harsh on 03-07-2017.
 */
public class JSONParser {

    public static Settings getSettings (String data)
    {
        try {
            JSONObject jsonObject = new JSONObject(data);
            Settings settingsObj = new Settings();
            settingsObj.setPower(Utils.getString("switch", jsonObject));
            settingsObj.setTimer(Utils.getString("timer", jsonObject));
            settingsObj.setScheduler(Utils.getString("schd", jsonObject));
            settingsObj.setSchd_onh(Utils.getString("onh", jsonObject));
            settingsObj.setSchd_onm(Utils.getString("onm", jsonObject));
            settingsObj.setSchd_offh(Utils.getString("offh", jsonObject));
            settingsObj.setSchd_offm(Utils.getString("offm", jsonObject));
            return settingsObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
