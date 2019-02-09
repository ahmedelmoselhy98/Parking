package com.example.parking.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.parking.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.parking.constants.Constants.FIREBASE_USERS;

public class Database {

    Context context;
    private static final String TAG = Database.class.getSimpleName();
    public DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public Authentication authentication;

    public Database(Context context) {
        this.context = context;
        authentication = new Authentication(context);
    }
    public Database(Context context,Authentication authentication) {
        this.context = context;
        this.authentication = authentication;
    }

    public void addUser(final User user){
        reference.child(FIREBASE_USERS).child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Toast.makeText(context, "success",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }

            }
        });
    }

    public interface UsersCallback {
        void onCallback(List<User> list);
    }

}
