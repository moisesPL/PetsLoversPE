package pe.edu.ulima.petapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.dao.User;
import pe.edu.ulima.petapp.ui.navigator.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog progressDialog = null;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


    }

    public void clickLogin(View v){
        Log.e("clickLogin", "clickeado");
        login();
    }

    public void clickToRegisterAccount(View v) {
        Intent i = new Intent(this, RegisterAccountActivity.class);
        startActivityForResult(i, REQUEST_SIGNUP);
    }
    public void login() {
        Log.d(TAG, "Login");
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verificando...");
        progressDialog.show();

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);


        validateParse();
        //goToMain();//borrarnesto +sin internet+

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        // onLoginFailed();
                    }
                }, 3000);
        }

        @Override
        public void onBackPressed() {

            moveTaskToBack(true);
        }


        public void onLoginFailed() {
            Toast.makeText(getBaseContext(), "Login incorrecto", Toast.LENGTH_LONG).show();

            _loginButton.setEnabled(true);
            progressDialog.dismiss();
        }

        public boolean validate() {
            boolean valid = true;

            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("ingrese un email válido");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("entre 4 a 10 letras");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            return valid;
        }

    private void validateParse() {
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        Log.e("validandoParse", email + " - " + password);

        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {

                if (user != null) {

                    Log.e("userParse", "name:" + user.get("name").toString() + " email:" + user.getUsername().toString() + " iD:" + user.getObjectId().toString());
                    UserController.getInstance().setUser(new User(user.get("name").toString(),
                            user.getUsername().toString(), user.getObjectId().toString()));
                    goToMain();
                } else {
                    onLoginFailed();
                }
            }
        });

    }
    private void goToMain(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        progressDialog.dismiss();
        _loginButton.setEnabled(true);
    }
}
