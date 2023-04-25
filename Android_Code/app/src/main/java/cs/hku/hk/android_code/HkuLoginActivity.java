package cs.hku.hk.android_code;

import static cs.hku.hk.android_code.Utils.convert_input_stream_to_string;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HkuLoginActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hku_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button submit_button = (Button) findViewById(R.id.btn_Login);
        EditText txt_UserName = (EditText)findViewById(R.id.txt_UserName);
        EditText txt_UserPW = (EditText)findViewById(R.id.txt_UserPW);
        doTrustToCertificates();
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                URLConnection conn_moodle = null;
                final int HTML_BUFFER_SIZE = 2 * 1024 * 1024;
                char htmlBuffer[] = new char[HTML_BUFFER_SIZE];
                final int HTTPCONNECTION_TYPE = 0;
                final int HTTPSCONNECTION_TYPE = 1;
                int moodle_conn_type = HTTPCONNECTION_TYPE;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url_portal = null;
                            HttpsURLConnection conn_portal = null;
                            url_portal = new
                                    URL("https://hkuportal.hku.hk/cas/servlet/edu.yale.its.tp.cas.servlet.Login");

                            conn_portal = (HttpsURLConnection) url_portal.openConnection();
                            String urlParameters = "keyid=" + keyid() + "&service=https://moodle.hku.hk/login/index.php?authCAS=CAS&username="
                                    + txt_UserName.getText().toString() + "&password=" + txt_UserPW.getText().toString() + "&x=38&y=26";
                            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                            int postDataLength = postData.length;
                            conn_portal.setDoOutput(true);
                            conn_portal.setInstanceFollowRedirects(false);
                            conn_portal.setRequestMethod("POST");
                            conn_portal.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn_portal.setRequestProperty("charset", "utf-8");
                            conn_portal.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                            conn_portal.setUseCaches(false);

                            try (DataOutputStream wr = new DataOutputStream(conn_portal.getOutputStream())) {
                                wr.write(postData);
                            }

                            int responseCode = conn_portal.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                InputStream inputStream = conn_portal.getInputStream();
                                String input_str = convert_input_stream_to_string(inputStream);
                                System.out.println(input_str);
                                if(input_str.contains("Login failed")) {
                                    throw new RuntimeException("Login failed");
                                }
                                //login successful
                                JSONObject jsonObject = Utils.send_http_request(Constants.BACKEND_LOCATION+ "/loginhku?username=" + txt_UserName.getText().toString(), "POST");
                                Utils.save_to_shared_preference("userId",jsonObject.getString("userId"),getBaseContext());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(HkuLoginActivity.this, MenuActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                throw new RuntimeException("Login Failed");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Login failed, please login again", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
                thread.start();



            }
        });

    }
    // generate keyid of POST data to hku portal
    public String keyid() {
        Calendar c1 = Calendar.getInstance();
        String time = String.valueOf(c1.get(Calendar.YEAR)) + String.valueOf(c1.get(Calendar.MONTH))
                + String.valueOf(c1.get(Calendar.DATE)) + String.valueOf(c1.get(Calendar.HOUR))
                + String.valueOf(c1.get(Calendar.MINUTE)) + String.valueOf(c1.get(Calendar.SECOND));
        return time;
    }

    public void doTrustToCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers()
                    {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                    {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                    {
                    }
                }
        };

        try {
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}