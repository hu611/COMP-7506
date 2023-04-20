package cs.hku.hk.android_code;

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

public class SetaddressActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_setaddress);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText newaddress = findViewById(R.id.newaddress);

        Button setaddress_button = findViewById(R.id.setaddress_button);

        setaddress_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = null;
                userid = Utils.get_shared_preference("userId",getBaseContext());

                String str_newaddress = newaddress.getText().toString();

                if (str_newaddress.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please input all information", Toast.LENGTH_LONG).show();
                    return;
                }

                String request_url = Constants.BACKEND_LOCATION + "/setAddress?userid=" + userid
                        + "&newaddress=" + str_newaddress;
                System.out.println(request_url);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Utils.send_http_request_2(request_url, "POST");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Set Address Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SetaddressActivity.this, SettingActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Set Address Error", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                });
                thread.start();
                return;
            }
        });
    }
}