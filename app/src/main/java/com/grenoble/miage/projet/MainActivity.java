package com.grenoble.miage.projet;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.grenoble.miage.projet.data.DataBDD;
import com.grenoble.miage.projet.data.Ticket;
import com.grenoble.miage.projet.data.Voiture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listViewTickets ;
    Map<Ticket,TicketView> ticketsMap ;
    public static final int ADD_TICKET = 457 ;
    public static final int ADD_VOIUTE = 458 ;
    private DataBDD bdd = ((MyApplication)getApplication()).getBdd() ;
    private MyApplication app = (MyApplication)getApplication() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTickets = (ListView) findViewById(R.id.list_ticket);
        ticketsMap = new HashMap<>();
        List<Ticket> list =  bdd.getUserTickets(app.getLogin()) ;
        for (int i = 0; i < list.size(); i++){
            Ticket ticket = list.get(i);
            if (ticket.isEncours()){
                ticketsMap.put(ticket,new TicketView(ticket,MainActivity.this));
                listViewTickets.addHeaderView(ticketsMap.get(ticket));
            }
        }
    }

    private void UpdateUI(){
        List<Ticket> list =  bdd.getUserTickets(app.getLogin()) ;
        for (int i = 0; i < list.size(); i++){
            Ticket ticket = list.get(i);
            if (ticket.isEncours()){
                ticketsMap.put(ticket,new TicketView(ticket,MainActivity.this));
                listViewTickets.addHeaderView(ticketsMap.get(ticket));
            }
        }
    }

    public void startMainVoiture(View view){

    }

    public void startAddVoiture(View view){
        Intent intent = new Intent(MainActivity.this,AddVoitureActivity.class);
        startActivityForResult(intent,ADD_VOIUTE);
    }

    public void startAddTicket(View view){
        Intent intent = new Intent(MainActivity.this,AddTicketActivity.class);
        startActivityForResult(intent,ADD_TICKET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ADD_TICKET){
                if (data.getExtras() != null){
                    Ticket ticket = (Ticket) data.getSerializableExtra(AddTicketActivity.NEW_TICKET);
                    bdd.ajouterTicket(ticket,app.getLogin());
                    UpdateUI();
                }
            } else if (requestCode == ADD_VOIUTE){
                if (data.getExtras() != null){
                    Voiture voiture = (Voiture) data.getSerializableExtra(AddVoitureActivity.NEW_VOITURE);
                    bdd.ajouterVoiture(voiture,app.getLogin());
                }
            }

        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(NotifyService.REFRESH_TIME_INTENT));
        if(isServiceRunning(NotifyService.class)) {
            for (Map.Entry<Ticket,TicketView> entry : ticketsMap.entrySet()){
                entry.getValue().initButton(false);
            }
        }
        else{
            for (Map.Entry<Ticket,TicketView> entry : ticketsMap.entrySet()){
                entry.getValue().initButton(true);
                entry.getValue().setTimer(0,0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, NotifyService.class));
        super.onDestroy();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotifyService.REFRESH_TIME_INTENT)) {
                if (intent.getExtras() != null){
                    Ticket ticket = (Ticket) intent.getSerializableExtra(NotifyService.TICKET_INTENT);
                    long timeInMs = intent.getLongExtra(NotifyService.KEY_EXTRA_LONG_TIME_IN_MS, 0);
                    ticketsMap.get(ticket).updateGUI(timeInMs);
                }
            }
        }
    };

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public class TicketView extends View {

        Ticket ticket ;
        TextView textView ;
        TimePicker timePicker ;
        Button startButton ;
        Button stopButton ;

        public TicketView(Ticket ticket, Context context) {
            super(context);
            this.ticket = ticket ;
            View view  = inflate(context,R.layout.ticket_layout,listViewTickets);
            textView = (TextView) findViewById(R.id.ticket_tittle);
            timePicker = (TimePicker) findViewById(R.id.ticket_timePicker);
            stopButton = (Button) findViewById(R.id.stopButton);
            startButton = (Button) findViewById(R.id.startButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getStartedValue() >= 1000L){
                        Intent service = new Intent(MainActivity.this, NotifyService.class);
                        service.putExtra(NotifyService.KEY_EXTRA_LONG_TIME_IN_MS, getStartedValue());
                        startService(service);
                        initButton(false);
                    }
                }
            });
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopService(new Intent(MainActivity.this, NotifyService.class));
                    initButton(true);
                }
            });
            setContentView(view);
        }

        private void setTimer(int hour, int minute) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }

        public void initButton(boolean startEnable){
            stopButton.setEnabled(!startEnable);
            startButton.setEnabled(startEnable);
        }

        public long getStartedValue(){
            return TimeUtil.toMs(timePicker.getHour(), timePicker.getMinute());
        }

        public void updateGUI(long timeInMs) {
            Integer[] minSec = TimeUtil.getMinSec(timeInMs);
            setTimer(minSec[TimeUtil.MINUTE], minSec[TimeUtil.SECOND]);
        }


    }
}
