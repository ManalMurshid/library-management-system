package com.example.library;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//public class Home_page extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_home_page);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    String[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.list);

        options = new String[]{
                "Memberships", "Lended Books details",
                "Books", "Book Copy details", "Publishers",
                "Authors", "Branches"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                 Handle click based on position
                switch (position) {
                    case 0:
                        Intent intent = new Intent( HomeActivity.this, Memberships.class);
                        startActivity(intent);
                        break;


                    case 1:
                        Intent intent1= new Intent(HomeActivity.this, Lended_books.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent( HomeActivity.this, Books.class);
                        startActivity(intent2);
                        break;

                    case 3:
                        Intent intent3 = new Intent(HomeActivity.this, Book_copy_Details.class);
                        startActivity(intent3);
                        break;

                    case 4:
                        Intent intent4 = new Intent(HomeActivity.this, Publishers.class);
                        startActivity(intent4);
                        break;

                    case 5:
                        Intent intent5 = new Intent(HomeActivity.this, Authors.class);
                        startActivity(intent5);
                        break;

                    case 6:
                        Intent intent6 = new Intent(HomeActivity.this, Branches.class);
                        startActivity(intent6);
                        break;



                }
            }


        });
    }
}