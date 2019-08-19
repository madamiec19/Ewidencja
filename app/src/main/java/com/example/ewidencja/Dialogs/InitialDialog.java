package com.example.ewidencja.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ewidencja.Globals;
import com.example.ewidencja.R;

public class InitialDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private String selectedDriver;

    private InitialDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_initial_dialog, null);

        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Globals g = Globals.getInstance();
                        g.setDriver(selectedDriver);
                        listener.applyDriver(selectedDriver);
                    }
                })
        .setTitle("Wybierz kierowcę: ");
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.users, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedDriver = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (InitialDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement MiddlepointDialogListener");
        }
    }

    public interface InitialDialogListener{
        void applyDriver(String driver);
    }
}
