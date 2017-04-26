package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.uitl.BitmapUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/24 10:05
 * @description
 */
public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;


    public FriendListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_friend, null);
            viewHolder = new ViewHolder();
            viewHolder.civAvatar = (CircleImageView) convertView.findViewById(R.id.civ_avatar);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvSignature = (TextView) convertView.findViewById(R.id.tv_signature);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (userList.get(position).getUserExtraInformation() != null && userList.get(position).getUserExtraInformation().getAvatarPath() != null)
            viewHolder.civAvatar.setImageBitmap(BitmapUtil.getFitSampleBitmap(userList.get(position).getUserExtraInformation().getAvatarPath(), viewHolder.civAvatar.getWidth(), viewHolder.civAvatar.getHeight()));
        viewHolder.tvUsername.setText(userList.get(position).getUserBasisInformation().getUsername());
        viewHolder.tvSignature.setText(userList.get(position).getUserExtraInformation() != null && userList.get(position).getUserExtraInformation().getSignature() != null ? userList.get(position).getUserExtraInformation().getSignature() : "他什么都没有留下...");
        return convertView;
    }

    private class ViewHolder {
        CircleImageView civAvatar;
        TextView tvUsername;
        TextView tvSignature;
    }
}
