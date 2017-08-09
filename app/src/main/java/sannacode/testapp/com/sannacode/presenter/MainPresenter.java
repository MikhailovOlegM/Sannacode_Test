package sannacode.testapp.com.sannacode.presenter;

import java.util.List;

import android.content.Context;
import sannacode.testapp.com.sannacode.Contact;
import sannacode.testapp.com.sannacode.model.MainModel;
import sannacode.testapp.com.sannacode.view.MainView;

public class MainPresenter implements IMainPresenter {

  MainModel mainModel = new MainModel();
  MainView mainView = new MainView();

  @Override
  public void connect(String nameDBTable, Context context, List<Contact> contacts,
      List<String> contactID, String orderBy, MainPresenter mainPresenter) {
    mainModel.connectToDB(nameDBTable, context);
    mainView.updateContactList(null, mainPresenter);
  }

  @Override
  public void showAllContact(String orderBy) {
    mainModel.showAllContact(orderBy);
  }

  @Override
  public void addContact(String name, String phone, String email) {
    mainModel.addContact(name, phone, email);
  }

}
