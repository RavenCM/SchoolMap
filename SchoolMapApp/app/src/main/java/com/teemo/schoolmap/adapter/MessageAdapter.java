package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.Message;
import com.teemo.schoolmap.application.bean.MessageImage;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.BitmapUtil;
import com.teemo.schoolmap.application.uitl.WindowUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        int length = (WindowUtil.getScreenWidth(context) - (3 * WindowUtil.dipToPx(context, 8)) / 2) / 3;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_text, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
//            viewHolder.gvImage = (GridView) convertView.findViewById(R.id.gv_image);
            viewHolder.sivImageList = new ArrayList<>();
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image1));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image2));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image3));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image4));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image5));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image6));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image7));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image8));
            viewHolder.sivImageList.add((SmartImageView) convertView.findViewById(R.id.siv_image9));
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
//            if (messageList.get(position).getMessageImageList() != null && messageList.get(position).getMessageImageList().size() > 0){
//                viewHolder.messageImageAdapter = new MessageImageAdapter(context, messageList.get(position).getMessageImageList());
//            }
//            viewHolder.gvImage.setAdapter(viewHolder.messageImageAdapter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(messageList.get(position).getName());
        viewHolder.tvContent.setText(messageList.get(position).getContent());
        viewHolder.tvTime.setText(new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒").format(messageList.get(position).getCreationDate()));
        if (messageList.get(position).getMessageImageList() != null && messageList.get(position).getMessageImageList().size() > 0) {
            for (int i = 0; i < 9; i++) {
                if (i < messageList.get(position).getMessageImageList().size()) {
                    MessageImage messageImage = messageList.get(position).getMessageImageList().get(i);
                    String path = UrlConfig.IMAGE + messageImage.getPath();
                    ViewGroup.LayoutParams layoutParams = viewHolder.sivImageList.get(i).getLayoutParams();
                    layoutParams.height = length;
                    layoutParams.width = length;
                    viewHolder.sivImageList.get(i).setLayoutParams(layoutParams);
                    viewHolder.sivImageList.get(i).setImageUrl(path, R.drawable.loading, R.drawable.loading);
                }else {
                    viewHolder.sivImageList.get(i).setVisibility(View.GONE);
                }

            }
//            viewHolder.gvImage.setVisibility(View.VISIBLE);
//            List<MessageImage> messageImageList = messageList.get(position).getMessageImageList();
//            viewHolder.messageImageAdapter.setMessageImageList(messageImageList);
//            viewHolder.messageImageAdapter.notifyDataSetChanged();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvContent;
        //        GridView gvImage;
        List<SmartImageView> sivImageList;
        TextView tvTime;

//        MessageImageAdapter messageImageAdapter;
    }
}
