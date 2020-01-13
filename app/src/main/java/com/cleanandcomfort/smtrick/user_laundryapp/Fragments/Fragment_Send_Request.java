package com.cleanandcomfort.smtrick.user_laundryapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cleanandcomfort.smtrick.user_laundryapp.Activities.Send_Request_Activity;
import com.cleanandcomfort.smtrick.user_laundryapp.R;


public class Fragment_Send_Request extends Fragment {

    private Button btnCreate;
    ConstraintLayout layout;
    final int sdk = android.os.Build.VERSION.SDK_INT;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_send_request, container, false);

        btnCreate = (Button) view.findViewById(R.id.btn);
        layout = (ConstraintLayout) view.findViewById(R.id.layout);
        layout.setBackground(getActivity().getResources().getDrawable(R.drawable.userbackground));

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentActivity(v);
            }
        });
        
        return view;
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(getContext(), Send_Request_Activity.class);
        intent.putExtra(Send_Request_Activity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(Send_Request_Activity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(getContext(), intent, options.toBundle());
    }

}
