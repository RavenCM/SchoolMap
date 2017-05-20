package com.teemo.schoolmap.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.Poi;

import java.util.List;

import static com.teemo.schoolmap.R.color.colorValid;


/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.20 13:59
 * @description
 */
public class PoiAdapter extends BaseAdapter {

    private List<Poi> poiList;

    private Context context;

    public PoiAdapter(Context context, List<Poi> poiList) {
        this.context = context;
        this.poiList = poiList;
    }

    public List<Poi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<Poi> poiList) {
        this.poiList = poiList;
    }

    @Override
    public int getCount() {
        return poiList.size();
    }

    @Override
    public Object getItem(int position) {
        return poiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_poi, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(poiList.get(position).getTitle());
        viewHolder.tvContent.setText(poiList.get(position).getContent());
        if (poiList.get(position).getIsEnable() == 1){
            viewHolder.tvStatus.setText("有效");
            viewHolder.tvStatus.setBackgroundColor(Color.parseColor("#22ff00"));
        } else {
            viewHolder.tvStatus.setText("无效");
            viewHolder.tvStatus.setBackgroundColor(Color.parseColor("#ff1500"));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvStatus;
    }
}
