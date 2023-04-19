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
        Button bought_button = (Button) findViewById(R.id.btn_bought_items);
        Bundle user_info = getIntent().getExtras();
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ListItemActivity.class);
                startActivity(intent);
            }
        });

        sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SellItemActivity.class);
                startActivity(intent);
            }
        });

        bought_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BoughtItemActivity.class);
                startActivity(intent);
            }
        });
    }
}