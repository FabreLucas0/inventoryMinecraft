package com.example.demo;

import java.util.List;

public class Bloc {

    private List<String> nom;

    public List<String> getNom() {
        return nom;
    }

    public void ajoute(String nom){
        this.nom.add(nom);
        return ;

    }
}
