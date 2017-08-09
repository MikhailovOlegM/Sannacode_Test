package sannacode.testapp.com.sannacode.presenter;

import java.util.List;

import android.content.Context;
import sannacode.testapp.com.sannacode.Contact;

public interface IMainPresenter {

  void connect(String nameDBTable, Context context, List<Contact> contacts, List<String> contactID,
      String orderBy, MainPresenter mainPresenter);

  void showAllContact(String orderBy);

  void addContact(String name, String phone, String email);

}
