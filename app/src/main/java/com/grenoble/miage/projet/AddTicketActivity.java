package com.grenoble.miage.projet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.grenoble.miage.projet.data.*;

import java.util.Date;
import java.util.List;

public class AddTicketActivity extends AppCompatActivity {

    public static final String NEW_TICKET = "NEW_TICKET";

    private Spinner spinner_voiture = (Spinner) findViewById(R.id.spinner_voiture);
    private Spinner spinner_zones = (Spinner) findViewById(R.id.spinner_zones);
    private TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
    private VoitureAdapter voituresAdapter;
    private ZoneAdapter zonesAdapter;
    private DataBDD bdd = ((MyApplication)getApplication()).getBdd() ;
    private MyApplication app = (MyApplication)getApplication() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        voituresAdapter = new VoitureAdapter(this,android.R.layout.simple_spinner_item,bdd.getUserVoitures(app.getLogin()));
        spinner_voiture.setAdapter(voituresAdapter);

        zonesAdapter = new ZoneAdapter(this,android.R.layout.simple_spinner_item,bdd.getZones());
        spinner_zones.setAdapter(zonesAdapter);
    }

    public void addTicket(View view){
        Voiture voiture = voituresAdapter.getItem(spinner_voiture.getSelectedItemPosition());
        Zone zone = zonesAdapter.getItem(spinner_zones.getSelectedItemPosition());
        int hr = picker.getHour();
        int mm = picker.getMinute();
        Ticket ticket = new Ticket(new Date(),new Date().getTime(),TimeUtil.toMs(hr*60+mm,0),voiture,zone);
        Intent intent = new Intent();
        intent.putExtra(NEW_TICKET,ticket);
        setResult(RESULT_OK,intent);
        finish();
    }

    public class VoitureAdapter extends ArrayAdapter<Voiture> {

        List<Voiture> voitures ;
        Context context ;
        public VoitureAdapter(Context context, int resource, List<Voiture> objects) {
            super(context, resource, objects);
            this.voitures = objects ;
            this.context = context ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setText(voitures.get(position).getMarque() + " " + voitures.get(position).getModele());
            return textView ;
        }
    }

    public class ZoneAdapter extends ArrayAdapter<Zone>{

        Context context ;
        List<Zone> zones ;

        public ZoneAdapter(Context context, int resource, List<Zone> objects) {
            super(context, resource, objects);
            this.context = context ;
            this.zones = objects ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setText(zones.get(position).getCoordonnee() + " " + zones.get(position).getZonetarif());
            return textView ;
        }
    }
}
