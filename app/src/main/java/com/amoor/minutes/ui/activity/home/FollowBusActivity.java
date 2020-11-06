package com.amoor.minutes.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.amoor.minutes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowBusActivity extends AppCompatActivity {

    @BindView(R.id.follow_bus_fragment_web_view)
    WebView followBusFragmentWebView;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WebView myWebView = new WebView(getApplicationContext());
//        setContentView(myWebView);
        setContentView(R.layout.activity_follow_bus);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("link")) {
            link = intent.getExtras().getString("link");
        }

//        followBusFragmentWebView.setWebViewClient(new WebViewClient()
//        {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url)
//            {
//                return true;
//            }
//        });
        followBusFragmentWebView.setWebViewClient(new WebViewClient());
//        followBusFragmentWebView.loadUrl("http://www.hh4track.com/page/share.jsp?mapType=google&token=S1569877200h0Y020471d6b6ef4d7a73fad2d9f560be06d6a2eb");
        followBusFragmentWebView.loadUrl(link);

        WebSettings webSettings = followBusFragmentWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (followBusFragmentWebView.canGoBack()) {
            followBusFragmentWebView.goBack();
        } else {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

    }
    //    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//        finish();
//    }
}
