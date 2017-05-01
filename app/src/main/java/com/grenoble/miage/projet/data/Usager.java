package com.grenoble.miage.projet.data;

import java.io.Serializable;

/**
 * Created by Dembeler on 15/03/2017.
 */

public class Usager implements Serializable{

    private String nom ;
    private String prenom ;
    private String tel ;
    private String mail ;
    private String password ;

    public Usager(String nom, String prenom, String tel, String mail, String password) {
        setNom(nom);
        setPrenom(prenom);
        setMail(mail);
        setTel(tel);
        setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



}
