package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate paate;

    @Before
    public void setUp() {
        paate = new Kassapaate();
    }

    @Test
    public void rahaaAlussaOikeaMaara() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void edullistenLounaidenMaaraAlussaOikea() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaidenLounaidenMaaraAlussaOikea() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKasvattaaKassaaOikeinKateisella() {
        paate.syoEdullisesti(10000);
        
        assertEquals(100240, paate.kassassaRahaa());
    }

    @Test
    public void edullinenKasvattaaLounaidenMaaraaOikeinKateisella() {
        paate.syoEdullisesti(10000);

        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void vaihtorahaOikeaEdullinenKortilla() {
        assertEquals(760, paate.syoEdullisesti(1000));
    }

    @Test
    public void maukasKasvattaaKassaaOikeinKateisella() {
        paate.syoMaukkaasti(1000);

        assertEquals(100400, paate.kassassaRahaa());
    }

    @Test
    public void vaihtorahaOikeaMaukasKortilla() {
        assertEquals(600, paate.syoMaukkaasti(1000));
    }
    
    @Test
    public void maukasKasvattaaLounaidenMaaraaOikeinKateisella() {
        paate.syoMaukkaasti(10000);

        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void eiRiitaMaukkaaseenKassaEiMuutuKateisella() {
        paate.syoMaukkaasti(100);
        
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void eiRiitaEdulliseenKassaEiMuutuKateisella() {
        paate.syoEdullisesti(100);
        
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void eiRiitaMaukkaaseenLounaatEiMuutuKateisella() {
        paate.syoMaukkaasti(100);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void eiRiitaEdulliseenLounaatEiMuutuKateisella() {
        paate.syoEdullisesti(100);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    //----------------------------------------------------------------------------------------------------
    
    @Test
    public void edullinenKasvattaaLounaitaOikeinKortilla() {
        paate.syoEdullisesti(new Maksukortti(10000));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKasvattaaLounaitaOikeinKortilla() {
        paate.syoMaukkaasti(new Maksukortti(10000));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaastiVeloittaaOikein() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void edullisestiVeloittaaOikein() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.syoEdullisesti(kortti);

        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void maukasPalauttaaTrueJosRiittaa() {
        assertTrue(paate.syoMaukkaasti(new Maksukortti(1000)));
    }
    
    @Test
    public void edullinenPalauttaaTrueJosRiittaa() {
        assertTrue(paate.syoEdullisesti(new Maksukortti(1000)));
    }
    
    @Test
    public void edullinenEiMuutuKassaJosSaldoEiRiita() {
        paate.syoEdullisesti(new Maksukortti(100));
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void maukasEiMuutuKassaJosSaldoEiRiita() {
        paate.syoMaukkaasti(new Maksukortti(100));

        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void edullinenEiMuutuLounaatJosSaldoEiRiita() {
        paate.syoEdullisesti(new Maksukortti(100));

        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasEiMuutuLounaatJosSaldoEiRiita() {
        paate.syoMaukkaasti(new Maksukortti(100));

        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasPalauttaaFalseJosEiRiita() {
        assertFalse(paate.syoMaukkaasti(new Maksukortti(100)));
    }

    @Test
    public void edullinenPalauttaaFalseJosEiRiita() {
        assertFalse(paate.syoEdullisesti(new Maksukortti(100)));
    }
    
    @Test
    public void maukasKassaEiMuutuKortilla() {
        paate.syoMaukkaasti(new Maksukortti(10000));
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void edullinenKassaEiMuutuKortilla() {
        paate.syoEdullisesti(new Maksukortti(10000));
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortilleLadattaessaKassaKasvaa() {
        paate.lataaRahaaKortille(new Maksukortti(100), 1000);
        assertEquals(101000, paate.kassassaRahaa());
    }

    @Test
    public void kortilleLadattaessaSaldoKasvaa() {
        Maksukortti kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(1100, kortti.saldo());
    }
    
    @Test
    public void kortilleLadattaessaNegatiivinenMaaraMitaanEiTapahdu() {
        paate.lataaRahaaKortille(new Maksukortti(100), -100);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
}
