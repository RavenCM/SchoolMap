package com.teemo.schoolmap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teemo.schoolmap.R;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/17 16:19
 * @description
 */
public class FindFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("FindFragment", "onCreateView");
        View parent = inflater.inflate(R.layout.fragment_find, container, false);
        init(parent);
        return parent;
    }

    private void init(View parent) {

    }
}
