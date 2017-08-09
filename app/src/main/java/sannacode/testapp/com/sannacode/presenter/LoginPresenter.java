package sannacode.testapp.com.sannacode.presenter;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import sannacode.testapp.com.sannacode.model.ILoginModel;
import sannacode.testapp.com.sannacode.model.LoginModel;
import sannacode.testapp.com.sannacode.view.ILoginView;
import sannacode.testapp.com.sannacode.view.LoginView;

public class LoginPresenter implements ILoginPresenter {

  private GoogleApiClient mGoogleApiClient;
  private ILoginModel loginModel = new LoginModel();
  private ILoginView loginView = new LoginView();


  @Override
  public Intent onClickBtn(FragmentActivity fragment, Context context,
      OnConnectionFailedListener error) {
    mGoogleApiClient = loginModel.connectionGoogle(fragment, context, error);
    return Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
  }

  @Override
  public String isAccountSelected( GoogleSignInResult result) {
    return loginModel.emailAccount();
  }

  @Override
  public GoogleSignInResult checkLogin(int requestCode, Intent data) {
    return loginModel.isLogin(requestCode, data);
  }
}
