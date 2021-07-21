package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Common.Common;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
  private   EditText nameText ,phoneText,emailText ,passwordText ;
  private  Button registerBtn;
    private  TextView googleBtn;
  private TextView loginBtn;

  private FirebaseAuth mAuth;
  FirebaseUser user;
    DatabaseReference reference;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);

        mAuth =FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");

nameText =(EditText)findViewById(R.id.username);
phoneText=(EditText)findViewById(R.id.phone);
emailText=(EditText)findViewById(R.id.emailEditText);
       // googleBtn= (TextView)findViewById(R.id.gbtn);
passwordText=(EditText)findViewById(R.id.passwordEditText);
registerBtn=(Button)findViewById(R.id.signBtn);
loginBtn =(TextView) findViewById(R.id.registerBtn);

        /*oogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });*/
loginBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(SignUpActivity.this ,SignInActivity.class));
    }
});
registerBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        createUser();
    }
});
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient =  GoogleSignIn.getClient(this,gso);
    }
    int RC_SIGN_IN =65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                //..
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                             user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }
    private void createUser() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String name = nameText.getText().toString().trim();
        String phone = phoneText.getText().toString().trim();

        if (name.isEmpty()) {
            nameText.setError("Username is required");
            nameText.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            phoneText.setError("Phone number is required");
            phoneText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailText.setError("email is required");
            emailText.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("provide valid email");
            emailText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordText.setError("password is required");
            passwordText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User users=new User(name, email, phone,password);
                            users.setIsStaff("false");
                            FirebaseDatabase.getInstance().getReference("Users").
                                   child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        if (user.getUid() != null) {
                                            reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    User userProfile = snapshot.getValue(User.class);
                                                    if (userProfile != null) {
                                                        Common.currentUser = userProfile;
                                                        Toast.makeText(SignUpActivity.this, "Login Successfully!" + Common.currentUser.getEmail(), Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(SignUpActivity.this, Home2Activity.class));
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        }
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this,"Failed to register! Try again" ,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"Failed to register! Try again" ,Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
