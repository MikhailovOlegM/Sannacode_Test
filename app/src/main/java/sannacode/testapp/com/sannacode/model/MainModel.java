package sannacode.testapp.com.sannacode.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import sannacode.testapp.com.sannacode.Constant;
import sannacode.testapp.com.sannacode.Contact;
import sannacode.testapp.com.sannacode.view.MainView;

public class MainModel implements IMainModel {

  private static DBHelper dbHelper;
  private static SQLiteDatabase db;
  final String LOG_TAG = "appTest";


  @Override
  public void connectToDB(String nameDBTable, Context context) {
    dbHelper = new DBHelper(context, nameDBTable);
    db = dbHelper.getWritableDatabase();


  }

  @Override
  public void addContact(String name, String phone, String email) {
    ContentValues cv = new ContentValues();

    cv.put("name", name);
    cv.put("phone", phone);
    cv.put("email", email);
    long rowID = db.insert(Constant.NAME_DB, null, cv);
    Log.i(LOG_TAG, "row inserted, ID = " + rowID);

  }

  @Override
  public Contact getContact(int id) {
    String query = "select * from " + Constant.NAME_DB + " where id = ?";
    String[] s = new String[]{String.valueOf(id)};
    Cursor cursor = db.rawQuery(query, s);
    Contact contact = null;
    if (cursor.moveToFirst()) {

      int nameColIndex = cursor.getColumnIndex("name");
      int phoneColIndex = cursor.getColumnIndex("phone");
      int emailColIndex = cursor.getColumnIndex("email");

      do {
        contact = new Contact(
            cursor.getString(nameColIndex),
            cursor.getString(phoneColIndex),
            cursor.getString(emailColIndex));

        Log.i(LOG_TAG,
            ", name = " + cursor.getString(nameColIndex) +
                ", phone = " + cursor.getString(phoneColIndex) +
                ", email = " + cursor.getString(emailColIndex));

      } while (cursor.moveToNext());
    } else {
      Log.d(LOG_TAG, "0 rows");
    }
    cursor.close();
    return contact;
  }

  @Override
  public void showAllContact(String orderBy) {
    Cursor cursor = db.query(Constant.NAME_DB, null, null, null, null, null, orderBy);

    if (cursor.moveToFirst()) {
      int idColIndex = cursor.getColumnIndex("id");
      int nameColIndex = cursor.getColumnIndex("name");
      int phoneColIndex = cursor.getColumnIndex("phone");
      int emailColIndex = cursor.getColumnIndex("email");

      MainView.contactID.clear();
      MainView.contacts.clear();

      do {
        MainView.contactID.add(String.valueOf(cursor.getInt(idColIndex)));
        MainView.contacts.add(new Contact(
            cursor.getString(nameColIndex),
            cursor.getString(phoneColIndex),
            cursor.getString(emailColIndex)));

        Log.i(LOG_TAG,
            "ID = " + cursor.getInt(idColIndex) +
                ", name = " + cursor.getString(nameColIndex) +
                ", phone = " + cursor.getString(phoneColIndex) +
                ", email = " + cursor.getString(emailColIndex));

      } while (cursor.moveToNext());
    } else {
      MainView.contactID.clear();
      MainView.contacts.clear();
      Log.i(LOG_TAG, "0 rows");
    }
    cursor.close();
  }

  @Override
  public void removeContact(int id) {
    db.delete(Constant.NAME_DB, "id" + " = " + id, null);
    showAllContact(null);
    Log.i(LOG_TAG, "Delete element " + id);

  }

  @Override
  public void editContact(String name, String phone, String email, int id) {
    ContentValues cv = new ContentValues();

    cv.put("name", name);
    cv.put("phone", phone);
    cv.put("email", email);
    int updCount = db.update(Constant.NAME_DB, cv, "id = ?", new String[]{String.valueOf(id)});
    Log.i(LOG_TAG, "update user, ID = " + id);
    showAllContact(null);
  }


  private class DBHelper extends SQLiteOpenHelper {

    private String DBTable;

    DBHelper(Context context, String name) {
      super(context, name, null, 1);
      DBTable = name;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table " + DBTable + " ("
          + "id integer primary key autoincrement,"
          + "name text,"
          + "phone text,"
          + "email text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
  }
}
