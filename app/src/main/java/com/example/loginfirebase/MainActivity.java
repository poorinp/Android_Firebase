package com.example.loginfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

//        check if the user is logged in
        if (mAuth.getCurrentUser() != null) {
//            User not login
            finish();
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString();
                String getpassword = password.getText().toString();
                callsigni(getemail, getpassword);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString();
                String getpassword = password.getText().toString();
                callsignup(getemail, getpassword);

            }
        });

}

    private void callsignup(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Account Created.", Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(email).build();
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Log.d("test", "User profile updated");
                                    }
                                }
                            });
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Sign Up failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void callsigni(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Test", "Sign in is successful:"+task.isSuccessful());

                if(!task.isSuccessful()) {
                    Log.d("Test", "Sign In With Email Failed:" + task.getException());
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, SignIn.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }
}