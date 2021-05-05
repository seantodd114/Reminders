package com.example.reminders;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class SignUpUser extends AsyncTask<Void, Void, Void> {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String emString;
    String pwString;
    onSignUpListener osul;

    public SignUpUser(String emString, String pwString, onSignUpListener osul) {
        this.emString = emString;
        this.pwString = pwString;
        this.osul = osul;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AtomicBoolean done = new AtomicBoolean(false);
            fAuth.createUserWithEmailAndPassword(emString, pwString).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    done.set(true);
                } else {
                    Log.d("SignUp: ", task.getException().getMessage());
                    done.set(true);
                }
            });
        while(!done.get()){}
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        osul.onSignUp(fAuth.getCurrentUser() != null);
    }

    public interface onSignUpListener{
        void onSignUp(Boolean userExists);
    }
}
