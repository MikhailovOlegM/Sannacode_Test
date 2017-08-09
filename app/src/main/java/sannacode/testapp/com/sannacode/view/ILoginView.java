package sannacode.testapp.com.sannacode.view;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import android.content.Context;

public interface ILoginView {

  void showSelectionMenu(GoogleApiClient mGoogleApiClient);

  void setStyleSignInBtn(SignInButton signInBtn);

  void openMainActivity(String email);

  void showError(String msg);
}
