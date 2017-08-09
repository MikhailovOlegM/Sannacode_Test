package sannacode.testapp.com.sannacode.view;


import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import sannacode.testapp.com.sannacode.R;
import sannacode.testapp.com.sannacode.presenter.ILoginPresenter;
import sannacode.testapp.com.sannacode.presenter.LoginPresenter;

public class LoginView extends AppCompatActivity implements ILoginView, View.OnClickListener,
    GoogleApiClient.OnConnectionFailedListener {

  private ILoginPresenter presenter;
  private SignInButton signInButton;
  private static final int RC_SIGN_IN = 9001;
  GoogleSignInResult result;
  GoogleApiClient mGoogleApiClient;
  Intent signInIntent;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    presenter = new LoginPresenter();

    signInButton = (SignInButton) findViewById(R.id.sign_in_button);
    signInButton.setOnClickListener(this);
    setStyleSignInBtn(signInButton);

  }


  @Override
  public void onClick(View v) {
    signInIntent = presenter.onClickBtn(this, getApplicationContext(), this);
    showSelectionMenu(this.mGoogleApiClient);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    result = presenter.checkLogin(requestCode, data);
    openMainActivity(presenter.isAccountSelected(result));
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    showError(connectionResult.getErrorMessage());
  }

  @Override
  public void showSelectionMenu(GoogleApiClient mGoogleApiClient) {
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void setStyleSignInBtn(SignInButton signInBtn) {
    this.signInButton.setSize(SignInButton.SIZE_WIDE);
    this.signInButton.setColorScheme(SignInButton.COLOR_DARK);
  }

  @Override
  public void openMainActivity(String email) {
    if (result.isSuccess()) {
      Intent intent = new Intent(this, MainView.class);
      intent.putExtra("name", email);
      startActivity(intent);
    } else {
      showError("Repeat connection");
    }

  }

  @Override
  public void showError(String msg) {
    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    toast.show();
  }
}
