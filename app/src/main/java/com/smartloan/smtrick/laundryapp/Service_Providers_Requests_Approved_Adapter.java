package com.smartloan.smtrick.laundryapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Service_Providers_Requests_Approved_Adapter extends RecyclerView.Adapter<Service_Providers_Requests_Approved_Adapter.ViewHolder> {

    private Context context;
    private List<Requests> uploads;
    AppSharedPreference appSharedPreference;
    LeedRepository leedRepository;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;
    EditText edtDateTime;
    Services_Adapter services_adapter;

    public Service_Providers_Requests_Approved_Adapter(Context context, List<Requests> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    public Service_Providers_Requests_Approved_Adapter(List<Requests> mUsers) {
        this.uploads = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceproviders_request_approved_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Requests user = uploads.get(position);
        appSharedPreference = new AppSharedPreference(holder.userCard.getContext());
        leedRepository = new LeedRepositoryImpl();

        holder.textViewName.setText(user.getDate());
        holder.textViewMobile.setText(user.getUserName());
        holder.textViewAddress.setText(user.getUserAddress());
        holder.textViewPinCode.setText(user.getUserMobile());
        holder.textViewId.setText(user.getUserPinCode());
        holder.txtstatus.setText(user.getStatus());

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(holder.userCard.getContext());
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
                dialog1.setContentView(R.layout.customdialogbox_services);

                RecyclerView serviecRecycle = (RecyclerView) dialog1.findViewById(R.id.services_recycle);
                if (user.getServiceList() != null) {
                    services_adapter = new Services_Adapter(holder.userCard.getContext(), user.getServiceList());
                    serviecRecycle.setAdapter(services_adapter);
                    serviecRecycle.setHasFixedSize(true);
                    serviecRecycle.setLayoutManager(new LinearLayoutManager(holder.userCard.getContext()));
                }
                dialog1.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewMobile;
        public TextView textViewAddress;
        public TextView textViewPinCode;
        public TextView textViewId;
        public TextView txtstatus;
        public CardView userCard;
        public Button Request;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            textViewPinCode = (TextView) itemView.findViewById(R.id.user_pincodevalue);
            textViewId = (TextView) itemView.findViewById(R.id.user_idvalue);
            txtstatus = (TextView) itemView.findViewById(R.id.request_status1);
            userCard = (CardView) itemView.findViewById(R.id.card_userid);
            Request = (Button) itemView.findViewById(R.id.request);

        }
    }

    public void reload(ArrayList<Requests> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
