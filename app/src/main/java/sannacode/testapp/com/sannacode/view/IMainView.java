package sannacode.testapp.com.sannacode.view;


import android.os.Bundle;
import sannacode.testapp.com.sannacode.presenter.MainPresenter;

public interface IMainView {

  void updateContactList(String orderBy,
      MainPresenter mainPresenter);

  void upSorted();

  void downSorted();

  void showDialogAddContact(Bundle bundle);

  void openCallIntent(int position);

  void openEmailSendIntent(int position);


}
