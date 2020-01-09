package com.cleanandcomfort.smtrick.user_laundryapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cleanandcomfort.smtrick.user_laundryapp.Fragments.Fragment_ContactUS;
import com.cleanandcomfort.smtrick.user_laundryapp.Fragments.Fragment_Send_Request;
import com.cleanandcomfort.smtrick.user_laundryapp.Fragments.Users_Requests_Tab_Fragment;
import com.cleanandcomfort.smtrick.user_laundryapp.Interface.OnFragmentInteractionListener;
import com.cleanandcomfort.smtrick.user_laundryapp.Models.Users;
import com.cleanandcomfort.smtrick.user_laundryapp.Preferences.AppSharedPreference;
import com.cleanandcomfort.smtrick.user_laundryapp.R;
import com.cleanandcomfort.smtrick.user_laundryapp.Repository.Impl.LeedRepositoryImpl;
import com.cleanandcomfort.smtrick.user_laundryapp.Repository.LeedRepository;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity_User extends AppCompatActivity
        implements OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private NavigationView navigationView;
    private Fragment selectedFragement;

    private TextView userEmail, userRole;
    private TextView username;
    Users user;
    LeedRepository leedRepository;
    private AppSharedPreference appSharedPreference;
    //    ImageView ProfileImage;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isNetworkAvailable()) {

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        leedRepository = new LeedRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        user = new Users();
        // NOTE : Just remove the fab button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Boolean per = isStoragePermissionGranted();
        if (per) {
//               Toast.makeText(this, "Storage Premission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Storage Premission Required", Toast.LENGTH_SHORT).show();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.memberregistration);
        View headerview = navigationView.getHeaderView(0);
        username = (TextView) headerview.findViewById(R.id.username);
        userEmail = (TextView) headerview.findViewById(R.id.useremail);
        userRole = (TextView) headerview.findViewById(R.id.userrole);

        getCurrentuserdetails();

        //NOTE:  Open fragment1 initially.
        selectedFragement = new Fragment_Send_Request();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, selectedFragement);
        ft.commit();

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void getCurrentuserdetails() {

        try {

            username.setText(appSharedPreference.getName());
            userEmail.setText(appSharedPreference.getNumber());
            userRole.setText(appSharedPreference.getRole());

        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //NOTE: creating fragment object
        Fragment fragment = null;
        if (id == R.id.serviceproviders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_Send_Request()).commit();

        } else if (id == R.id.user_requests) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Users_Requests_Tab_Fragment()).commit();

        } else if (id == R.id.user_contactus) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_ContactUS()).commit();

        } else if (id == R.id.logout) {

//             clearDataWithSignOut();
            appSharedPreference.clear();
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity_User.this, LoginActivity.class));

        }

        //NOTE: Fragment changing code
        selectedFragement = fragment;
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(String title) {

        // NOTE:  Code to replace the toolbar title based current visible fragment
        getSupportActionBar().setTitle(title);
    }


    @Override
    public void changeFragement(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {

//        }
    }
}
