package com.grenoble.miage.projet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.grenoble.miage.projet.data.Voiture;

public class AddVoitureActivity extends AppCompatActivity {

    public static final String NEW_VOITURE = "NEW_VOITURE" ;
    private EditText editText_plaque ;
    private AutoCompleteTextView editText_marque ;
    private AutoCompleteTextView editText_modele ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voiture);

        editText_plaque = (EditText) findViewById(R.id.editText_plaque);
        editText_marque = (AutoCompleteTextView) findViewById(R.id.editText_marque);
        ArrayAdapter<String> marqueAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.marque_list));
        editText_marque.setAdapter(marqueAdapter);
        editText_modele = (AutoCompleteTextView) findViewById(R.id.editText_modele);
    }

    public void addVoiture(View view){
        Voiture voiture = new Voiture(editText_marque.getText().toString(),editText_modele.getText().toString(),editText_plaque.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(NEW_VOITURE,voiture);
        setResult(RESULT_OK,intent);
        finish();
    }


}
