package com.grenoble.miage.projet.data;

import android.database.Cursor;

import com.grenoble.miage.projet.TimeUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dembeler on 15/03/2017.
 */

public class Ticket implements Serializable{

    private Date dateDemande ;
    private long heureDebut ;
    private long dureeInit ;
    private long heureFin ;
    private long dureeEffec ;
    private Double coutTotal ;
    private boolean encours ;
    private Voiture voiture ;
    private Zone zone;
    private String user ;

    public Ticket(Date dateDemande, long heureDebut, long dureeInit, Voiture voiture, Zone zone) {
        setDateDemande(dateDemande);
        setHeureDebut(heureDebut);
        setDureeInit(dureeInit);
        setVoiture(voiture);
        setZone(zone);
        encours = true ;
    }

    public Ticket(Date dateDemande, long heureDebut, long heureFin, long dureeInit, long dureeEffec, Double coutTotal, boolean encours, Voiture voiture, Zone zone, String user) {
        this.dateDemande = dateDemande;
        this.heureDebut = heureDebut;
        this.dureeInit = dureeInit;
        this.heureFin = heureFin;
        this.dureeEffec = dureeEffec;
        this.coutTotal = coutTotal;
        this.encours = encours;
        this.voiture = voiture;
        this.zone = zone;
        this.user = user;
    }

    public void StopTicket(long heureFin){
        setHeureFin(heureFin);
        setDureeEffec(getHeureFin()-getHeureDebut());
        setCoutTotal(TimeUtil.getMinSec(getDureeEffec())[1] * getZone().getZonetarif());
    }

    public boolean isEncours() {
        return encours;
    }

    public void setEncours(boolean encours) {
        this.encours = encours;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public long getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(long heureDebut) {
        this.heureDebut = heureDebut;
    }

    public long getDureeInit() {
        return dureeInit;
    }

    public void setDureeInit(long dureeInit) {
        this.dureeInit = dureeInit;
    }

    public long getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(long heureFin) {
        this.heureFin = heureFin;
    }

    public long getDureeEffec() {
        return dureeEffec;
    }

    private void setDureeEffec(long dureeEffec) {
        this.dureeEffec = dureeEffec;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Double getCoutTotal() {
        return coutTotal;
    }

    private void setCoutTotal(Double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ticket{" + getDateDemande() + ", voiture : " + getVoiture() + ", zone" + getZone().toString() +'}';
    }
}
