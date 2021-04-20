package com.dgl114.improvedtodolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

//**************************************************************************************************
//AddDialogFragment.java      Author: zakacat
//
//This fragment class is a part of the controller that handles input from the user.
//It has an onCreateView() which sets the dialog which includes an edit text. It also has
//a method to handle the add button click
//**************************************************************************************************
public class AddDialogFragment extends DialogFragment {
    private EditText input;
    private String inputString;
    public static String TAG = "AddDialog";

    //**********************************************************************************************
    //onCreateView() creates a View that is an inflated layout (add_layout.xml). The edit text is
    //is then assigned. The add button is assigned. And then a listener is established with an
    //inner class. Finally the View object is returned.
    //**********************************************************************************************
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_layout,
                container, false);

        input = rootView.findViewById(R.id.type_here);

        Button addButton = rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            //*****************************************************************************************
            //When the add button is clicked, the string input from the user is converted to a string
            //and then trimmed. The edit text field is then cleared. The addButtonClick() from Main
            //Activity is then called from here with the user input as a parameter. Then the dialog
            //fragment is dismissed.
            //*****************************************************************************************
            @Override
            public void onClick(View v) {
                inputString = input.getText().toString().trim();
                input.setText("");
                ((MainActivity) Objects.requireNonNull(getActivity())).addButtonClick(inputString);
                dismiss();
            }
        });
        return rootView;
    }

}




