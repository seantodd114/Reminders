package com.example.reminders;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.atomic.AtomicBoolean;

public class LogOut extends AsyncTask<Void, Void, Void> {
    onLogOutListener olol;

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseAuth.getInstance().signOut();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        olol.onLogOut();
    }

    public interface onLogOutListener{
        void onLogOut();
    }
}
