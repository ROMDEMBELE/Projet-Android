package com.grenoble.miage.projet;

import android.app.Application;

import com.grenoble.miage.projet.data.DataBDD;
import com.grenoble.miage.projet.data.DataSQLite;
import com.grenoble.miage.projet.data.Usager;

/**
 * Created by Dembeler on 20/04/2017.
 */

public class MyApplication extends Application {

    private static String login ;
    DataBDD bdd ;

    @Override
    public void onCreate() {
        super.onCreate();
        bdd = new DataBDD(this);
        bdd.open();
    }

    public DataBDD getBdd(){
        return bdd;
    }

    public String getLogin(){
        return login ;
    }

    public boolean setConnectedUser(Usager user){
        if (user != null) {
            this.login = user.getMail();
            return true;
        }
        return false ;
    }

    @Override
    public void onTerminate() {
        this.login = null ;
        bdd.close();
        super.onTerminate();
    }
}
