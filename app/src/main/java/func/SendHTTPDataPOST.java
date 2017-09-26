package func;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import Util.Utils;

/**
 * Created by Harsh on 05-07-2017.
 */
public class SendHTTPDataPOST {

    public String sendData(String path, String sw, String timer, String scheduler, String onh, String onm, String offh, String offm)
    {
        String TAG = "someTag";
        String POST_DATA = "switch=" + sw + "&time=" + timer + "&schd=" + scheduler + "&onh=" + onh  +"&onm="+onm + "&ffh="+offh + "&ffm="+offm;
        URL obj = null;
        HttpURLConnection con = null;
        Log.e("sendpower", sw);
        Log.e("sendtimer", timer);
        Log.e("sendschd", scheduler);
        Log.e("sendonh", onh);
        Log.e("sendonm", onm);
        Log.e("sendoffh", offh);
        Log.e("sendofffm", offm);
        try {

            obj = new URL(Utils.WEB_URL+path+"?"+POST_DATA);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_DATA.getBytes());
            os.flush();
            os.close();


            int responseCode = con.getResponseCode();
            Log.e(TAG, "POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                Log.e("RESPONSE", response.toString());
                return "APPLIED";
            } else {
                Log.e("someTag", "POST request did not work.");
                return "ERROR!";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR!";
    }
}
