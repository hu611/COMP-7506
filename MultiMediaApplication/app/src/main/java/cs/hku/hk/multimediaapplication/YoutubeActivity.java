package cs.hku.hk.multimediaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class YoutubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);;
        String youTubeUrl = "https://www.youtube.com/embed/81fUwHshjG4";
        webView.setWebViewClient(new WebViewClient());
        String html = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/81fUwHshjG4\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");
    }

}