package com.crumet.musicleap;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crumet.musicleap.fragments.SongsFragment;
import com.crumet.musicleap.helper.BottomNavigationHelper;
import com.crumet.musicleap.helper.RuntimePermissionsActivity;

public class MainActivity extends RuntimePermissionsActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 20;

    private BottomNavigationView bottomNavigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request permissions
        MainActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.defaultText));
        toolbar.setLogo(R.drawable.ic_songs);
        toolbar.setTitle("Music");

        initViews();
        initListeners();
        BottomNavigationHelper.disableShiftMode(bottomNavigation);
    }

    private void initListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_songs:
                        pushFragment(new SongsFragment());
                        break;
                    case R.id.nav_artists:
                        break;
                    case R.id.nav_albums:
                        break;
                    case R.id.nav_playlists:
                        break;


                }
                return true;
            }
        });
    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.nav_main);
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * method to push fragments for bottom navigation
     */
    protected void pushFragment(Fragment fragment) {

        if (fragment == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        pushFragment(new SongsFragment());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
