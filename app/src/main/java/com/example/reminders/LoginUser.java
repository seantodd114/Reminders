package com.example.reminders;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginUser extends AsyncTask<Void, Void, Void> {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String emString;
    String pwString;
    onLoginListener oll;

    public LoginUser(String emString, String pwString, onLoginListener oll) {
        this.emString = emString;
        this.pwString = pwString;
        this.oll = oll;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AtomicBoolean done = new AtomicBoolean(false);
            fAuth.signInWithEmailAndPassword(emString, pwString).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Login: ", fAuth.getUid());
                    done.set(true);
                } else {
                    Log.d("Login: ", task.getException().getMessage());
                    done.set(true);
                }
            });
        while(!done.get()) { }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        oll.onLogin(fAuth.getCurrentUser() != null);
    }

    public interface onLoginListener{
        void onLogin(Boolean userExists);
    }

}
