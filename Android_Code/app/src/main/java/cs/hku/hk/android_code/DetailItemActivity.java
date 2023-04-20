package cs.hku.hk.android_code;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;

public class DetailItemActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_detail_item);
        Bundle user_info = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String itemid = null;
                String user_id = null;
                if(user_info != null) {
                    user_id = Utils.get_shared_preference("userId",getBaseContext());
                    itemid = user_info.getString("itemid");
                } else {
                    return;
                }
                try {
                    JSONObject jsonObject = Utils.send_http_request(Constants.BACKEND_LOCATION + "/getDetailedItem" +
                            "?item_id=" + itemid, "GET");
                    String item_name = jsonObject.getString("item_name");
                    String item_description = jsonObject.getString("item_description");
                    String price = jsonObject.getString("price");
                    String img_url = jsonObject.getString("img_url");
                    img_url = Utils.get_img_request_url(img_url);
                    new DetailedItemTask(item_name,item_description,price, user_id, itemid).execute(img_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private class DetailedItemTask extends AsyncTask<String, Void, Bitmap> {

        String item_name = "";
        String item_description = "";
        String item_price = "";

        String user_id = "";

        String item_id = "";

        public DetailedItemTask(String item_name,String item_description, String price, String user_id, String item_id) {
            this.item_name = item_name;
            this.item_description = item_description;
            this.item_price = price;
            this.user_id = user_id;
            this.item_id = item_id;
        }

        protected Bitmap doInBackground(String... urls) {
            String request_url = urls[0];
            try {
                InputStream inputStream = Utils.send_image_request(request_url,"GET");
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e("DetailItemActivity", "doInBackground: ." + request_url);
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                TextView view_item_name = findViewById(R.id.item_name);
                TextView view_item_description = findViewById(R.id.item_desc);
                Button buy_button = findViewById(R.id.btn_buy);
                ImageView imageView = findViewById(R.id.item_image);
                view_item_name.setText(this.item_name);
                view_item_description.setText(this.item_description);
                String text_price = "Buy this! $" + this.item_price;
                buy_button.setText(text_price);
                buy_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("userId", user_id);
                            jsonObject.put("itemId", item_id);
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject1 = Utils.send_http_request_with_json(Constants.BACKEND_LOCATION + "/buyItem", "POST", jsonObject);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"Buy Successful",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(DetailItemActivity.this, MenuActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                    } catch (Exception e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"Buy failed, please make sure you have enough money"
                                                        ,Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(),"Download failed",Toast.LENGTH_LONG).show();
            }

        }
    }
}