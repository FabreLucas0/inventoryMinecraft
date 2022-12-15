package com.example.demo;

public abstract class Bloc_decorateur implements Recipies{

    protected Bloc shaped;




    public Bloc_decorateur(Bloc shaped) {
        this.shaped = shaped;
    }


    public void getNom(String nom) throws Exception {
        shaped.ajoute(nom);
        return;
    }



}
