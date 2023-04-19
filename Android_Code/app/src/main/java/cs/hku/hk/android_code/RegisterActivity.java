package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText phone = findViewById(R.id.phone);

        Button submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                String str_phone = phone.getText().toString();
                if(str_username.length() == 0 || str_password.length() == 0
                        || str_phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please input all information", Toast.LENGTH_LONG).show();
                    return;
                }

                String request_url = Constants.BACKEND_LOCATION + "/register?username=" + str_username
                        + "&password=" + str_password + "&phone=" + str_phone;
                System.out.println(request_url);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = Utils.send_http_request(request_url, "POST");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                                    }
                                });
                                Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                                intent.putExtra("userId",jsonObject.getString("userId"));
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_LONG).show();
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