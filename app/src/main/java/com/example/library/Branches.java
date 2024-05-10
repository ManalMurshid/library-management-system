package com.example.library;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Branches extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private TextView WelcomeText ;
    private EditText BranchIdEditText;
    private EditText BranchNameEditText1;
    private EditText BranchNameEditText;
    private EditText BranchAddressEditText;
    private Button createButton;
    private Button readButton;
    private Button deleteButton;
    private DBHandler dbHandler;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);

        // initializing all our variables.
        BranchIdEditText = findViewById(R.id.Branch_id_edit_text);
        BranchNameEditText1= findViewById(R.id.Branch_name_edit_text1);
        BranchNameEditText = findViewById(R.id.Branch_name_edit_text);
        BranchAddressEditText = findViewById(R.id.address_edit_text);
        createButton = findViewById(R.id.create_button);
        readButton = findViewById(R.id.read_button);
        deleteButton = findViewById(R.id.delete_button);

        dbHandler = new DBHandler(Branches.this);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBranch();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deleteBranch(); }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getAllBranches(); }
        });
    }

    private void addNewBranch() {

        String BranchID = BranchIdEditText.getText().toString();
        String BranchName = BranchNameEditText.getText().toString();
        String BranchAddress = BranchAddressEditText.getText().toString();


        // validating if the text fields are empty or not.
        if (BranchID.isEmpty() && BranchName.isEmpty() && BranchAddress.isEmpty()) {
            Toast.makeText(Branches.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return;
        }

        // on below line we are calling a method to add new
        // course to sqlite data and pass all our values to it.
        dbHandler.addNewBranch(BranchName,BranchAddress,BranchID);

        // after adding the data we are displaying a toast message.
        Toast.makeText(Branches.this, "New Branch has been added.", Toast.LENGTH_SHORT).show();
        BranchIdEditText.setText("");
        BranchAddressEditText.setText("");
        BranchNameEditText.setText("");
    }


    private void deleteBranch(){

        String BranchName = BranchNameEditText1.getText().toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(Branches.this);
        builder.setMessage("Are you sure you want to delete this book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call deleteBook method if confirmed
                dbHandler.deleteBranch(BranchName);
                Toast.makeText(Branches.this, "Entry deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    private void getAllBranches() {
        // Get all book data from the database
        String bookData = dbHandler.getAllBranches();

        if (bookData.isEmpty()) {
            // Handle no books case (optional)
            Toast.makeText(Branches.this, "No branches found in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Branches.this);
        builder.setTitle("Available Branches");

        // Set a TextView with all book data and vertical scroll bar
        TextView branchListTextView = new TextView(Branches.this);
        branchListTextView.setText(bookData);
        branchListTextView.setVerticalScrollBarEnabled(true);
        builder.setView(branchListTextView);

        // Optional: Set a button to close the dialog
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }



}