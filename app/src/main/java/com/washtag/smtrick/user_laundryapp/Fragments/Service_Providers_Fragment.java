package com.washtag.smtrick.user_laundryapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DatabaseReference;
import com.washtag.smtrick.user_laundryapp.Adapters.Service_Providers_Adapter1;
import com.washtag.smtrick.user_laundryapp.CallBack.CallBack;
import com.washtag.smtrick.user_laundryapp.Constants.Constant;
import com.washtag.smtrick.user_laundryapp.Interface.OnFragmentInteractionListener;
import com.washtag.smtrick.user_laundryapp.Models.User;
import com.washtag.smtrick.user_laundryapp.R;
import com.washtag.smtrick.user_laundryapp.Repository.Impl.UserRepositoryImpl;
import com.washtag.smtrick.user_laundryapp.Repository.UserRepository;
import com.washtag.smtrick.user_laundryapp.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Service_Providers_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // NOTE: Removed Some unwanted Boiler Plate Codes
    private OnFragmentInteractionListener mListener;
    private DatabaseReference mdataRefuser;
    private UserRepository leedRepository;
    private List<User> userList;
    private List<User> userList1;

    public Service_Providers_Fragment() {
    }

    RecyclerView listView;
    Service_Providers_Adapter1 adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_users, container, false);
        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Generated");

            leedRepository = new UserRepositoryImpl();
            userList = new ArrayList<>();
            listView = (RecyclerView) view.findViewById(R.id.recycler_view_users);

            readUsers();
        }

        return view;
    }

    private void readUsers() {
        userList.clear();
        leedRepository.readUserByRole(Constant.ROLE_SERVICE_PROVIDER,new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    userList = (ArrayList<User>) object;

                }

                adapter = new Service_Providers_Adapter1(getActivity(), userList);
                //adding adapter to recyclerview
                listView.setAdapter(adapter);
                // CatalogAdapter catalogAdapter = new CatalogAdapter(catalogList);
                listView.setHasFixedSize(true);
                listView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onError(Object object) {
                Utility.showLongMessage(getActivity(), getString(R.string.server_error));
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}