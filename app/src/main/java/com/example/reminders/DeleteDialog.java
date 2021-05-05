package com.example.reminders;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DeleteDialog extends DialogFragment {
    public interface DeleteListener{
        void onDelete();
        void onCancel();
    }

    DeleteListener dl;

    public AlertDialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete").setMessage("Delete task?").setPositiveButton("Delete", (dialog, id) -> {
            dl.onDelete();
        }).setNegativeButton("Cancel", (dialog, id) -> {
            dl.onCancel();
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            dl = (DeleteListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement DeleteListener");
        }
    }
}
