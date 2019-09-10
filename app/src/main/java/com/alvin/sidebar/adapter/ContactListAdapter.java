package com.alvin.sidebar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alvin.sidebar.MainActivity;
import com.alvin.sidebar.R;
import com.alvin.sidebar.model.FriendEntity;
import com.alvin.sidebar.util.Section;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    private List<FriendEntity> list;
    private MainActivity activity;
    private ContactScrollerAdapter mContactScrollerAdapter;

    public ContactListAdapter(List<FriendEntity> list, MainActivity activity, ContactScrollerAdapter contactScrollerAdapter) {
        this.list = list;
        this.activity = activity;
        this.mContactScrollerAdapter = contactScrollerAdapter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FriendEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null || convertView.getTag()==null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_list_contact,parent,false);
            holder = new ViewHolder();
            holder.ll_group = convertView.findViewById(R.id.ll_group);
            holder.tv_group = convertView.findViewById(R.id.tv_group);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        FriendEntity friendEntity = getItem(position);
        holder.tv_name.setText(friendEntity.getUserName());
        Section s = mContactScrollerAdapter.fromItemIndex(position);
        if (s.getIndex() == position) {
            holder.ll_group.setVisibility(View.VISIBLE);
            holder.tv_group.setText(s.getTitle());
        } else {
            holder.ll_group.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        LinearLayout ll_group;
        TextView tv_group;
        TextView tv_name;
    }
}
