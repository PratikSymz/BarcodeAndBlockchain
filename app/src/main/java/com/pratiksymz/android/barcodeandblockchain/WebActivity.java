package com.pratiksymz.android.barcodeandblockchain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import im.delight.android.webview.AdvancedWebView;

public class WebActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    private static final String LOG_TAG = WebActivity.class.getSimpleName();
    private AdvancedWebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mProgressBar = findViewById(R.id.progress_bar);

        String url = "";
        try {
            url = getIntent().getExtras().getString("url");
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        mWebView = findViewById(R.id.web_view);
        mWebView.setListener(this, this);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.setMixedContentAllowed(true);
        mWebView.setGeolocationEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Snackbar.make(view, "Page Loaded", 5);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Snackbar.make(view, title, 5);
            }
        });
        mWebView.addHttpHeader("X-Requested-With", "");
        mWebView.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.onDestroy();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        mWebView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(String url) {
        mWebView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Log.e(LOG_TAG, "onPageError(errorCode = " + errorCode
                + ",  description = " + description
                + ",  failingUrl = " + failingUrl + ")"
        );
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
            // download handled successfully
        } else {
            // unsuccessful
        }
    }

    @Override
    public void onExternalPageRequest(String url) {
        Log.v(LOG_TAG, "onExternalPageRequest(url = " + url + ")");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mWebView.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!mWebView.onBackPressed()) return;
    }
}
