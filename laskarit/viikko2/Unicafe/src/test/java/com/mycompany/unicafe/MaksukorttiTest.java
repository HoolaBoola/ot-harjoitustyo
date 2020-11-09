package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public  void saldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void saldoOikeinKunRahaaTarpeeksi() {
        kortti.otaRahaa(3);
        
        assertEquals(7, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuKunEiTarpeeksiRahaa() {
        kortti.otaRahaa(100);
        
        assertEquals(10, kortti.saldo());
    }

    
    @Test
    public void palauttaaTrueJosSaldoRiitti() {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void palauttaaFalseJosSaldoEiRiita() {
        assertFalse(kortti.otaRahaa(15));
    }
}
