package com.example.mwessj.util;

import com.example.mwessj.bean.City;

import java.util.Comparator;

public class SortUtil implements Comparator<City> {

    @Override
    public int compare(City lettersModel, City t1) {
            return lettersModel.getAllFirstPY().compareTo(t1.getAllFirstPY());
    }
}
