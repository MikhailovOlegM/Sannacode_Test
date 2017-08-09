package sannacode.testapp.com.sannacode.model;


import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public interface ILoginModel {

  GoogleApiClient connectionGoogle(FragmentActivity fragment, Context context,
      OnConnectionFailedListener error);

  GoogleSignInResult isLogin(int requestCode, Intent data);

  String emailAccount();
}
