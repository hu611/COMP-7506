package cs.hku.hk.android_code;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class ListItemActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    Bitmap[]imgid = new Bitmap[20];

    int curr_idx = 0;


    String[]textid = new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        //add footer
        FooterFragment myFragment = new FooterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.footer_fragment, myFragment).commit();

        //add a back button to the toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle user_info = getIntent().getExtras();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userid = null;
                    if(user_info != null) {
                        userid = user_info.getString("user");
                    }

                    String suffix = userid == null ? "":"?user_id=" + userid;
                    //this will return a list of item image path and item name
                    JSONObject getPicUrls = Utils.send_http_request(Constants.BACKEND_LOCATION + "/getUserImage" + suffix,
                            "GET");
                    JSONObject result = getPicUrls.getJSONObject("result");
                    String imageUrls = result.getString("imageUrlList");
                    String itemNames = result.getString("itemNameList");
                    String[]imageUrlList = preprocess_json_str(imageUrls);
                    String[]itemNameList = preprocess_json_str(itemNames);

                    //set item name for view
                    textid = itemNameList;
                    imgid = new Bitmap[imageUrlList.length];

                    //set request urls for different images
                    String req_url = Constants.BACKEND_LOCATION + "/getImage?image=";
                    for(int i = 0; i < imageUrlList.length;i++) {
                        imageUrlList[i] = req_url + imageUrlList[i];
                    }
                    for(String imgUrl: imageUrlList) {
                        new DownloadImageTask()
                                .execute(imgUrl);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
        thread.start();
    }

    /**
     * Example: Change ["bag.png","apple.png"] to [bag.png, apple.png]
     * @param string
     * @return
     */
    public String[] preprocess_json_str(String string) {
        //remove lead and tail bracket
        string = string.substring(1,string.length()-1);
        String[]res = string.split(",");
        for(int i = 0; i < res.length; i++) {
            res[i] = res[i].substring(1,res[i].length()-1);
        }
        return res;

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public DownloadImageTask() {
        }

        protected Bitmap doInBackground(String... urls) {
            String request_url = urls[0];
            try {
                InputStream inputStream = Utils.send_image_request(request_url, "GET");
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e("ListItemActivity", "doInBackground: ." + request_url);
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                imgid[curr_idx++] = bitmap;
                GridView simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
                if(curr_idx == imgid.length) {
                    //last index
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), imgid, textid);
                    simpleGrid.setAdapter(customAdapter);
                    curr_idx = 0;
                }

            } else {
                Toast.makeText(getApplicationContext(),"Download failed",Toast.LENGTH_LONG).show();
            }

        }
    }
}

