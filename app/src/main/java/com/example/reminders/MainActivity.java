package com.example.reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements LogOut.onLogOutListener, DeleteDialog.DeleteListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cr = db.collection(FirebaseAuth.getInstance().getUid());
    TaskAdapter adapter;
    public static RecyclerView.ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(myToolbar);

        recycle();
    }

    private void recycle(){
        Query q = cr.orderBy("date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>().setQuery(q, Task.class).build();
        adapter = new TaskAdapter(options);
        RecyclerView rv = findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MainActivity.viewHolder = viewHolder;
                DialogFragment dialog = new DeleteDialog();
                dialog.show(getSupportFragmentManager(), "delete");
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newTask:
                startActivity(new Intent(getApplicationContext(), NewTask.class));
                return true;
            case R.id.logOut:
                LogOut bgtask = new LogOut();
                bgtask.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLogOut() {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    @Override
    public void onDelete() {
        adapter.deleteTask(viewHolder.getAdapterPosition());
    }

    @Override
    public void onCancel() {
        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }
}