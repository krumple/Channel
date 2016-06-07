package test.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by dmytr on 07.06.2016.
 */
public class SelectedItemActivity extends Activity {
    String linc;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_item);
        Intent intent = getIntent();
         linc = intent.getStringExtra("linc");
        webView = (WebView)findViewById(R.id.content);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(linc);
    }
}
