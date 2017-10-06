package com.example.root.lifeline.Profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.example.root.lifeline.R;
import com.example.root.lifeline.adapters.PagerAdapter1;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class DocProfile extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    RelativeLayout content;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Snackbar.make(content, "Successfully Signed in  Welcome "
                        , Snackbar.LENGTH_SHORT)
                        .show();
                //startActivity(new Intent(DocProfile.this,CompleteReg.class));

            }else
            {
                Snackbar.make(content, "Sorry we couldn't sign in, Try again later "
                        , Snackbar.LENGTH_SHORT)
                        .show();
                finish();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        content = (RelativeLayout) findViewById(R.id.contentRela);

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build()
                    ,SIGN_IN_REQUEST_CODE);

        }else
        {
            Snackbar.make(content, "Welcome " + FirebaseAuth.getInstance().getCurrentUser()
                            .getEmail()
                    , Snackbar.LENGTH_SHORT)
                    .show();
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("POST"));
        tabLayout.addTab(tabLayout.newTab().setText("INFO"));
        tabLayout.addTab(tabLayout.newTab().setText("FOLLOWERS"));
        tabLayout.addTab(tabLayout.newTab().setText("CHAT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.Viewpager);

        PagerAdapter1 adapter = new PagerAdapter1(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });


    }
}
