package com.grenoble.miage.projet.data;

/**
 * Created by Dembeler on 15/03/2017.
 */

public class Zone {

    private String coordonnee ;
    private double Zonetarif;

    public Zone(String coordonnee, double tarif) {
        setCoordonnee(coordonnee);
        setZonetarif(tarif);
    }

    public String getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(String coordonnee) {
        this.coordonnee = coordonnee;
    }

    public double getZonetarif() {
        return Zonetarif;
    }

    public void setZonetarif(double zonetarif) {
        this.Zonetarif = zonetarif;
    }

    @Override
    public String toString() {
        return coordonnee ;
    }
}
