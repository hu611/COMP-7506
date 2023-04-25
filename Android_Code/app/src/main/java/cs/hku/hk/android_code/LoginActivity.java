package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        Button login_button = findViewById(R.id.login_button);
        Button register_button = findViewById(R.id.register_button);
        Button hku_login_button = findViewById(R.id.hku_student_login);

        hku_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HkuLoginActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();

                if (str_username.length() == 0 || str_password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please input all information", Toast.LENGTH_LONG).show();
                    return;
                }
                String request_url = Constants.BACKEND_LOCATION + "/login?username=" + str_username
                        + "&password=" + str_password;
                System.out.println(request_url);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = Utils.send_http_request(request_url, "POST");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            Utils.save_to_shared_preference("userId", jsonObject.getString("userId"), getBaseContext());
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                });
                thread.start();
                return;
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
                thread.start();
                return;
            }
        });
    }
}