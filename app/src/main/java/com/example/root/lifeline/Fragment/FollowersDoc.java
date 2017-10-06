package com.example.root.lifeline.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.lifeline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersDoc extends Fragment {


    public FollowersDoc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers_doc, container, false);
    }

}
