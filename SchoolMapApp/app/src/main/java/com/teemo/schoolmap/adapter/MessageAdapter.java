package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.Message;
import com.teemo.schoolmap.application.bean.MessageImage;

import java.util.List;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.20 20:13
 * @description
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_text, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.gvImage = (GridView) convertView.findViewById(R.id.gv_image);
            if (messageList.get(position).getMessageImageList() != null && messageList.get(position).getMessageImageList().size() > 0){
                viewHolder.messageImageAdapter = new MessageImageAdapter(context, messageList.get(position).getMessageImageList());
            }
            viewHolder.gvImage.setAdapter(viewHolder.messageImageAdapter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(messageList.get(position).getName());
        viewHolder.tvContent.setText(messageList.get(position).getContent());
        if (messageList.get(position).getMessageImageList() != null && messageList.get(position).getMessageImageList().size() > 0) {
            viewHolder.gvImage.setVisibility(View.VISIBLE);
            List<MessageImage> messageImageList = messageList.get(position).getMessageImageList();
            viewHolder.messageImageAdapter.setMessageImageList(messageImageList);
            viewHolder.messageImageAdapter.notifyDataSetChanged();
        } else {
            viewHolder.gvImage.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvContent;
        GridView gvImage;
        MessageImageAdapter messageImageAdapter;
    }
}
