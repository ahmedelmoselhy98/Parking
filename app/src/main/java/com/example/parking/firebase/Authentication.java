package com.example.parking.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.parking.activities.MainActivity;
import com.example.parking.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    Context context;
    private static final String TAG = Authentication.class.getSimpleName();
    public Database database;
    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    ProgressDialog progressDialog;



    public Authentication(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(context);
        database = new Database(context,this);
    }

    public void signIn(String email, String password){
        progressDialog.setMessage("دخول...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmailAndPassword:success");
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                    progressDialog.dismiss();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                    Toast.makeText(context, "حدث خطأ برجاء المحاوله مره اخري ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        });

    }



//    public void resetPassword(String email){
//        progressDialog.setMessage("دخول...");
//        progressDialog.show();
//        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "sendPasswordResetEmail:success");
//                    Toast.makeText(context, "success",
//                            Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "sendPasswordResetEmail:failure", task.getException());
//                    Toast.makeText(context, context.getResources().getString(R.string.error_reset_password),
//                            Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
//
//            }
//        });
//    }
    public void signUp(final User user){
        String email=user.getEmail();
        String password=user.getPassword();
        progressDialog.setMessage("تسجيل...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user.setId(mAuth.getCurrentUser().getUid());
                            database.addUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "حدث خطأ برجاء المحاوله مره اخري ", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

}
