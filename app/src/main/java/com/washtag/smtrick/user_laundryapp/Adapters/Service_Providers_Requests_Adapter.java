package com.washtag.smtrick.user_laundryapp.Adapters;

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
import android.widget.Toast;

import com.washtag.smtrick.user_laundryapp.CallBack.CallBack;
import com.washtag.smtrick.user_laundryapp.Models.Requests;
import com.washtag.smtrick.user_laundryapp.Preferences.AppSharedPreference;
import com.washtag.smtrick.user_laundryapp.R;
import com.washtag.smtrick.user_laundryapp.Repository.Impl.LeedRepositoryImpl;
import com.washtag.smtrick.user_laundryapp.Repository.LeedRepository;
import com.washtag.smtrick.user_laundryapp.Utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itextpdf.text.factories.GreekAlphabetFactory.getString;
import static com.washtag.smtrick.user_laundryapp.Constants.Constant.STATUS_APPROVED;

public class Service_Providers_Requests_Adapter extends RecyclerView.Adapter<Service_Providers_Requests_Adapter.ViewHolder> {

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

    public Service_Providers_Requests_Adapter(Context context, List<Requests> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    public Service_Providers_Requests_Adapter(List<Requests> mUsers) {
        this.uploads = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceproviders_request_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Requests request = uploads.get(position);
        appSharedPreference = new AppSharedPreference(holder.userCard.getContext());
        leedRepository = new LeedRepositoryImpl();

        holder.textViewName.setText(request.getDate());
        holder.textViewMobile.setText(request.getUserName());
        holder.textViewAddress.setText(request.getUserAddress());
        holder.textViewPinCode.setText(request.getUserMobile());
        holder.textViewId.setText(request.getUserPinCode());

        holder.CardApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       
                setLeedStatus(request);
            }
            private void setLeedStatus(Requests user) {
                user.setStatus(STATUS_APPROVED);
                Toast.makeText(holder.CardApprove.getContext(), "Approved Successfully", Toast.LENGTH_SHORT).show();
                updateLeed(user.getRequestId(), user.getLeedStatusMap());
            }
            private void updateLeed(String requestId, Map leedStatusMap) {
                leedRepository.updateRequest(requestId, leedStatusMap, new CallBack() {
                    @Override
                    public void onSuccess(Object object) {


                    }

                    @Override
                    public void onError(Object object) {
                        Utility.showLongMessage(holder.CardApprove.getContext(), getString(R.string.server_error));
                    }
                });
            }
        });

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(holder.userCard.getContext());
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
                dialog1.setContentView(R.layout.customdialogbox_services);

                RecyclerView serviecRecycle = (RecyclerView) dialog1.findViewById(R.id.services_recycle);
                if (request.getServiceList() != null) {
                    services_adapter = new Services_Adapter(holder.userCard.getContext(), request.getServiceList());
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
        public CardView userCard;
        public Button Request;
        public CardView CardApprove;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.namevalue);
            textViewMobile = (TextView) itemView.findViewById(R.id.user_mobilevalue);
            textViewAddress = (TextView) itemView.findViewById(R.id.user_addressvalue);
            textViewPinCode = (TextView) itemView.findViewById(R.id.user_pincodevalue);
            textViewId = (TextView) itemView.findViewById(R.id.user_idvalue);
            userCard = (CardView) itemView.findViewById(R.id.card_userid);
            Request = (Button) itemView.findViewById(R.id.request);
            CardApprove = (CardView) itemView.findViewById(R.id.card_view_approve);

        }
    }

    public void reload(ArrayList<Requests> leedsModelArrayList) {
        uploads.clear();
        uploads.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
