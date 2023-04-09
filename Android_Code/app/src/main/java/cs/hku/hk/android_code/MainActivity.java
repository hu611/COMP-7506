package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.ReferenceQueue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button test_button = (Button) findViewById(R.id.button);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder().url("http://10.0.2.2:9010/test").build();
                        try {
                            Response response = client.newCall(request).execute();
                            if(response.isSuccessful()) {
                                System.out.println(response.body().string());
                            } else {
                                System.out.println("Request failed");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Request failed");
                        }
                    }
                }).start();

            }
        });
    }


}