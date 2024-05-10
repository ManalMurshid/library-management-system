package com.example.library;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Books extends AppCompatActivity {
    private EditText titleEditText;
    private EditText publisherEditText;
    private Button createButton;
    private Button readButton;
    private Button deleteButton;
    private Button UpdateButton;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        titleEditText = findViewById(R.id.title_edit_text);
        publisherEditText = findViewById(R.id.publisher_edit_text);
        createButton = findViewById(R.id.create_button);
        deleteButton = findViewById(R.id.delete_button);
        readButton=findViewById(R.id.read_button);

        dbHandler = new DBHandler(Books.this);

       createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBook();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deleteBook(); }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showBooks(); }
        });
    }

    private void addNewBook() {
        String BookTitle = titleEditText.getText().toString();
        String PublisherName = publisherEditText.getText().toString();

        if (BookTitle.isEmpty() && PublisherName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHandler.addNewBook( BookTitle, PublisherName);
        if (result != -1) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add Book", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteBook(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Books.this);
        builder.setMessage("Are you sure you want to delete this book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String BookTitle = titleEditText.getText().toString();
                // calling a method to delete our course.
                dbHandler.deleteBook(BookTitle);
                Toast.makeText(Books.this, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
                titleEditText.setText("");
                publisherEditText.setText("");
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    private void showBooks() {
        // Get all book data from the database
        String bookData = dbHandler.getAllBooks();

        if (bookData.isEmpty()) {
            // Handle no books case (optional)
            Toast.makeText(Books.this, "No books found in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Books.this);
        builder.setTitle("Available Books");

        // Set a TextView with all book data and vertical scroll bar
        TextView bookListTextView = new TextView(Books.this);
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




