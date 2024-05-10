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

public class Publishers extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private EditText nameEditText;
    private EditText AddressEditText;
    private EditText PhoneEditText;
    private Button createButton;

    private Button deleteButton;
    private Button readButton;
    private DBHandler dbHandler;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishers);

        // initializing all our variables.
        nameEditText = findViewById(R.id.name_edit_text);
        AddressEditText = findViewById(R.id.address_edit_text);
        PhoneEditText = findViewById(R.id.Phone_edit_text);
        createButton = findViewById(R.id.create_button);
        readButton = findViewById(R.id.read_button);
        deleteButton = findViewById(R.id.delete_button);

        dbHandler = new DBHandler(Publishers.this);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPublisher();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deletePublisher(); }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getAllPublisher(); }
        });
    }

    private void addNewPublisher() {
        String PublisherName = nameEditText.getText().toString();
        String PublisherAddress = AddressEditText.getText().toString();
        String PublisherPhone = PhoneEditText.getText().toString();

        dbHandler.addNewPublisher(PublisherName, PublisherAddress, PublisherPhone);

        // after adding the data we are displaying a toast message.
        Toast.makeText(Publishers.this, "Book has been added.", Toast.LENGTH_SHORT).show();
        nameEditText.setText("");
        AddressEditText.setText("");
        PhoneEditText.setText("");
    }


    private void  deletePublisher(){

        String PublisherName = nameEditText.getText().toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(Publishers.this);
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call deleteBook method if confirmed
                dbHandler.deletePublisher(PublisherName);
                Toast.makeText(Publishers.this, "Publisher deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    private void getAllPublisher() {
        String PublishersData = dbHandler.getAllPublisher();

        if (PublishersData.isEmpty()) {
            // Handle no books case (optional)
            Toast.makeText(Publishers.this, "No Publishers found in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Publishers.this);
        builder.setTitle("Available Publishers");

        // Set a TextView with all book data and vertical scroll bar
        TextView publisherTextView = new TextView(Publishers.this);
        publisherTextView.setText(PublishersData);
        publisherTextView.setVerticalScrollBarEnabled(true);
        builder.setView(publisherTextView);

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