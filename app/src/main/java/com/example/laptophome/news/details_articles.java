package com.example.laptophome.news;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class details_articles extends AppCompatActivity  implements AppBarLayout.OnOffsetChangedListener{

    ImageView imageView;
    TextView app_title,appbar_subtitle,title,date,time;
    FrameLayout app_date;
    LinearLayout titleppbar;
    AppBarLayout appBarLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    Toolbar toolbar;
    Boolean ishidetoolbarview=false;
    String murl,mimage,mtitle,mdate,msource,mauthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_articles);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle("");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
      collapsingToolbarLayout.setTitle("");
      appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
      appBarLayout.addOnOffsetChangedListener(this);

      app_date=(FrameLayout)findViewById(R.id.date_behavior);
      titleppbar=(LinearLayout)findViewById(R.id.title_appbar);
      imageView=(ImageView)findViewById(R.id.backdrop);
      app_title=(TextView)findViewById(R.id.title_on_appbar);
      appbar_subtitle=(TextView)findViewById(R.id.subtitle_on_appbar);
      title=(TextView)findViewById(R.id.title);
      date=(TextView)findViewById(R.id.date);
        Intent intent=getIntent();
        murl=intent.getStringExtra("url");
        msource=intent.getStringExtra("source");
        mimage=intent.getStringExtra("imageurl");
        mdate=intent.getStringExtra("date");
        mtitle=intent.getStringExtra("title");
        mauthor=intent.getStringExtra("author");
       Picasso.get().load(mimage).into(imageView);
       app_title.setText(msource);
       date.setText(Utils.DateFormat(mdate));
       title.setText(mtitle);
       appbar_subtitle.setText(murl);

        initwebview(murl);

    }

    public void initwebview(String url)
    {
        WebView webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            int maxscroll=appBarLayout.getTotalScrollRange();
            float percetage=(float)Math.abs(i) / (float) maxscroll;
             if(percetage ==1f && ishidetoolbarview)
             {
                 app_date.setVisibility(View.GONE);
                 titleppbar.setVisibility(View.VISIBLE);
                 ishidetoolbarview =! ishidetoolbarview;
             }
             else if(percetage < 1f && ishidetoolbarview)
             {
                 app_date.setVisibility(View.VISIBLE);
                 titleppbar.setVisibility(View.GONE);
                 ishidetoolbarview =! ishidetoolbarview;
             }


    }
}
