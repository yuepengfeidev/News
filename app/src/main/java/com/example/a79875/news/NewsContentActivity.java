package com.example.a79875.news;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);// 设置webview支持javascript
        webView.setWebViewClient(new WebViewClient());// 网页只会在当前webview中显示，而不会通过浏览器打开

        // 接受从MainActivity传来的数据
        String title = getIntent().getStringExtra("title");
        String uri = getIntent().getStringExtra("uri");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);// 设置新闻内容标题栏
        webView.loadUrl(uri);// 设置新闻内容界面的地址用于加载新闻网页

    }

    // 给点击返回键进行了处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
