package com.example.root.lifeline.Profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.root.lifeline.R;

public class CompleteReg extends AppCompatActivity {

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
        setContentView(R.layout.activity_complete_reg);
    }
}
