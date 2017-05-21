package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.uitl.BitmapUtil;
import com.teemo.schoolmap.application.uitl.WindowUtil;

import java.util.List;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.21 00:50
 * @description
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> pathList;

    public ImageAdapter(Context context, List<String> pathList) {
        this.context = context;
        this.pathList = pathList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    @Override
    public int getCount() {
        return pathList.size();
    }

    @Override
    public Object getItem(int position) {
        return pathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int length = (WindowUtil.getScreenWidth(context) - (3 * WindowUtil.dipToPx(context, 8)) / 2) / 3;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_image, null);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, length);
            convertView.setLayoutParams(params);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivImage.setImageBitmap(BitmapUtil.getFitSampleBitmap(pathList.get(position), length, length));
        return convertView;
    }

    private class ViewHolder {
        ImageView ivImage;
    }
}
