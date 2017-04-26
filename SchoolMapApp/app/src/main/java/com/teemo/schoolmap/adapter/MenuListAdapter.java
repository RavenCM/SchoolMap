package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.Menu;

import java.util.List;


/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/24 14:32
 * @description
 */
public class MenuListAdapter extends BaseAdapter {
    private List<Menu> menuList;
    private Context context;

    public MenuListAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_menu, null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(menuList.get(position).getTitle());
        viewHolder.ivIcon.setBackgroundResource(menuList.get(position).getIcon());
        return convertView;
    }

    private class ViewHolder{
        ImageView ivIcon;
        TextView tvTitle;
    }
}
