package cs.hku.hk.android_code;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Utils {
    /**
     * Send HTTP Request to {your_url}, and return the response in string
     *
     * @param your_url
     * @param your_method
     * @return
     * @throws Exception
     */
    public static JSONObject send_http_request(String your_url, String your_method) throws Exception{
        URL url = new URL(your_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(your_method);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = conn.getInputStream();
            //convert input stream to string
            //e.g input_str:{"code":0,"msg":"Success","result":"images/bag.png "}
            String input_str = convert_input_stream_to_string(inputStream);
            conn.disconnect();
            //convert string to json object
            return new JSONObject(input_str);
        }
        conn.disconnect();
        throw new RuntimeException("Http request failure");
    }

    /**
     * Similar to send_http_request function, but only accept bytestream as response
     * @param your_url
     * @param your_method
     * @return
     * @throws Exception
     */
    public static InputStream send_image_request(String your_url, String your_method) throws Exception {
        URL url = new URL(your_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(your_method);
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        throw new RuntimeException();
    }

    public static String convert_input_stream_to_string(InputStream inputStream) throws Exception{
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }

}
