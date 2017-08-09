package sannacode.testapp.com.sannacode.view;


import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import sannacode.testapp.com.sannacode.Constant;
import sannacode.testapp.com.sannacode.Contact;
import sannacode.testapp.com.sannacode.CustomListAdapter;
import sannacode.testapp.com.sannacode.FragmentAddContact;
import sannacode.testapp.com.sannacode.FragmentAddContact.AddContactToDB;
import sannacode.testapp.com.sannacode.R;
import sannacode.testapp.com.sannacode.model.MainModel;
import sannacode.testapp.com.sannacode.presenter.MainPresenter;

public class MainView extends AppCompatActivity implements AddContactToDB, IMainView {

  private MainPresenter mainPresenter;
  private String nameDBTable;
  private Integer imageId = R.drawable.icon;
  public static ArrayList<String> contactID = new ArrayList<>();
  public static List<Contact> contacts = new ArrayList<>();
  private CustomListAdapter adapter;
  private ListView listView;
  private MainModel mainModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mainPresenter = new MainPresenter();

    final Intent intent = getIntent();
    ActionBar actionBar = getDelegate().getSupportActionBar();
    if (actionBar != null) {
      nameDBTable = intent.getStringExtra("name").replaceAll("[^A-Za-zА-Яа-я0-9]", "");
      Constant.NAME_DB = nameDBTable;

      actionBar.setTitle(nameDBTable);
    }

    mainPresenter
        .connect(nameDBTable, getApplicationContext(), contacts, contactID, null, mainPresenter);
    mainModel = new MainModel();
    adapter = new
        CustomListAdapter(MainView.this, (ArrayList<Contact>) contacts, imageId, contactID);
    listView = (ListView) findViewById(R.id.contacts);
    listView.setAdapter(adapter);
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
          final long id) {

        Builder builder = new AlertDialog.Builder(MainView.this);
        builder.setTitle(contacts.get(position).getName());
        builder.setItems(getResources().getStringArray(R.array.type),
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                  Bundle bundle = new Bundle();
                  bundle.putString("status", "edit");
                  bundle.putInt("id", Integer.parseInt(MainView.contactID.get(position)));
                  Contact contact = mainModel
                      .getContact(Integer.parseInt(MainView.contactID.get(position)));
                  bundle.putString("name", contact.getName());
                  bundle.putString("phone", contact.getNumber());
                  bundle.putString("email", contact.getEmail());
                  showDialogAddContact(bundle);
                }
                if (item == 1) {
                  openCallIntent(position);
                }
                if (item == 2) {
                  openEmailSendIntent(position);
                }
                if (item == 3) {
                  mainModel.removeContact(Integer.parseInt(MainView.contactID.get(position)));
                  adapter.notifyDataSetChanged();
                }
              }
            });
        AlertDialog alert = builder.create();
        alert.show();
        return false;
      }
    });
  }

  @Override
  public void updateContactList(String orderBy,
      MainPresenter mainPresenter) {
    mainPresenter.showAllContact(orderBy);
  }

  @Override
  public void upSorted() {
    mainPresenter.showAllContact("name asc");
  }

  @Override
  public void downSorted() {
    mainPresenter.showAllContact("name desc");
  }

  @Override
  public void showDialogAddContact(Bundle bundle) {
    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
        .beginTransaction();
    android.support.v4.app.Fragment prev = getSupportFragmentManager()
        .findFragmentByTag("addContactFragment");
    if (prev != null) {
      ft.remove(prev);
    }
    ft.addToBackStack(null);

    DialogFragment addContactFragment = new FragmentAddContact();
    addContactFragment.setArguments(bundle);
    addContactFragment.show(ft, "addContactFragment");
  }

  @Override
  public void openCallIntent(int position) {
    Contact contact = mainModel.getContact(Integer.parseInt(contactID.get(position)));
    int number = Integer.parseInt(contact.getNumber());
    Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
    startActivity(intentCall);
  }

  @Override
  public void openEmailSendIntent(int position) {
    Contact contact = mainModel.getContact(Integer.parseInt(contactID.get(position)));
    String email = contact.getEmail();
    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", email, null));
    startActivity(Intent.createChooser(emailIntent, "Send email..."));
  }

  @Override
  public void onDialogClickBtn(String name, String phone, String email) {
    mainPresenter.addContact(name, phone, email);
  }

  @Override
  public void editContactInDB(String name, String phone, String email, int idContact) {
    mainModel.editContact(name, phone, email, idContact);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void updateList() {
    mainPresenter.showAllContact(null);
    adapter.notifyDataSetChanged();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    String orderBy;
    switch (item.getItemId()) {
      case R.id.addContact:
        Bundle bundle = new Bundle();
        bundle.putString("status", "add");
        showDialogAddContact(bundle);
        break;
      case R.id.upPoint:
        upSorted();
        adapter.notifyDataSetChanged();
        break;
      case R.id.downPoint:
        downSorted();
        adapter.notifyDataSetChanged();
        break;
    }
    return true;
  }

}
