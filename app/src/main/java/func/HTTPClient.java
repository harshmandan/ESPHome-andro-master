package func;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Util.Utils;

/**
 * Created by Harsh on 03-07-2017.
 */
public class HTTPClient {

    public String getSettings (String path)
    {
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

            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
