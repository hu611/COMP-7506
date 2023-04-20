package cs.hku.hk.android_code;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SellItemActivity extends AppCompatActivity {
    Uri uri = null;
    Bitmap bitmap;

    byte[] img_bytes;
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
        setContentView(R.layout.activity_sell_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Button upload_button = findViewById(R.id.upload_button);
        Button submit_button = findViewById(R.id.submit_button);
        EditText description_edittext = findViewById(R.id.description_edittext);
        EditText name_edittext = findViewById(R.id.name_edittext);
        EditText price_edittext = findViewById(R.id.price_edittext);


        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Get Image"), 1);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = price_edittext.getText().toString();
                String item_name = name_edittext.getText().toString();
                String item_description = description_edittext.getText().toString();

                String userId;
                JSONObject postData;

                userId = Utils.get_shared_preference("userId",getBaseContext());
                postData = new JSONObject();
                try {
                    postData.put("item_name", item_name);
                    postData.put("price", price);
                    postData.put("item_description", item_description);
                    postData.put("userId", userId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                    //check if there are null value
                if(price.length() == 0 || item_name.length() == 0 || item_description.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Please enter each field accordingly",Toast.LENGTH_LONG).show();
                    return;
                }

                if(uri == null) {
                    Toast.makeText(getApplicationContext(),"Please upload images",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!price.matches("\\d+")) {
                    Toast.makeText(getApplicationContext(),"Please enter integer for price",Toast.LENGTH_LONG).show();
                    return;
                }


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ImageView imageView = findViewById(R.id.imageView);
                            JSONObject jsonObject = Utils.send_image_to_server(Constants.BACKEND_LOCATION +
                                            "/uploadImage", img_bytes,item_name);
                            JSONObject jsonObject1 = Utils.send_http_request_with_json(Constants.BACKEND_LOCATION + "/sellItem"
                                    ,"POST",postData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Upload successful",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SellItemActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Upload failure, please try again",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });
    }

    /**
     * get uri of uploaded image
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("on activity result");
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            this.uri = data.getData();
            System.out.println(uri.toString());

            try {
                ImageView imageView = findViewById(R.id.imageView);
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[]buffer = new byte[1024];
                int length;
                while((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer,0,length);
                }
                this.img_bytes = outputStream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(img_bytes,0,img_bytes.length);
                this.bitmap = bitmap;

                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}