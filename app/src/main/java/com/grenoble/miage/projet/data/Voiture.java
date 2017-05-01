package com.grenoble.miage.projet.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Dembeler on 15/03/2017.
 */

public class Voiture implements Serializable {

    private String marque ;
    private String modele ;
    private String plaque ;
    private Bitmap image ;
    private String QRcode;

    public Voiture(Bitmap image, String marque, String modele, String plaque) {
        setMarque(marque);
        setModele(modele);
        setPlaque(plaque);
        setImage(image);
    }

    public Voiture(String marque, String modele, String plaque) {
        setMarque(marque);
        setModele(modele);
        setPlaque(plaque);
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }


    public boolean equals(Voiture vt) {
        if((vt.getMarque().equals(this.getMarque()) && vt.getModele().equals(this.getModele())) || vt.getPlaque().equals(this.getPlaque())){
            return true ;
        }
        return false ;
    }

    @Override
    public String toString() {
        return "Voiture {" + marque + ", " + modele + " : " + plaque ;
    }
}
