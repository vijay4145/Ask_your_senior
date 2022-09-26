package com.example.askyoursenior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.askyoursenior.databinding.ActivityLoginBinding;
import com.example.askyoursenior.firebaseoperation.RealtimeDatabase;
import com.example.askyoursenior.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.signInButton.setOnClickListener(view -> signIn());
        activityLoginBinding.continueBtn.setOnClickListener(view -> moveToHomePage());

    }

    private void moveToHomePage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Toast.makeText(this, "please signin with google", Toast.LENGTH_SHORT).show();
        }
        else if(!anyErrorForBlankFields()){
            Toast.makeText(this, "Signincompleted", Toast.LENGTH_SHORT).show();
            String name = activityLoginBinding.usernameEdittext.getText().toString();
            String email = user.getEmail();
            String uid = user.getUid();
            String number = user.getPhoneNumber();
            String username = email.split("@")[0];
            String orgname = activityLoginBinding.organizationEdittext.getText().toString();
            User muser = new User(name, username, number, uid, orgname);
            RealtimeDatabase.PushUserDetailsAndLoadHomePageActivity(this, muser); //this method will pushuserdetail and load homepage activity
        }
    }


    private void signIn() {
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
                Log.d("errrors", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w("errrors", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("quizz", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("quizz", "signInWithCredential:failure", task.getException());
                        updateUI(null);
                    }


                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null) {
            activityLoginBinding.usernameEdittext.setText(user.getDisplayName());
            anyErrorForBlankFields();
        }

    }

    private boolean anyErrorForBlankFields(){
        String errormsg = "This field cannot be blank";
        boolean iserror = false;
        if(Objects.requireNonNull(activityLoginBinding.organizationEdittext.getText()).toString().equals("")){
            activityLoginBinding.organizationEdittext.setError(errormsg);
            iserror = true;
        }
        if(Objects.requireNonNull(activityLoginBinding.usernameEdittext.getText()).toString().equals("")){
            activityLoginBinding.usernameEdittext.setError(errormsg);
            iserror = true;
        }
        return iserror;
    }

}