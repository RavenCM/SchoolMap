package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.loopj.android.image.SmartImageView;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.MessageImage;
import com.teemo.schoolmap.application.config.UrlConfig;

import java.util.List;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.20 20:41
 * @description
 */
public class MessageImageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageImage> messageImageList;

    public MessageImageAdapter(Context context, List<MessageImage> messageImageList) {
        this.context = context;
        this.messageImageList = messageImageList;
    }

    public List<MessageImage> getMessageImageList() {
        return messageImageList;
    }

    public void setMessageImageList(List<MessageImage> messageImageList) {
        this.messageImageList = messageImageList;
    }

    @Override
    public int getCount() {
        return messageImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_text_image, null);
            viewHolder = new ViewHolder();
            viewHolder.sivImage = (SmartImageView) convertView.findViewById(R.id.siv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.i("path", UrlConfig.IMAGE + messageImageList.get(position).getPath());
        viewHolder.sivImage.setImageUrl(UrlConfig.IMAGE + messageImageList.get(position).getPath(), R.drawable.loading, R.drawable.loading);
        return convertView;
    }

    private class ViewHolder{
        SmartImageView sivImage;
    }
}
