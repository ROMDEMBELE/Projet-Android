package com.grenoble.miage.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.grenoble.miage.projet.data.Usager;

public class AddUsagerActivity extends AppCompatActivity {

    public static final String NEW_USER = "NEW_USER" ;
    public EditText editText_nom ;
    public EditText editText_prenom ;
    public EditText editText_tel ;
    public EditText editText_mail ;
    public EditText editText_password ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usager);
        editText_nom = (EditText) findViewById(R.id.editText_nom);
        editText_prenom = (EditText) findViewById(R.id.editText_prenom);
        editText_tel = (EditText) findViewById(R.id.editText_tel);
        editText_mail = (EditText) findViewById(R.id.editText_mail);
        editText_password = (EditText) findViewById(R.id.editText_password);
    }

    public void addUsager(View view){
        Usager usager = new Usager(editText_nom.getText().toString(),editText_prenom.getText().toString(),editText_tel.getText().toString(),editText_mail.getText().toString(),editText_password.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(NEW_USER,usager);
        setResult(RESULT_OK,intent);
        finish();
    }
}
