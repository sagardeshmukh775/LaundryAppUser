package com.smartloan.smtrick.user_laundryapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smartloan.smtrick.user_laundryapp.Activities.CropImageActivity;
import com.smartloan.smtrick.user_laundryapp.R;

import java.util.List;

/**
 * Created by akshayejh on 19/12/17.
 */

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ViewHolder> {

    //  public List<String> fileNameList;
    public List<Uri> fileDoneList;
    Context mContext, nContext;
    private static final int REQUEST_CROP_IMAGE = 2342;

    public UploadListAdapter(Context fileNameList, List<Uri> fileDoneList) {

        this.mContext = fileNameList;
        this.fileDoneList = fileDoneList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        nContext = parent.getContext();
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Uri fileDone = fileDoneList.get(position);

        Glide.with(mContext).load(fileDone).apply(new RequestOptions().placeholder(R.drawable.loading)).into(holder.fileDoneView);

        holder.fileDoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = fileDone.toString();
//                Intent i = new Intent(holder.fileDoneView.getContext(),Crop_Selected_Activity.class);
//                i.putExtra("url", uri);
//                holder.fileDoneView.getContext().startActivity(i);

                Intent intent = new Intent(holder.fileDoneView.getContext(), CropImageActivity.class);
                intent.putExtra(CropImageActivity.EXTRA_IMAGE_URI, uri.toString());
                ((Activity) nContext).startActivityForResult(intent, REQUEST_CROP_IMAGE);

                holder.imagecard.setBackgroundColor(Color.GRAY);
            }
        });


    }


    @Override
    public int getItemCount() {
        return fileDoneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView fileNameView;
        public ImageView fileDoneView;
        public CardView imagecard;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            fileDoneView = (ImageView) mView.findViewById(R.id.upload_icon);
            imagecard = (CardView) mView.findViewById(R.id.newcardimage);
        }

    }

}
