package com.example.laptophome.news;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptophome.news.api_key.Retrofit_client;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    private Adapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<articles>articls=new ArrayList<>();
    public final  static String app_key="e75646a507ca43a7ae0792895e0b476b";
    RecyclerView.LayoutManager layoutManager;
    private RelativeLayout errorlayout;
    Button EEOR_Retry;
    ImageView error_image;
    TextView error_title,error_message,top_headlines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiplayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        errorlayout=(RelativeLayout)findViewById(R.id.error_layout);
        EEOR_Retry=(Button)findViewById(R.id.retry_button);
        error_title=(TextView)findViewById(R.id.error_title);
        error_message=(TextView)findViewById(R.id.error_message);
        recyclerView=(RecyclerView)findViewById(R.id.recycleview);
        top_headlines=(TextView)findViewById(R.id.top_headlines);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        onswaprefresh("");

    }

    public void Loaddata( String keyword)
    {      swipeRefreshLayout.setRefreshing(true);
    errorlayout.setVisibility(View.GONE);
        app_interface app_interface=Retrofit_client.getRetrofit_client().create(com.example.laptophome.news.app_interface.class);
        String county=Utils.getCountry();
        Locale locale =Locale.getDefault();

        String Languge=String.valueOf(locale.getLanguage());
        Call<news>call;

        if(keyword.length() >0)
        {
            call=app_interface.getsearchablenews(keyword,Languge,"publishedAt",app_key);
        }
        else
        {
            call=app_interface.getnews(county,app_key);
        }



        call.enqueue(new Callback<news>() {
            @Override
            public void onResponse(Call<news> call, Response<news> response) {
                if(response.isSuccessful() && response.body().getArticles() !=null)
                {
                    if(!articls.isEmpty())
                    {
                        articls.clear();
                    }
                    articls=response.body().getArticles();
                    adapter=new Adapter(articls,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else
                {   swipeRefreshLayout.setRefreshing(false);
                    top_headlines.setVisibility(View.INVISIBLE);
                   String errorcode;
                   switch (response.code())
                   {
                       case 404:
                           errorcode="404 not found ";
                           break;
                       case 500:
                           errorcode="505 server not found";
                           break;
                           default:
                               errorcode="unknown error ";
                               break;
                   }
                   error_handling(R.drawable.no_result,"no Result","please Try again\n"+errorcode);
                }
            }

            @Override
            public void onFailure(Call<news> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        MenuItem menuItem=menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(s.length() >2)
                {
                    onswaprefresh(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Loaddata(s);
                return false;
            }
        });


        menuItem.getIcon().setVisible(false,false);

        return true;
    }


    @Override
    public void onRefresh() {
        Loaddata("");
    }

    private void onswaprefresh(final String keyword)
    {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Loaddata(keyword);
            }
        });
    }

    private void error_handling (int imageview,String title,String message)
    {

        if(errorlayout.getVisibility()==View.GONE)
        {
            errorlayout.setVisibility(View.VISIBLE);
        }

        error_title.setText(title);
        error_message.setText(message);
        error_image.setImageResource(imageview);
        EEOR_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onswaprefresh("");
            }
        });
    }
}

