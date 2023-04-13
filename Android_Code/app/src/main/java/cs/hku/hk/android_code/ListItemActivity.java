package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListItemActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    int[] imgid = {
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple
    };

    String[] textid = {
            "apple",
            "banana",
            "carrot",
            "apple",
            "banana",
            "carrot",
            "apple"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        GridView simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), imgid, textid);
        simpleGrid.setAdapter(customAdapter);
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity

            }
        });
        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute("http://10.0.2.2:9010/testimage");
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView viewImage;


        public DownloadImageTask(ImageView viewImage) {
            this.viewImage = viewImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String request_url = urls[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(request_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                viewImage.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(),"Download failed",Toast.LENGTH_LONG).show();
            }

        }
    }
}

