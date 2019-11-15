package com.smartloan.smtrick.user_laundryapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.user_laundryapp.Constants.Constants;
import com.smartloan.smtrick.user_laundryapp.Adapters.ImageAdapter;
import com.smartloan.smtrick.user_laundryapp.Interface.OnFragmentInteractionListener;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Models.Upload;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Advertise extends Fragment {

    private static int NUM_PAGES = 0;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private DatabaseReference mDatabase;

    // NOTE: Removed Some unwanted Boiler Plate Codes
    private OnFragmentInteractionListener mListener;
    private List<Upload> uploads;
    private List<Upload> uploads1;
    ViewPager viewPager;

    private boolean flag = false;

    public Fragment_Advertise() {
    }

    Context context;
    //    Button btnhl, btnpl, btnml, btntp, btnbt, btndl;
    ProgressBar progressBar;
    String abcd = "abcd";
    Animation animBounce;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adds, container, false);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        uploads = new ArrayList<>();
        uploads1 = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.ADVERTISE_PATH_UPLOADS);

        Query query = FirebaseDatabase.getInstance().getReference("Advertise");

        query.addValueEventListener(valueEventListener);

        viewPager = view.findViewById(R.id.viewPager);
        dots = new ImageView[0];

        /*After setting the adapter use the timer */


        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Adds");
        }

        return view;
    }


    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            uploads.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Upload upload = postSnapshot.getValue(Upload.class);

                uploads.add(upload);
                int size = uploads.size() - 1;
                uploads1.clear();
                for (int i = size; i >= 0; i--) {
                    uploads1.add(uploads.get(i));
                }

            }
            NUM_PAGES = uploads1.size();
            showDots();
            ImageAdapter adapter = new ImageAdapter(getContext(), uploads1);
            viewPager.setAdapter(adapter);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 500, 3000);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void showDots() {

        dots = new ImageView[uploads.size()];

        // dots = new ImageView[dotscount];

        for (int i = 0; i < uploads.size(); i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {

                    for (int i = 0; i < uploads.size(); i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

                } catch (Exception e) {

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for zoom in animation
        if (animation == animBounce) {
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // NOTE: This is the part that usually gives you the error
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if (isVisible()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < NUM_PAGES - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        flag = false;
        super.onDestroy();
    }
}

