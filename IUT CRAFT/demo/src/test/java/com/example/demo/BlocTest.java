package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlocTest {
    Bloc bloc = new Bloc();
    List<String> nom = new ArrayList<>();
    @BeforeEach
    void setUp() {
        bloc.ajoute("test");
        nom.add("test");
    }

    @Test
    void testGetNom() {
        assertEquals(bloc.getNom(),nom);
    }

    @Test
    void testAjoute() {
        String newNom = "test2";
        nom.add(newNom);
        bloc.ajoute(newNom);
        assertEquals(bloc.getNom(),nom);
    }
}