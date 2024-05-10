package com.example.library;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Memberships extends AppCompatActivity {
    private EditText cardNoInput;
    private EditText nameInput;
    private EditText addressInput;
    private EditText phoneInput;
    private EditText unpaidDuesInput;
    private Button addMemberButton;
    private Button removeMemberButton;
    private Button viewMembersButton;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberships);

        cardNoInput = findViewById(R.id.card_no_input);
        nameInput = findViewById(R.id.name_input);
        addressInput = findViewById(R.id.address_input);
        phoneInput = findViewById(R.id.phone_input);
        unpaidDuesInput = findViewById(R.id.unpaid_dues_input);
        addMemberButton = findViewById(R.id.add_member_button);
        removeMemberButton = findViewById(R.id.remove_member_button);
        viewMembersButton = findViewById(R.id.view_members_button);

        dbHandler = new DBHandler(Memberships.this);

        removeMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deleteMember(); }
        });

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMember();
            }
        });

        viewMembersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllMember();
            }
        });
    }

    private void addNewMember() {
        String CardNo = cardNoInput.getText().toString();
        String MemberName = nameInput.getText().toString();
        String MemberAddress = addressInput.getText().toString();
        String MemberPhone = phoneInput.getText().toString();
        String MemberUnpaidDues = unpaidDuesInput.getText().toString();


        if (CardNo.isEmpty() && MemberName .isEmpty() && MemberAddress.isEmpty() && MemberPhone.isEmpty() && MemberUnpaidDues.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHandler.addNewMember( CardNo, MemberName, MemberAddress, MemberPhone, MemberUnpaidDues);
        if (result != -1) {
            Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add Member", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMember(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Memberships.this);
        builder.setMessage("Are you sure you want to delete this book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String CardNo = cardNoInput.getText().toString();
                // calling a method to delete our course.
                dbHandler.deleteMember(CardNo);
                Toast.makeText(Memberships.this, "Member deleted successfully!", Toast.LENGTH_SHORT).show();
                cardNoInput.setText("");
                nameInput.setText("");
                addressInput.setText("");
                phoneInput.setText("");
                unpaidDuesInput.setText("");
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    private void getAllMember() {
        // Get all book data from the database
        String MembersData = dbHandler.getAllMember();

        if (MembersData.isEmpty()) {
            // Handle no books case (optional)
            Toast.makeText(Memberships.this, "No Members found in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Memberships.this);
        builder.setTitle("Available Members");

        // Set a TextView with all book data and vertical scroll bar
        TextView MembersListTextView = new TextView(Memberships.this);
        MembersListTextView.setText(MembersData);
        MembersListTextView.setVerticalScrollBarEnabled(true);
        builder.setView(MembersListTextView);

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