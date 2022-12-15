package com.example.demo;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class IutCraftApplicationTest {
    @Test
    void test_recherche (){
        Recherche find = new Recherche("https://minecraft.fandom.com/wiki/");
        find.run();
        Assert.assertEquals(false,find.isInterrupted());
    }
    @Test
    void test_recherche_inturuption (){
        Recherche find = new Recherche("https://minecraft.fandom.com/wiki/");
        find.run();
        find.interrupt();
        Assert.assertEquals(true,find.isInterrupted());
    }
    @Test
    void test_shaped_recipe() throws Exception {
        Shaped_recipes recipes =new Shaped_recipes(new Bloc());
        Assert.assertEquals("nether_brick_slab" ,recipes.getNom().get(0));
    }

    @Test
    void test_shapeless_recipes() throws Exception {
        Shapeless_recipes recipes =new Shapeless_recipes(new Bloc());
        Assert.assertEquals("magenta_dye_from_lapis_red_pink" ,recipes.getNom().get(0));
    }
    @Test
    void test_musique(){

    }
}