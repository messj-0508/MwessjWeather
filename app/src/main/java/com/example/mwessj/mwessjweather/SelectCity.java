package com.example.mwessj.mwessjweather;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mwessj.app.MyApplication;
import com.example.mwessj.bean.City;
import com.example.mwessj.mlayout.ClearEditText;
import com.example.mwessj.mlayout.Myadapter;

import java.util.ArrayList;
import java.util.List;

public class SelectCity extends Activity implements View.OnClickListener{

    private ImageView BackBtn;
    private ClearEditText mClearEditText;
    private ListView mList;
    private List<City> cityList;
    private ArrayList<City> filterDataList;

    private Myadapter myadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);

        initViews();
    }

    private void initViews() {
        BackBtn = (ImageView) findViewById(R.id.title_back);
        BackBtn.setOnClickListener(this);

        mClearEditText = (ClearEditText) findViewById(R.id.search_city);

        mList = (ListView) findViewById(R.id.title_list);
        MyApplication myApplication = (MyApplication) getApplication();
        cityList = myApplication.getmCityList();
        filterDataList = new ArrayList<City>(cityList);
        myadapter = new Myadapter(SelectCity.this, cityList);
        mList.setAdapter(myadapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = filterDataList.get(position);
                Intent i = new Intent();
                i.putExtra("cityCode",city.getNumber());
                setResult(RESULT_OK, i);
                finish();
            }
        });
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s);
            }
        });
    }

    private void filterData(Editable filterStr) {
        filterDataList = new ArrayList<City>();
        Log.d("Filter",filterStr.toString());

        if (TextUtils.isEmpty(filterStr)){
            for (City city: cityList){
                filterDataList.add(city);
            }
        }else {
            filterDataList.clear();
            for (City city: cityList)
                if (city.getCity().indexOf(filterStr.toString()) != -1){
                    filterDataList.add(city);
                }
        }
        myadapter.updateListView(filterDataList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.title_back:
                Intent i = new Intent();
                i.putExtra("cityCode","101160101");
                setResult(RESULT_OK, i);
                finish();
                break;
            default:
                break;
        }
    }
}
