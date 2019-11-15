package com.smartloan.smtrick.user_laundryapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartloan.smtrick.user_laundryapp.Interface.OnFragmentInteractionListener;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Adapters.ViewPagerAdapter;

public class Users_Requests_Tab_Fragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public OnFragmentInteractionListener mListener;

    public Users_Requests_Tab_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteraction("My Requests");
        }
        View view = inflater.inflate(R.layout.fragment_reports_tab, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragement(new Fragment_View_Users_Requests(), "Generated Requests");
        viewPagerAdapter.addFragement(new Fragment_View_Users_Approved_Requests(), "Approved Requests");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabMode(1);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
