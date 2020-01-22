package com.juanma.greennews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

public class NewsActivity extends BaseActivity {

    private WebView webView;
    public static String mLoadUrl;
    private String mTrackUrlChange;
    private String mTitle;
    private AppBarLayout mAppBarLayout;
    private ProgressBar mProgressBar;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_2);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(1);

        String url  = getIntent().getStringExtra(mLoadUrl);
        if (url == null || url.isEmpty()) finish();


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar((toolbar));


        //WebView initialization
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        setWebView(webView,webSettings);
        //Anonymous class to obtain current url and title
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mTrackUrlChange = url;

            }



            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("WebView", "your current url when webpage loading: " + url + "  " + " TITLE: " + view.getTitle());
                mTitle = view.getTitle();

            }
        });




        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE) {
                    mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
                    mProgressBar.setProgress(progress);
                if(progress == 100) {
                    mProgressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        webView.loadUrl(url);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_toolbar_menu,menu);
        return true;
    }

    //Shares the title and opens in a new tab.

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.share_news){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/html");
            i.putExtra(Intent.EXTRA_SUBJECT, mTitle);
            i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(mTrackUrlChange));
            startActivity(Intent.createChooser(i, "Share"));
        }
        else if (item.getItemId() == R.id.open_in_browser){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mTrackUrlChange));
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setWebView(WebView webView, WebSettings webSettings){
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        webView.getSettings().setDisplayZoomControls(false); // disable the default zoom controls on the page
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisabledActionModeMenuItems(0);
    }


}
