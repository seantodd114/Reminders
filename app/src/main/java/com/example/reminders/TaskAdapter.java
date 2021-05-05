package com.example.reminders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TaskAdapter extends FirestoreRecyclerAdapter<Task, TaskAdapter.TaskHolder> {

    public TaskAdapter(@NonNull FirestoreRecyclerOptions<Task> options) {
        super(options);
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvDate;
        TextView tvTime;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.card_name);
            tvDate = itemView.findViewById(R.id.card_date);
            tvTime = itemView.findViewById(R.id.card_time);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull TaskHolder holder, int position, @NonNull Task model) {
        holder.tvName.setText(model.getName());
        if(!model.getType().equals("To-Do List")) {
            holder.tvDate.setText(model.getDate());
            holder.tvTime.setText(model.getTime());
        }else{
            holder.tvDate.setText(" ");
            holder.tvTime.setText(" ");
        }
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new TaskHolder(v);
    }

    public void deleteTask(int pos) {
        getSnapshots().getSnapshot(pos).getReference().delete();
    }
}
