package com.grenoble.miage.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.grenoble.miage.projet.data.* ;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int ADD_USER = 499 ;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private DataBDD bdd = ((MyApplication) getApplication()).getBdd();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    public void login(View view) {
        Usager user = bdd.getUser(mEmailView.getText().toString());
        if (user != null && user.getPassword().equals(mPasswordView.getText().toString())){
            ((MyApplication) getApplication()).setConnectedUser(user);
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void createUser(View view){
        Intent intent = new Intent(LoginActivity.this,AddUsagerActivity.class);
        startActivityForResult(intent,ADD_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK){
            if (requestCode == ADD_USER){
                if (data.getExtras() != null){
                    Usager usager = (Usager) data.getSerializableExtra(AddUsagerActivity.NEW_USER);
                    bdd.ajouterUsaget(usager);
                    ((MyApplication) getApplication()).setConnectedUser(usager);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        }
    }
}

