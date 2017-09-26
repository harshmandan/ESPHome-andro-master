package func;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Util.Utils;

/**
 * Created by Harsh on 06-07-2017.
 */
public class LastDate {

    public String getLastUpdated(String path) {
        HttpURLConnection conn= null;
        InputStream inStream = null;
        try {
            conn = (HttpURLConnection) (new URL(Utils.WEB_URL + path)).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            String temp;

            while((temp = bufferedReader.readLine())!= null) {
                stringBuffer.append(temp + "\r\n");
            }

            inStream.close();
            conn.disconnect();

            String inputLine = stringBuffer.toString();
            inputLine = inputLine.substring(16, inputLine.length()-3);
            Log.e("someTag", inputLine);
            return inputLine;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

