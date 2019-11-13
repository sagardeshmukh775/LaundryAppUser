package com.smartloan.smtrick.user_laundryapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.smartloan.smtrick.user_laundryapp.R;

public class sharedtransitionActivity extends AppCompatActivity {
    PhotoView photoView;
    ProgressBar imageprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedtransition);

        imageprogress = (ProgressBar) findViewById(R.id.image_progress);
        imageprogress.setVisibility(View.VISIBLE);
        photoView = (PhotoView) findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String resid = bundle.getString("url");
            Glide.with(this)
                    .load(resid)
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.loading))
                    .into(photoView);


            imageprogress.setVisibility(View.GONE);

            }
    }
}
