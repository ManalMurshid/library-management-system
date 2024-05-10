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

public class Book_copy_Details extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private TextView welcome_text ;
    private EditText bookIdEditText;
    private EditText BranchIdEditText;
    private EditText AccessNoEditText;
    private Button createButton;
    private Button readButton;
    private Button deleteButton;
    private DBHandler dbHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy_details);

        // initializing all our variables.
        bookIdEditText = findViewById(R.id.book_id_edit_text);
        BranchIdEditText = findViewById(R.id.Branch_id_edit_text);
        AccessNoEditText = findViewById(R.id.AccessNo_edit_text);
        createButton = findViewById(R.id.create_button);
        readButton = findViewById(R.id.read_button);  // Button text changed to "View" as requested
        deleteButton = findViewById(R.id.delete_button);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(Book_copy_Details.this);

        // below line is to add on click listener for our add course button.
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int BookID = 0;  // Initialize with default value in case parsing fails
                try {
                    BookID = Integer.parseInt(bookIdEditText.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle invalid card number input (optional)
                    Toast.makeText(Book_copy_Details.this, "Invalid  format!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int BranchID = 0;  // Initialize with default value in case parsing fails
                try {
                    BranchID = Integer.parseInt(BranchIdEditText.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle invalid card number input (optional)
                    Toast.makeText(Book_copy_Details.this, "Invalid  format!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int AccessNo= 0;  // Initialize with default value in case parsing fails
                try {
                    AccessNo = Integer.parseInt(AccessNoEditText.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle invalid card number input (optional)
                    Toast.makeText(Book_copy_Details.this, "Invalid format!", Toast.LENGTH_SHORT).show();
                    return;
                }



                // validating if the text fields are empty or not.
                if (AccessNo==0 && BranchID==0 && BookID==0) {
                    Toast.makeText(Book_copy_Details.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewBookCopy( BookID,BranchID, AccessNo);

                // after adding the data we are displaying a toast message.
                Toast.makeText(Book_copy_Details.this, "Book Copy has been added.", Toast.LENGTH_SHORT).show();
                bookIdEditText.setText("");
                BranchIdEditText.setText("");
                AccessNoEditText.setText("");
            }
        });

        // Add click listener for delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get book title from edit text (assuming title is used for deletion)
                int AccessNo = Integer.parseInt(AccessNoEditText.getText().toString());

                // Confirmation dialog before deletion (optional but recommended)
                // You can use AlertDialog builder for this
                // Here's a basic example:

                AlertDialog.Builder builder = new AlertDialog.Builder(Book_copy_Details.this);
                builder.setMessage("Are you sure you want to delete this book?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call deleteBook method if confirmed
                        dbHandler.deleteBookCopy(AccessNo);
                        Toast.makeText(Book_copy_Details.this, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
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
                getAllBookCopy();
            }
        });
    }

    // Method to show all books in a scrollable dialog
    private void getAllBookCopy() {
        // Get all book data from the database
        String bookData = dbHandler.getAllBookCopy();

        if (bookData.isEmpty()) {
            // Handle no books case (optional)
            Toast.makeText(Book_copy_Details.this, "No books found in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Book_copy_Details.this);
        builder.setTitle("Available Books");

        // Set a TextView with all book data and vertical scroll bar
        TextView bookListTextView = new TextView(Book_copy_Details.this);
        bookListTextView.setText(bookData);
        bookListTextView.setVerticalScrollBarEnabled(true);
        builder.setView(bookListTextView);

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