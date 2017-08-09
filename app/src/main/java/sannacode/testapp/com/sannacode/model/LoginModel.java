package sannacode.testapp.com.sannacode.model;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class LoginModel implements ILoginModel {

  private GoogleApiClient mGoogleApiClient;
  private static final int RC_SIGN_IN = 9001;
  private GoogleSignInResult result;


  @Override
  public GoogleApiClient connectionGoogle(FragmentActivity fragment, Context context,
      OnConnectionFailedListener error) {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build();

    if (mGoogleApiClient != null &&
        mGoogleApiClient.isConnected()) {
      Auth.GoogleSignInApi.signOut(mGoogleApiClient);
      Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
    } else {
      mGoogleApiClient = new GoogleApiClient.Builder(context)
          .enableAutoManage(fragment, error)
          .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
          .build();
    }

    return mGoogleApiClient;
  }

  @Override
  public GoogleSignInResult isLogin(int requestCode, Intent data) {
    if (requestCode == RC_SIGN_IN) {
      return result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
    } else {
      return null;
    }
  }

  @Override
  public String emailAccount() {
    GoogleSignInAccount acct = result.getSignInAccount();
    String result = acct.getEmail().substring(0, acct.getEmail().indexOf("@"));
    return result;
  }
}
