package com.example.a79875.news.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a79875.news.MainActivity;
import com.example.a79875.news.NewsContentActivity;
import com.example.a79875.news.R;
import com.example.a79875.news.Title;
import com.example.a79875.news.adapter.TitleAdapter;
import com.example.a79875.news.gson.News;
import com.example.a79875.news.gson.NewsList;
import com.example.a79875.news.util.HttpUtil;
import com.example.a79875.news.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.a79875.news.MainActivity.SOCIAL_ITEM;
import static com.example.a79875.news.MainActivity.responseNew;


public class SocialFragment extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private List<Title> titleList = new ArrayList<>();
    private TitleAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);

        initView();
        requestNew(SOCIAL_ITEM);
        return view;
    }

    private void initView() {
        listView = (ListView)view.findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);

        adapter = new TitleAdapter(getActivity(),R.layout.recycler_view_item,titleList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),NewsContentActivity.class);
                Title title = titleList.get(position);
                intent.putExtra("title",title.getDescription());
                intent.putExtra("uri",title.getUri());
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                requestNew(SOCIAL_ITEM);
            }
        });

    }

    /**
     * 请求处理数据
     */
    public void requestNew(int itemName){

        // 根据返回到的 URL 链接进行申请和返回数据
        String address = responseNew(itemName);    // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(), "新闻加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsList newlist = Utility.parseJsonWithGson(responseText);
                final int code = newlist.code;
                if (code == 200){
                    titleList.clear();
                    for (News news:newlist.newsList){
                        Title title = new Title(news.title,news.description,news.picUrl, news.url);
                        titleList.add(title);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            listView.setSelection(0);
                            swipeRefreshLayout.setRefreshing(false);
                        };
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "数据错误返回",Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }
}
