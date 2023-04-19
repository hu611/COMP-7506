package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button buy_button = (Button) findViewById(R.id.btn_buy);
        Button sell_button = (Button) findViewById(R.id.btn_sell);
        Bundle user_info = getIntent().getExtras();
        String userId;
        if(user_info != null) {
            userId = user_info.getString("userId");
        } else {
            Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
            return;
        }
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ListItemActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SellItemActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }
}