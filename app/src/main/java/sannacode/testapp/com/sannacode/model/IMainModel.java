package sannacode.testapp.com.sannacode.model;

import android.content.Context;
import sannacode.testapp.com.sannacode.Contact;

public interface IMainModel {

  void connectToDB(String nameDBTable, Context context);

  void addContact(String name, String phone, String email);

  Contact getContact(int id);

  void showAllContact(String orderBy);

  void removeContact(int id);

  void editContact(String name, String phone, String email, int id);

  interface DBHelper {

    void connect();

  }
}
