package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
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

        /*
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override


            }
        });
         */
        //activity redirect, jump to list item activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //send extra information to ListItemActivity
        //intent.putExtra("user","all");
        startActivity(intent);
    }


}