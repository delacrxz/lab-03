package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    // setting city to be passed as an argument
    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // set interface for listener and instantiate one
    interface EditCityDialogListener {
        void editCity(City city, String newName, String newProvince);
    }
    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // set so listener provides reusability of fragment
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    // customizes dialog and binds views
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // create and inflate view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);

        // link EditText elements
        EditText editCityName = view.findViewById(R.id.edit_text_city_info);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_info);

        // get fragment arguments
        City passedCity = (City) this.getArguments().getSerializable("city");

        // set EditText text to city info
        editCityName.setText(passedCity.getName());
        editProvinceName.setText(passedCity.getProvince());

        // construct and customize builder for alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog, which) -> {

                    // get EditText text input
                    String cityInput = editCityName.getText().toString();
                    String provinceInput = editProvinceName.getText().toString();

                    // set new info
                    listener.editCity(passedCity, cityInput, provinceInput);
                })
                .create();
    }
}
