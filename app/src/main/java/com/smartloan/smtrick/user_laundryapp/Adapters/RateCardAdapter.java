package com.smartloan.smtrick.user_laundryapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smartloan.smtrick.user_laundryapp.R;

import java.util.List;

/**
 * Created by akshayejh on 19/12/17.
 */

public class RateCardAdapter extends RecyclerView.Adapter<RateCardAdapter.ViewHolder> {

    //  public List<String> fileNameList;
    public List<Uri> fileDoneList;
    Context mContext, nContext;
    private static final int REQUEST_CROP_IMAGE = 2342;

    public RateCardAdapter(Context fileNameList, List<Uri> fileDoneList) {

        this.mContext = fileNameList;
        this.fileDoneList = fileDoneList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_caed_layout, parent, false);
        nContext = parent.getContext();
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Uri fileDone = fileDoneList.get(position);

        Glide.with(mContext).load(fileDone).apply(new RequestOptions().placeholder(R.drawable.loading)).into(holder.fileDoneView);

        holder.imagecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(holder.imagecard.getContext());
                dialog.setContentView(R.layout.customdialogboximagedisplay);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                final ImageView image = (ImageView) dialog.findViewById(R.id.floorimage);
                Glide.with(holder.imagecard.getContext())
                        .load(fileDone)
                        .apply(new RequestOptions()
                        .placeholder(R.drawable.loading))
                        .into(image);

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
