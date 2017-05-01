package com.grenoble.miage.projet.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dembeler on 15/03/2017.
 */

public class DataSQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1 ;
    public static final String DATABASE_NAME = "DataTicket.db" ;
    public static final String TABLE_TICKET = "table_tickets" ;
    public static final String TABLE_USAGER = "table_usagers" ;
    public static final String TABLE_VOITURE = "table_voitures" ;
    public static final String TABLE_ZONE = "table_zones" ;

    public static  final String USAGER_NOM = "nom" ;
    public static  final String USAGER_PRENOM = "prenom" ;
    public static  final String USAGER_TEL = "telephone" ;
    public static  final String USAGER_MAIL = "mail" ;
    public static  final String USAGER_PASSWORD = "password" ;
    public static  final String DATE_TICKET = "DATE_TICKET" ;
    public static  final String HEURE_DEBUT = "HEURE_DEBUT" ;
    public static  final String HEURE_FIN = "HEURE_FIN" ;
    public static  final String DUREE_INIT = "DUREE_INIT" ;
    public static  final String DUREE_EFFEC = "DUREE_EFFEC" ;
    public static  final String VOITURE_TICKET = "VOITURE_TICKET" ;
    public static  final String USAGER_TICKET = "USAGER_TICKET" ;
    public static  final String ZONE_TICKET = "ZONE_TICKET";
    public static  final String COUT_TICKET = "COUT_TICKET" ;
    public static  final String ETAT_TICKET = "ETAT_TICKET" ;
    public static  final String MARQUE = "MARQUE" ;
    public static  final String MODELE = "MODELE" ;
    public static  final String PLAQUE = "PLAQUE" ;
    public static  final String IMAGE = "IMAGE" ;
    public static  final String QRCODE = "QRCODE" ;
    public static  final String USAGER_VOITURE = "USAGER_VOITURE" ;
    public static  final String COORDONNEE = "COORDONNEE" ;
    public static  final String TARIF = "TARIF" ;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_USAGER +
            "(" + USAGER_MAIL + " TEXT PRIMARY KEY,"
                + USAGER_PRENOM + "TEXT ,"
                + USAGER_TEL + " TEXT ,"
                + USAGER_NOM + " TEXT ,"
                + USAGER_PASSWORD + " TEXT );"
            + "CREATE TABLE " + TABLE_TICKET +
                " (" + DATE_TICKET + " REAL ,"
                + HEURE_DEBUT + " REAL ,"
                + HEURE_FIN + " REAL ,"
                + DUREE_INIT + " REAL ,"
                + DUREE_EFFEC + " REAL ,"
                + VOITURE_TICKET + " TEXT ,"
                + USAGER_TICKET + " TEXT ,"
                + ZONE_TICKET + " TEXT ,"
                + COUT_TICKET + " REAL ,"
                + ETAT_TICKET + " INT, "
                +"FOREIGN KEY (" +VOITURE_TICKET+") REFERENCES "+TABLE_VOITURE+"("+PLAQUE+"),"
                +"FOREIGN KEY (" +ZONE_TICKET+") REFERENCES "+TABLE_ZONE+"("+COORDONNEE+"),"
                +"FOREIGN KEY (" +USAGER_TICKET+") REFERENCES "+TABLE_USAGER+"("+USAGER_MAIL+"),"
                +"PRIMARY KEY ("+DATE_TICKET+","+ZONE_TICKET+","+HEURE_DEBUT+"));"
            + "CREATE TABLE " + TABLE_VOITURE +
                " ( " + PLAQUE + " TEXT PRIMARY KEY,"
                + MARQUE + " TEXT,"
                + MODELE + " TEXT,"
                + IMAGE + " TEXT,"
                + QRCODE + "TEXT,"
                + USAGER_VOITURE + " TEXT,"
                +"FOREIGN KEY (" +USAGER_VOITURE+") REFERENCES "+TABLE_USAGER+"("+USAGER_MAIL+"));"
            + "CREATE TABLE " + TABLE_ZONE +
                " ( " + COORDONNEE + " TEXT PRIMARY KEY,"
                + TARIF + " REAL );" ;

    private static final String SQL_DELETE_ENTRIES = " DROP TABLE " + TABLE_TICKET + " ;"
            + "DROP TABLE " + TABLE_VOITURE + "; "
            + "DROP TABLE " + TABLE_USAGER + "; "
            + "DROP TABLE " + TABLE_ZONE + "; " ;

    public DataSQLite(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
