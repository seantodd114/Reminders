package com.example.reminders;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Create extends AsyncTask<Void, Void, Void> {
    String name, type, date, time;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    onCreateListener ocl;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Create(String tName, String type, String tDate, String tTime, onCreateListener ocl) {
        this.name = tName;
        this.type = type;
        this.date = tDate;
        this.time = tTime;
        this.ocl = ocl;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AtomicBoolean done = new AtomicBoolean(false);

        DocumentReference dr = db.collection(fAuth.getCurrentUser().getUid()).document();

        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("name", name);
        data.put("date", date);
        data.put("time", time);


        dr.set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                done.set(true);
            }else{
                done.set(true);
                Log.d("Create", task.getException().getMessage());
            }
        });
        while(!done.get()){}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ocl.onComplete();
    }

    public interface onCreateListener{
        void onComplete();
    }
}
