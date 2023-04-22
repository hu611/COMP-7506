package cs.hku.hk.android_code;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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

    public static boolean send_http_request_2(String your_url, String your_method) throws Exception{
        URL url = new URL(your_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(your_method);
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = conn.getInputStream();
            String input_str = convert_input_stream_to_string(inputStream);
            conn.disconnect();
            if(input_str.equals("false")) return false;
            else return true;
        }
        else {
            conn.disconnect();
            throw new RuntimeException("Http request failure");
        }
    }

    public static JSONObject send_http_request_with_json(String your_url, String your_method, JSONObject jsonObject) throws Exception{
        URL url = new URL(your_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(your_method);
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setDoOutput(true);
        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
        os.writeBytes(jsonObject.toString());
        os.flush();
        os.close();

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

    public static void save_to_shared_preference(String key, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String get_shared_preference(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key,null);
    }

    public static JSONObject send_image_to_server(String your_url, byte[] img_bytes, String filename) throws Exception {

        String attachmentName = "file";
        String attachmentFileName = filename;
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        HttpURLConnection httpUrlConnection = null;
        URL url = new URL(your_url);
        httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setDoOutput(true);

        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
        httpUrlConnection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream request = new DataOutputStream(
                httpUrlConnection.getOutputStream());

        request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
                attachmentName + "\";filename=\"" +
                attachmentFileName + "\"" + crlf);
        request.writeBytes(crlf);


        request.write(img_bytes);

        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary +
                twoHyphens + crlf);

        request.flush();
        request.close();
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpUrlConnection.getInputStream();
            //convert input stream to string
            //e.g input_str:{"code":0,"msg":"Success","result":"images/bag.png "}
            String input_str = convert_input_stream_to_string(inputStream);
            httpUrlConnection.disconnect();
            //convert string to json object
            return new JSONObject(input_str);
        }

        httpUrlConnection.disconnect();
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

    public static String get_img_request_url(String pic_loc) {
        return Constants.BACKEND_LOCATION + "/getImage?image=" + pic_loc;
    }

    public static byte[] convert_uri_to_inputstream(Context context, Uri uri) throws Exception {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[]buffer = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer,0,len);
        }
        byte[]bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }


}
