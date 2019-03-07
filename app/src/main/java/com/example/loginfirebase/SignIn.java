package com.example.loginfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signout;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        user = (TextView) findViewById(R.id.user);
        signout = (Button) findViewById(R.id.signout);
        String currentUser = mAuth.getCurrentUser().getEmail().toString();
        user.setText(currentUser);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsignout();
            }
        });
    }

    private void callsignout() {
        mAuth.signOut();
        Intent i = new Intent(SignIn.this, MainActivity.class);
        finish();
        startActivity(i);
    }
}
