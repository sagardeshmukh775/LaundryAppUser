package com.smartloan.smtrick.laundryapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Service_Providers_Adapter extends RecyclerView.Adapter<Service_Providers_Adapter.ViewHolder> {

    private Context context;
    private List<User> uploads;
    AppSharedPreference appSharedPreference;
    LeedRepository leedRepository;

    public Service_Providers_Adapter(Context context, List<User> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    public Service_Providers_Adapter(List<User> mUsers) {
        this.uploads = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceproviders_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = uploads.get(position);
        appSharedPreference = new AppSharedPreference(holder.userCard.getContext());
        leedRepository = new LeedRepositoryImpl();

        holder.textViewName.setText(user.getName());
        holder.textViewMobile.setText(user.getNumber());
        holder.textViewAddress.setText(user.getAddress());
        holder.textViewPinCode.setText(user.getPincode());
        holder.textViewId.setText(user.getUserid());

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Requests requests = new Requests();
                requests.setServiceProviderId(user.getUserid());
                requests.setUserId(appSharedPreference.getUserid());
                requests.setUserAddress(appSharedPreference.getAddress());
                requests.setUserMobile(appSharedPreference.getNumber());
                requests.setUserPinCode(appSharedPreference.getPincode());
                requests.setRequestId(Constant.REQUESTS_TABLE_REF.push().getKey());
                leedRepository.sendRequest(requests, new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        Toast.makeText(holder.userCard.getContext(), "Submitted", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
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
        public CardView userCard;
        public Button Request;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            textViewPinCode = (TextView) itemView.findViewById(R.id.user_pincodevalue);
            textViewId = (TextView) itemView.findViewById(R.id.user_idvalue);
            userCard = (CardView) itemView.findViewById(R.id.card_userid);
            Request = (Button) itemView.findViewById(R.id.request);

        }
    }

    public void reload(ArrayList<User> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
