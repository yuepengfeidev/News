package com.example.a79875.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a79875.news.fragment.HealthFragment;
import com.example.a79875.news.fragment.ITFragment;
import com.example.a79875.news.fragment.KeJiFragment;
import com.example.a79875.news.fragment.SocialFragment;
import com.example.a79875.news.fragment.TiYuFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private String[] titles = {"社会", "体育","科技","健康","IT"};
    private MyAdapter myAdapter;
    public static final int SOCIAL_ITEM = 1;
    public static final int TIYU_ITEM = 2;
    public static final int KEJI_ITEM = 3;
    public static final int JIANKANG_ITEM = 4;
    public static final int IT_Item = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        fragmentList = new ArrayList<>();
        fragmentList.add(new SocialFragment());
        fragmentList.add(new TiYuFragment());
        fragmentList.add(new KeJiFragment());
        fragmentList.add(new HealthFragment());
        fragmentList.add(new ITFragment());
        myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
        // 绑定
        mTabLayout.setupWithViewPager(mViewPager);

    }

    // 不同新闻类型返回各自的请求地址
    public static String responseNew(int item) {
        // 初始化为天行数据的社会新闻请求地址,num=50：50条数据,rand=1:随机刷新
        String address = "http://api.tianapi.com/social/?key=0d04e5f2e6e958023505eb156bc56ef9&num=50&rand=1";
        switch (item) {
            case SOCIAL_ITEM:
                break;
            case TIYU_ITEM:
                address = address.replace("social", "tiyu");// 更换为体育新闻请求地址
                break;
            case KEJI_ITEM:
                address = address.replace("social","keji");
                break;
            case JIANKANG_ITEM:
                address = address.replace("social","health");
                break;
            case IT_Item:
                address = address.replace("social","it");
                break;
            default:
                break;
        }
        return address;
    }


    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public CharSequence getPageTitle(int i){
            return titles[i];
        }
    }
}
