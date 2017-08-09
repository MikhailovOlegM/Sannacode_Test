package sannacode.testapp.com.sannacode.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public interface ILoginPresenter {

  Intent onClickBtn(FragmentActivity fragment, Context context,
      OnConnectionFailedListener error);

  String isAccountSelected(GoogleSignInResult result);


  GoogleSignInResult checkLogin(int requestCode, Intent data);
}
