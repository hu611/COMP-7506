package cs.hku.hk.android_code;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void request_all_image_loc() throws Exception {

        String your_url = "http://localhost:9014/testimage";
        String your_method = "GET";
        JSONObject jsonObject = Utils.send_http_request(your_url, your_method);
        System.out.println(jsonObject.get("result"));



    }
}