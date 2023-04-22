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

public class SetpasswordActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_setpassword);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText curpassword = findViewById(R.id.curpassword);
        EditText newpassword = findViewById(R.id.newpassword);

        Button setpassword_button = findViewById(R.id.setpassword_button);

        setpassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = null;
                userid = Utils.get_shared_preference("userId", getBaseContext());

                String str_curpassword = curpassword.getText().toString();
                String str_newpassword = newpassword.getText().toString();

                if (str_curpassword.length() == 0 || str_newpassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please input all information", Toast.LENGTH_LONG).show();
                    return;
                }

                String request_url = Constants.BACKEND_LOCATION + "/setPassword?userid=" + userid
                        + "&oldpassword=" + str_curpassword + "&newpassword=" + str_newpassword;
                System.out.println(request_url);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean res;
                        try {
                            res = Utils.send_http_request_2(request_url, "POST");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(res==true) {
                                        Toast.makeText(getApplicationContext(), "Set Password Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SetpasswordActivity.this, SettingActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Set Password Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Set Password Error", Toast.LENGTH_LONG).show();
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