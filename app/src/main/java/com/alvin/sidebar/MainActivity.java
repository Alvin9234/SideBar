package com.alvin.sidebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.alvin.sidebar.adapter.ContactListAdapter;
import com.alvin.sidebar.adapter.ContactScrollerAdapter;
import com.alvin.sidebar.model.FriendEntity;
import com.alvin.sidebar.view.SideBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.sideBar)
    SideBar sideBar;

    private List<FriendEntity> list = new ArrayList<>();
    public String[] INDEX_STRING = {"安徽", "北京", "成都", "大连", "福建", "广州", "河南","河北","淮北",
            "江苏", "K", "L", "M", "南京", "欧阳峰","欧阳振华", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private ContactListAdapter adapter;
    private ContactScrollerAdapter mContactScrollerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        addListener();
    }

    private void initData() {
        int len = INDEX_STRING.length;
        for (int i = 0; i < len; i++) {
            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setUserName(INDEX_STRING[i]+"_friend_"+i);
            list.add(friendEntity);
        }
        mContactScrollerAdapter = new ContactScrollerAdapter(list);
        List<String> sectionList = mContactScrollerAdapter.getSectionsTitles();
        sideBar.setIndexText(sectionList);
        adapter = new ContactListAdapter(list,MainActivity.this, mContactScrollerAdapter);
        listView.setAdapter(adapter);
    }

    private void addListener(){
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s,int index) {
                listView.setSelection(mContactScrollerAdapter.positionFromSection(index));
            }
        });
    }
}
