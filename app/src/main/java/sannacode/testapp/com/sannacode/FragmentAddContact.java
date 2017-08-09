package sannacode.testapp.com.sannacode;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

public class FragmentAddContact extends DialogFragment implements OnClickListener {

  private EditText name;
  private EditText phone;
  private EditText email;
  private AddContactToDB addContactToDB;
  private String status;
  private int id;

  private Button btnAdd;
  private Button btnEdit;


  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    addContactToDB = (AddContactToDB) activity;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    getDialog().getWindow().setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    View v = inflater.inflate(R.layout.fragment_add_contact, container, true);

    btnAdd = (Button) v.findViewById(R.id.btn_add);
    btnAdd.setOnClickListener(this);
    btnAdd.setVisibility(View.VISIBLE);
    btnAdd.setEnabled(false);

    btnEdit = (Button) v.findViewById(R.id.btn_edit);
    btnEdit.setOnClickListener(this);
    btnEdit.setVisibility(View.GONE);
    btnEdit.setEnabled(false);

    name = (EditText) v.findViewById(R.id.first_lat_name);
    phone = (EditText) v.findViewById(R.id.phone);
    email = (EditText) v.findViewById(R.id.email);

    Observable<String> emailObservable = RxEditText.getTextWatcherObserv(email);
    Observable<String> phoneObservable = RxEditText.getTextWatcherObserv(phone);
    Observable
        .combineLatest(emailObservable, phoneObservable, new Func2<String, String, Boolean>() {
          @Override
          public Boolean call(String s, String s2) {
            if (s.isEmpty() || s2.isEmpty() || !s.contains("@")) {
              return false;
            } else {
              return true;
            }
          }

        }).subscribe(new Action1<Boolean>() {
      @Override
      public void call(Boolean aBoolean) {
        if (aBoolean) {
          btnAdd.setEnabled(true);
          btnEdit.setEnabled(true);
        } else {
          btnAdd.setEnabled(false);
          btnEdit.setEnabled(false);
        }

      }
    });

    Bundle bundle = this.getArguments();
    if (bundle != null) {
      status = bundle.getString("status");
      assert status != null;
      if (status.equals("edit")) {
        id = bundle.getInt("id", 0);
        name.setText(bundle.getString("name"));
        phone.setText(bundle.getString("phone"));
        email.setText(bundle.getString("email"));

        btnAdd.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);
      }
    }

    return v;
  }


  @Override
  public void onClick(View v) {

    switch (v.getId()) {

      case R.id.btn_add:

        addContactToDB.onDialogClickBtn(
            name.getText().toString(),
            phone.getText().toString(),
            email.getText().toString());

        addContactToDB.updateList();
        break;
      case R.id.btn_edit:
        addContactToDB.editContactInDB(
            name.getText().toString(),
            phone.getText().toString(),
            email.getText().toString(),
            id);
        addContactToDB.updateList();
        break;
    }
    this.dismiss();
  }


  public interface AddContactToDB {

    void onDialogClickBtn(String name, String phone, String email);

    void editContactInDB(String name, String phone, String email, int idContact);

    void updateList();
  }

}
