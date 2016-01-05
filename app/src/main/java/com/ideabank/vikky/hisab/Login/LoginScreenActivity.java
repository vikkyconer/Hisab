package com.ideabank.vikky.hisab.Login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ideabank.vikky.hisab.R;


public class LoginScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

//        new LoginPresenter(loginModel(), loginView());
    }

    /*private LoginView loginView() {
        return null;
    }

    private LoginModel loginModel() {
        return null);
    }*/

    private Object service() {
        return null;
    }


}
