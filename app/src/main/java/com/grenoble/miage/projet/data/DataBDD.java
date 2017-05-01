package com.grenoble.miage.projet.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dembeler on 29/04/2017.
 */

public class DataBDD {

    private SQLiteDatabase bdd;

    private DataSQLite dat;

    public DataBDD(Context context){
        //On crée la BDD et sa table
        dat = new DataSQLite(context);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = dat.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long ajouterTicket(Ticket ticket,String mail){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(dat.DATE_TICKET, ticket.getDateDemande().getTime());
        values.put(dat.HEURE_DEBUT, ticket.getHeureDebut());
        values.put(dat.HEURE_FIN, ticket.getHeureFin());
        values.put(dat.DUREE_INIT, ticket.getDureeInit());
        values.put(dat.DUREE_EFFEC, ticket.getDureeEffec());
        values.put(dat.COUT_TICKET, ticket.getCoutTotal());
        values.put(dat.ETAT_TICKET, ticket.isEncours());
        values.put(dat.VOITURE_TICKET, ticket.getVoiture().getPlaque());
        values.put(dat.ZONE_TICKET, ticket.getZone().getCoordonnee());
        values.put(dat.USAGER_TICKET, mail);
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(dat.TABLE_TICKET, null, values);
    }

    public long ajouterVoiture(Voiture v, String mail){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(dat.MARQUE,v.getMarque());
        values.put(dat.MODELE,v.getModele());
        values.put(dat.PLAQUE,v.getPlaque());
        values.put(dat.QRCODE,v.getQRcode());
        values.put(dat.USAGER_VOITURE,mail);
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(dat.TABLE_TICKET, null, values);
    }

    public long ajouterUsaget(Usager u){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(dat.USAGER_NOM,u.getNom());
        values.put(dat.USAGER_PRENOM,u.getPrenom());
        values.put(dat.USAGER_TEL,u.getTel());
        values.put(dat.USAGER_MAIL,u.getMail());
        values.put(dat.USAGER_PASSWORD,u.getPassword());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(dat.TABLE_TICKET, null, values);
    }

    public int updateTicket(Ticket ticket){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(dat.HEURE_FIN, ticket.getHeureFin());
        values.put(dat.DUREE_EFFEC, ticket.getDureeEffec());
        values.put(dat.COUT_TICKET, ticket.getCoutTotal());
        values.put(dat.ETAT_TICKET, ticket.isEncours());
        return bdd.update(dat.TABLE_TICKET, values, dat.DATE_TICKET + " = " +ticket.getDateDemande().toString() +
                " AND " + dat.ZONE_TICKET + " = " + ticket.getZone().getCoordonnee() +
                " AND " + dat.HEURE_DEBUT + " = " + ticket.getHeureDebut(), null);
    }

    public List<Ticket> getUserTickets(String mail){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(dat.TABLE_TICKET, new String[] {dat.DATE_TICKET, dat.HEURE_DEBUT, dat.HEURE_FIN,dat.DUREE_INIT,dat.DUREE_EFFEC,dat.COUT_TICKET,dat.ETAT_TICKET,dat.VOITURE_TICKET,dat.ZONE_TICKET,dat.USAGER_TICKET},dat.USAGER_TICKET + "=" + mail,null, null, null, null);
        if (c.getCount() == 0){
            return null ;
        }
        List<Ticket> tickets = new ArrayList<>();
        while (c.moveToNext()){
            //public Ticket(Date dateDemande, long heureDebut, long dureeInit, long heureFin, long dureeEffec, Double coutTotal, boolean encours, Voiture voiture, Zone zone, Usager user) {
            Voiture voiture = getVoiture(c.getString(7));
            Zone zone = getZone(c.getString(8));
            boolean encours = false ;
            if(c.getInt(6) == 1){
                encours = true ;
            }
            Ticket ticket = new Ticket(new Date(c.getLong(0)),c.getLong(1),c.getLong(2),c.getLong(3),c.getLong(4),c.getDouble(5),encours,voiture,zone,mail);
            //Ticket ticket = new Ticket(new Date(c.getLong(0)),c.getLong(1),c.getLong(2),c.getLong(3),c.getLong(4),c.getFloat(5),)
            tickets.add(ticket);
        }
        return tickets ;
    }

    public Usager getUser(String mail){
        //String nom, String prenom, String tel, String mail, String password
        Cursor c = bdd.query(dat.TABLE_USAGER, new String[] {dat.USAGER_NOM,dat.USAGER_PRENOM,dat.USAGER_TEL,dat.USAGER_MAIL,dat.USAGER_PASSWORD },dat.USAGER_MAIL + "=" + mail,null, null, null, null);
        if(c.getCount() != 0){
            return new Usager(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)) ;
        }
        return null ;
    }

    public List<Voiture> getUserVoitures(String mail){
        List<Voiture> voitures = new ArrayList<>();
        Cursor c = bdd.query(dat.TABLE_VOITURE, new String[] {dat.MARQUE,dat.MODELE,dat.PLAQUE},dat.USAGER_VOITURE + " = " + mail,null,null,null,null);
        while (c.moveToNext()){
            voitures.add(new Voiture(c.getString(0),c.getString(1),c.getString(2)));
        }
        return voitures ;
    }

    public Voiture getVoiture(String plaque){
        Cursor c = bdd.query(dat.TABLE_VOITURE, new String[] {dat.MARQUE,dat.MODELE,dat.PLAQUE},dat.PLAQUE + " = " + plaque,null,null,null,null);
        while (c.moveToNext()){
            return new Voiture(c.getString(0),c.getString(1),c.getString(2));
        }
        return null ;
    }

    public Zone getZone(String coordonnee){
        Cursor c = bdd.query(dat.TABLE_VOITURE, new String[] {dat.COORDONNEE,dat.TARIF},dat.COORDONNEE + " = " + coordonnee,null,null,null,null);
        while (c.moveToNext()){
            return new Zone(c.getString(0),c.getDouble(1));
        }
        return null ;
    }

    public List<Zone> getZones(){
        List<Zone> zones = new ArrayList<>();
        Cursor c = bdd.query(dat.TABLE_VOITURE, new String[] {dat.COORDONNEE,dat.TARIF},null,null,null,null,null);
        while (c.moveToNext()){
            zones.add(new Zone(c.getString(0),c.getDouble(1)));
        }
        return zones ;
    }
}
