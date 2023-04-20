package cs.hku.hk.android_code;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_my_profile);
        TextView textView = findViewById(R.id.textview_balance);
        Button add_balance_button = findViewById(R.id.btn_addBalance);
        Button sign_out_button = findViewById(R.id.btn_signout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String user_id = Utils.get_shared_preference("userId",getBaseContext());
        Thread get_balance = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = Utils.send_http_request(Constants.BACKEND_LOCATION
                            + "/getBalance?user_id=" + user_id, "GET");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                textView.setText(jsonObject.getString("userBalance"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        add_balance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread add_balance_thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = Utils.send_http_request(Constants.BACKEND_LOCATION
                                    + "/addBalance?user_id=" + user_id, "POST");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String user_balance = jsonObject.getString("userBalance");
                                        textView.setText(user_balance);
                                        int balance = Integer.parseInt(user_balance);
                                        if(balance <= 10000) {
                                            Toast.makeText(getApplicationContext(), "Add Balance successful", Toast.LENGTH_SHORT).show();
                                        } else if(balance < 50000){
                                            Toast.makeText(getApplicationContext(), "Dude you rich", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "u the boss now", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


                add_balance_thread.start();

            }
        });

        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.save_to_shared_preference("userId","",getBaseContext());
                //TODO change register activity to login activity if available
                Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        get_balance.start();
    }
}