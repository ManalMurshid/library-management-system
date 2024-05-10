package com.example.library;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "Librarydb";

    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    //BOOKS TABLE
    private static final String BOOK_TABLE = "book";
    private static final String MEMBER_TABLE = "member";
    private static final String PUBLISHER_TABLE = "publisher";
    private static final String BRANCH_TABLE = "branch";
    private static final String AUTHOR_TABLE = "author";
    private static final String BOOKCOPY_TABLE = "bookcopy";
    private static final String BOOKLOAN_TABLE = "bookloan";

    //BOOK TABLE
    private static final String BookID_COL = "book_id";
    private static final String Title_COL = "book_title";
    private static final String PublisherName_COL = "publisher_name";

    //MEMBER TABLE
    private static final String CardNo_COL = "Card_No";
    private static final String MemberName_COL = "Member_Name";
    private static final String MemberAddress_COL = "Member_Address";
    private static final String MemberPhone_COL = "Member_Phone";
    private static final String MemberUnpaidDues_COL = "Member_UnpaidDues";
    
    //PUBLISHER TABLE
    private static final String Publisher_Name_COL = "publisher_name";
    private static final String PublisherAddress_COL = "publisher_address";
    private static final String Publisherphone_COL = "publisher_phone";
    
    //BRANCH TABLE
    private static final String BranchID_COL = "branch_id";
    private static final String BranchName_COL = "branch_name";
    private static final String BranchAddress_COL = "branch_address";

    //AUTHOR TABLE
    private static final String Book_ID_COL = "Book_id";
    private static final String AuthorName_COL = "Author_Name";

    //BOOK COPY TABLE
    private static final String BooksID_COL = "Book_ID";
    private static final String BranchId_COL = "Branch_ID";
    private static final String AccessNo_COL = "Access_No";

    //BOOK LOAN TABLE
    private static final String Access_No_COL = "Access_No";
    private static final String Branch_ID_COL = "Branch_ID";
    private static final String Card_No_COL = "Card_No";
    private static final String Date_out_COL = "Date_Out Date";
    private static final String Date_Due_COL = "Date_Due Date";
    private static final String DateReturned_COL = "Date_Returned Date";



    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + MEMBER_TABLE + " ("
                + CardNo_COL + " INTEGER PRIMARY KEY, "
                + MemberName_COL + " TEXT,"
                + MemberAddress_COL + " TEXT,"
                + MemberPhone_COL + " INTEGER,"
                + MemberUnpaidDues_COL+ " INTEGER)";

        String query4 = "CREATE TABLE " + BOOK_TABLE + " ("
                + BookID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Title_COL + " TEXT,"
                + PublisherName_COL + " TEXT, "  // Add a comma after PublisherName_COL
                + "FOREIGN KEY (" + PublisherName_COL + ") REFERENCES " + PUBLISHER_TABLE + "(" + Publisher_Name_COL + ")"
                + ")";
        
        String query2 = "CREATE TABLE " + PUBLISHER_TABLE + " ("
                + Publisher_Name_COL + " TEXT PRIMARY KEY, "
                + PublisherAddress_COL + " TEXT,"
                + Publisherphone_COL + " INTEGER)";
        
        String query3 = "CREATE TABLE " + BRANCH_TABLE + " ("
                + BranchID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BranchName_COL + " TEXT,"
                + BranchAddress_COL+ " TEXT)";

        String query5 = "CREATE TABLE " + AUTHOR_TABLE + " ("
                + Book_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +AuthorName_COL+ " TEXT PRIMARY KEY,"
                + "FOREIGN KEY (" + Book_ID_COL + ") REFERENCES " + BOOK_TABLE + "(" + BookID_COL + ")"
                + ")";

        String query6 = "CREATE TABLE " + BOOKCOPY_TABLE + " ("
                + AccessNo_COL + " INTEGER PRIMARY KEY, "
                + BranchId_COL+ " INTEGER PRIMARY KEY,"
                + BooksID_COL + " INTEGER,"
                + "FOREIGN KEY (" + BooksID_COL + ") REFERENCES " + BOOK_TABLE + "(" + BookID_COL + "),"
                + "FOREIGN KEY (" + BranchId_COL + ") REFERENCES " + BRANCH_TABLE + "(" + BranchID_COL + ")"
                + ")";

        String query7 = "CREATE TABLE " + BOOKLOAN_TABLE + " ("
                + Access_No_COL  + " INTEGER PRIMARY KEY, "
                + Branch_ID_COL + " INTEGER PRIMARY KEY,"
                + Card_No_COL  + " INTEGER PRIMARY KEY,"
                + Date_out_COL  + " ,INTEGER PRIMARY KEY,"
                + DateReturned_COL + " , TEXT ,"
                + Date_Due_COL + " , INTEGER , "
                + "FOREIGN KEY (" + Card_No_COL + ") REFERENCES " + MEMBER_TABLE + "(" + CardNo_COL + "),"
                + "FOREIGN KEY (" + Branch_ID_COL + ") REFERENCES " + BRANCH_TABLE + "(" + BranchID_COL + "),"
                + "FOREIGN KEY (" + Branch_ID_COL + Access_No_COL + ") REFERENCES " + BOOKCOPY_TABLE + "(" + BranchId_COL + "," + AccessNo_COL + ")"
                + ")";

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE);
        onCreate(db);
    }

    //BOOKS FUNCTIONS
    // this method is use to add new course to our sqlite database.
    public long addNewBook( String BookTitle, String PublisherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Title_COL, BookTitle);
        values.put(PublisherName_COL, PublisherName);

        db.insert(BOOK_TABLE, null, values);
        db.close();
        return 0;
    }
    public void deleteBook(String BookTitle) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(BOOK_TABLE, "book_title=?", new String[]{BookTitle});
        db.close();
    }

    public String getAllBooks() {
        String booksData = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all data from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOK_TABLE, null);

        // Loop through cursor and build the book data string
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(BookID_COL);
                int titleIndex = cursor.getColumnIndex(Title_COL);
                int publisherIndex = cursor.getColumnIndex(PublisherName_COL);

                // Check if column indexes are valid before accessing data
                if (idIndex >= 0 &&  titleIndex >= 0 && publisherIndex >= 0) {
                    String id = cursor.getString(idIndex);
                    String title = cursor.getString(titleIndex);
                    String publisher = cursor.getString(publisherIndex);

                    // You can add more columns as needed
                    booksData += "Book ID: " + id + "  \nTitle: " + title + " \nPublisher: " + publisher + "\n\n";
                } else {
                    Log.e("DBHandler", "Error: Could not find columns Title or PublisherName");
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return booksData;
    }



    //MEMBERS FUNCTIONS

    public long addNewMember(String CardNo, String PublisherName, String PublisherAddress, String PublisherPhone, String MemberUnpaidDues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CardNo_COL,CardNo );
        values.put(MemberName_COL,PublisherName);
        values.put(MemberAddress_COL,PublisherAddress);
        values.put(MemberPhone_COL,PublisherPhone);
        values.put(MemberUnpaidDues_COL,MemberUnpaidDues);

        db.insert(MEMBER_TABLE, null, values);

        db.close();
        return 0;
    }

    public void deleteMember(String CardNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEMBER_TABLE, "Card_No=?", new String[]{String.valueOf(CardNo)});
        db.close();
    }

    public String getAllMember() {
        String membersData = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all data from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + MEMBER_TABLE, null);

        // Loop through cursor and build the book data string
        if (cursor.moveToFirst()) {
            do {
                int CardNoIndex = cursor.getColumnIndex(CardNo_COL );
                int PublisherNameIndex = cursor.getColumnIndex(MemberName_COL);
                int PublisherAddressIndex = cursor.getColumnIndex(MemberAddress_COL);
                int PublisherPhoneIndex = cursor.getColumnIndex(MemberPhone_COL);
                int UnpaidDuesIndex = cursor.getColumnIndex(MemberUnpaidDues_COL);

                // Check if column indexes are valid before accessing data
                if (CardNoIndex>= 0 &&  PublisherNameIndex >= 0 && PublisherAddressIndex >= 0 && PublisherPhoneIndex >= 0 && UnpaidDuesIndex >= 0) {
                    String CardNo = cursor.getString(CardNoIndex);
                    String PublisherName = cursor.getString(PublisherNameIndex);
                    String PublisherAddress = cursor.getString(PublisherAddressIndex);
                    String PublisherPhone = cursor.getString( PublisherPhoneIndex);
                    String UnpaidDues = cursor.getString(UnpaidDuesIndex);

                    // You can add more columns as needed
                    membersData+= "  CardNo: " + CardNo+ "  \nMember Name : " + PublisherName + " \nMember Address: " + PublisherAddress + " \nMember Phone: " + PublisherPhone + " \nUnpaidDues: " + UnpaidDues + "\n\n";
                } else {
                    Log.e("DBHandler", "Error: Could not find columns Title or PublisherName");
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return membersData;
    }

    //PUBLISHER TABLE FUNCTIONS
    public void addNewPublisher(String PublisherName, String PublisherAddress, String PublisherPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PublisherName_COL,PublisherName );
        values.put(PublisherAddress_COL,PublisherAddress);
        values.put(Publisherphone_COL,PublisherPhone);

        db.insert(PUBLISHER_TABLE, null, values);

        db.close();
    }

    public void deletePublisher(String PublisherName) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PUBLISHER_TABLE, "publisher_name=?", new String[]{String.valueOf(PublisherName)});
        db.close();
    }

    public String getAllPublisher() {

        String PublishersData = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all data from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + PUBLISHER_TABLE, null);

        // Loop through cursor and build the book data string
        if (cursor.moveToFirst()) {
            do {
                int PublisherNameIndex = cursor.getColumnIndex(Publisher_Name_COL);
                int PublisherAddressIndex = cursor.getColumnIndex(PublisherAddress_COL);
                int PublisherPhoneIndex = cursor.getColumnIndex(Publisherphone_COL);

                // Check if column indexes are valid before accessing data
                if ( PublisherNameIndex >= 0 && PublisherAddressIndex >= 0 && PublisherPhoneIndex >= 0) {
                    String PublisherName = cursor.getString(PublisherNameIndex);
                    String PublisherAddress = cursor.getString(PublisherAddressIndex);
                    String PublisherPhone = cursor.getString( PublisherPhoneIndex);
                    // You can add more columns as needed
                    PublishersData+= "  \n publisher_name : " + PublisherName + " \npublisher_address: " + PublisherAddress + " \npublisher_phone: " + PublisherPhone + "\n\n";
                } else {
                    Log.e("DBHandler", "Error: Could not find columns Title or PublisherName");
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return PublishersData;

    }

    //BRANCH TABLE
    public void addNewBranch(String BranchName, String BranchAddress, String BranchID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BranchID_COL,BranchID );
        values.put(BranchName_COL,BranchName);
        values.put(BranchAddress_COL,BranchAddress);

        db.insert(BRANCH_TABLE, null, values);

        db.close();
    }

    public void deleteBranch(String BranchName) {

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + BRANCH_TABLE + " WHERE " + BranchName_COL + " = ?";
        String[] whereArgs = {BranchName};
        db.delete(BRANCH_TABLE, deleteQuery, whereArgs);
        db.close();
    }

    public String getAllBranches() {
        String BranchData = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all data from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + BRANCH_TABLE, null);

        // Loop through cursor and build the book data string
        if (cursor.moveToFirst()) {
            do {
                int IDIndex = cursor.getColumnIndex(BookID_COL);
                int BranchNameIndex = cursor.getColumnIndex(BranchName_COL);
                int BranchAddressIndex = cursor.getColumnIndex(BranchAddress_COL);

                // Check if column indexes are valid before accessing data
                if (IDIndex >= 0 &&  BranchNameIndex >= 0 && BranchAddressIndex >= 0) {
                    String ID= cursor.getString(IDIndex);
                    String Name = cursor.getString(BranchNameIndex);
                    String Address = cursor.getString(BranchAddressIndex);

                    // You can add more columns as needed
                    BranchData += "  Branch ID: " + Name + "  \nBranch Name: " + Name + " \nBranch Address: " + Address + "\n\n";
                } else {
                    Log.e("DBHandler", "Error: Could not find columns ");
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return BranchData;
    }

    public void addNewAuthor(String AuthorName, int BookID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Book_ID_COL,BookID );
        values.put(AuthorName_COL,BookID);


        db.insert(AUTHOR_TABLE, null, values);

        db.close();
    }

    public void deleteAuthor(String AuthorName,int BookID) {

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + AUTHOR_TABLE + " WHERE " + AuthorName_COL + "=? " + Book_ID_COL + " = ?";
        String[] whereArgs = {String.valueOf(BookID)};
        db.delete(AUTHOR_TABLE, deleteQuery, whereArgs);
        db.close();
    }

    public String getAllAuthors() {
        String AuthorData = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all data from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + AUTHOR_TABLE, null);

        // Loop through cursor and build the book data string
        if (cursor.moveToFirst()) {
            do {
                int IDIndex = cursor.getColumnIndex(Book_ID_COL);
                int AuthorNameIndex = cursor.getColumnIndex(AuthorName_COL);

                // Check if column indexes are valid before accessing data
                if (IDIndex >= 0 &&  AuthorNameIndex >= 0) {
                    String ID= cursor.getString(IDIndex);
                    String Name = cursor.getString(AuthorNameIndex);

                    // You can add more columns as needed
                    AuthorData += "  Branch ID: " + ID + "  \nAuthor Name: " + Name + "\n\n";
                } else {
                    Log.e("DBHandler", "Error: Could not find columns ");
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return AuthorData;
    }

    public void addNewBookCopy(int BookID, int BranchID, int AccessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BooksID_COL ,BookID );
        values.put(BranchId_COL ,BranchID);
        values.put(AccessNo_COL  ,AccessNo);


        db.insert(BOOKCOPY_TABLE, null, values);

        db.close();
    }

    public void deleteBookCopy(int AccessNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + BOOKCOPY_TABLE + " WHERE " + AccessNo_COL+ " = ?";
        String[] whereArgs1 = {String.valueOf(AccessNo)};
        db.delete(BOOKCOPY_TABLE, deleteQuery, whereArgs1);
        db.close();
    }

    public String getAllBookCopy() {
        String BookCopyData = "";
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all data from the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOKCOPY_TABLE, null);

        // Loop through cursor and build the book data string
        if (cursor.moveToFirst()) {
            do {

                int IDIndex = cursor.getColumnIndex(BooksID_COL);
                int BranchIDIndex = cursor.getColumnIndex(BranchId_COL);
                int AccessNoIndex = cursor.getColumnIndex(AccessNo_COL );

                // Check if column indexes are valid before accessing data
                if (IDIndex >= 0 &&  BranchIDIndex >= 0 &&  AccessNoIndex >= 0) {
                    int ID= Integer.parseInt(cursor.getString(IDIndex));
                    int BranchID= Integer.parseInt(cursor.getString(IDIndex));
                    int AccessNo = Integer.parseInt(cursor.getString(AccessNoIndex));

                    // You can add more columns as needed
                    BookCopyData += "  Book ID: " + ID + "  \nBranch ID: " + BranchID +"  \nAccess No.: " +AccessNo + "\n\n";
                } else {
                    Log.e("DBHandler", "Error: Could not find columns ");
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return BookCopyData;
    }
    
    


}
