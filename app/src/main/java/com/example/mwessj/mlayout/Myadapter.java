package com.example.mwessj.mlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mwessj.bean.City;
import com.example.mwessj.mwessjweather.R;

import java.util.ArrayList;
import java.util.List;

public class Myadapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<City> mDatas;
    public Myadapter(Context context, List<City> datas){
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    public void  updateListView(List<City> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_listview, parent, false);
            holder = new ViewHolder();
            holder.cityTv = (TextView) convertView.findViewById(R.id.cityTv);
            holder.letrersTv = (TextView) convertView.findViewById(R.id.lettersTv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        City city = mDatas.get(position);
        int firstPosition = getNmaeForPosition(position);
        int index = getPositionForNmae(firstPosition);
        if (index == position){
            holder.letrersTv.setVisibility(View.VISIBLE);
            holder.letrersTv.setText(city.getFirstPY());
        } else {
            holder.letrersTv.setVisibility(View.GONE);
        }
        holder.cityTv.setText(city.getCity());

        return convertView;
    }

    private class ViewHolder{
        TextView letrersTv;
        TextView cityTv;
    }

    /**
     * 通过首字母获取该首字母要显示的第一个人的下标
     *
     * @param position
     * @return
     */
    public int getPositionForNmae(int position) {
        for (int i = 0; i < getCount(); i++) {
            String letter = mDatas.get(i).getAllFirstPY();
            //首字母显示
            char firstChar = letter.toUpperCase().charAt(0);
            if (firstChar == position) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 根据名称拿到下标
     *
     * @param position
     * @return
     */
    private int getNmaeForPosition(int position) {
        return mDatas.get(position).getAllFirstPY().charAt(0);
    }



}
