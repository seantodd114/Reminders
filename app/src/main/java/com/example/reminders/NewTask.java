package com.example.reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class NewTask extends AppCompatActivity implements Create.onCreateListener{
    EditText name, date, time;
    String tName, type, tDate, tTime;
    Button create;
    Spinner taskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        create = findViewById(R.id.create);
        taskType = findViewById(R.id.taskType);

        create.setOnClickListener(v -> {
            tName = name.getText().toString();
            type = taskType.getSelectedItem().toString();
            tDate = date.getText().toString();
            tTime = time.getText().toString();

            Create bgtask = new Create(tName, type, tDate, tTime, this);
            bgtask.execute();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        taskType = findViewById(R.id.taskType);

        name.setText(savedInstanceState.getString("name"));
        date.setText(savedInstanceState.getString("date"));
        time.setText(savedInstanceState.getString("time"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("name", name.getText().toString());
        outState.putString("date", date.getText().toString());
        outState.putString("time", time.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onComplete() {
        Toast.makeText(NewTask.this, "New reminder created", Toast.LENGTH_LONG).show();
    }
}