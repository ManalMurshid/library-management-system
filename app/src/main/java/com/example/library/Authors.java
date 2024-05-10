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

public class Authors extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private TextView welcome_text ;
    private EditText bookidEditText;
    private EditText authorNameEditText;
    private Button createButton;
    private Button readButton;
    private Button deleteButton;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        // initializing all our variables.
        bookidEditText = findViewById(R.id.book_id_edit_text);
        authorNameEditText = findViewById(R.id.author_name_edit_text);
        createButton = findViewById(R.id.create_button);
        readButton = findViewById(R.id.read_button);
        deleteButton = findViewById(R.id.delete_button);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(Authors.this);

        // below line is to add on click listener for our add course button.
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String AuthorName = authorNameEditText.getText().toString();
                int BookID = 0;  // Initialize with default value
                try {
                    BookID= Integer.parseInt(bookidEditText.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle invalid phone number input (optional)
                    Toast.makeText(Authors.this, "Invalid Book ID  format!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // validating if the text fields are empty or not.
                if (BookID==0 && AuthorName.isEmpty()) {
                    Toast.makeText(Authors.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewAuthor(AuthorName,BookID);

                // after adding the data we are displaying a toast message.
                Toast.makeText(Authors.this, "Author has been added.", Toast.LENGTH_SHORT).show();
                bookidEditText.setText("");
                authorNameEditText.setText("");
            }
        });

        // Add click listener for delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get book title from edit text (assuming title is used for deletion)
                int  BookID = Integer.parseInt(bookidEditText.getText().toString());
                String AuthorName = authorNameEditText.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(Authors.this);
                builder.setMessage("Are you sure you want to delete this Author?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call deleteBook method if confirmed
                        dbHandler.deleteAuthor(AuthorName,BookID);
                        Toast.makeText(Authors.this, "Author deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }
        });


        // Click listener for View Available Books button
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllAuthors();
            }
        });
    }

    // Method to show all books in a scrollable dialog
    private void getAllAuthors() {
        // Get all book data from the database
        String bookData = dbHandler.getAllAuthors();

        if (bookData.isEmpty()) {
            // Handle no books case (optional)
            Toast.makeText(Authors.this, "No Authors found in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Authors.this);
        builder.setTitle("Available Authors");

        // Set a TextView with all book data and vertical scroll bar
        TextView AuthorListTextView = new TextView(Authors.this);
        AuthorListTextView.setText(bookData);
        AuthorListTextView.setVerticalScrollBarEnabled(true);
        builder.setView(AuthorListTextView);

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
