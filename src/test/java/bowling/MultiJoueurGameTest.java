package bowling;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MultiJoueurGameTest {
    private PartieMultiJoueurs partie;
    private String[] players = {"Pierre", "Paul"};

    @BeforeEach
    public void setUp() {
        partie = new PartieMultiJoueurs();
        partie.demarreNouvellePartie(players);
    }

    @Test 
    public void premierTir(){
        assertEquals("Pierre", partie.getJouerCourant());
    }

    @Test
    public void deuxiemeTir(){
        partie.enregistreLancer(5);
        assertEquals("Pierre", partie.getJouerCourant());
    }

    @Test
    public void changementDeJoueur(){
        partie.enregistreLancer(5);
        partie.enregistreLancer(3);
        assertEquals("Paul", partie.getJouerCourant());
    }

    @Test
    public void strikeChangeJoueur(){
        faireUnStrike();
        assertEquals("Paul", partie.getJouerCourant());
    }

    @Test
    public void partieTerminee(){
        for(int i = 0; i < 12; i++){
            partie.enregistreLancer(10); 
            partie.enregistreLancer(10);
        }
        assertEquals(true, partie.estTermine());
    }

    @Test
    public void scorePourJoueur(){
        partie.enregistreLancer(3);
        partie.enregistreLancer(6);
        assertEquals(9, partie.scorePour("Pierre"));
    }

    @Test
    public void scoreNulDebutJeu(){
        assertEquals(0, partie.scorePour("Pierre"));
        assertEquals(0, partie.scorePour("Paul"));
    }
        
    @Test
	void deuxStrikes() {
		faireUnStrike(); // Pierre = 10 + 4 + 3
		faireUnStrike(); // Paul = 10 
		partie.enregistreLancer(4); // 4
		partie.enregistreLancer(3); // 3
		assertEquals( (10 + 4 + 3) + 4 + 3, partie.scorePour("Pierre"));
        assertEquals(10, partie.scorePour("Paul"));
	}

    @Test
    void unSpare() {
        faireUnSpare(); // Pierre = 7 + 3 + 3
        partie.enregistreLancer(10); // Paul = 3
        partie.enregistreLancer(3); // Pierre = 3
        assertEquals(16, partie.scorePour("Pierre"));
        assertEquals(10, partie.scorePour("Paul"));
    }


    @Test
    void testPerfectGame() {
        // 12 boules à 10 points pour chaque joueur
        for(int i = 0; i < 12; i++){
            partie.enregistreLancer(10); // Pierre
            partie.enregistreLancer(10); // Paul
        }
        assertEquals(300, partie.scorePour("Pierre"));
        assertEquals(300, partie.scorePour("Paul"));
        assertTrue(partie.estTermine());
    }


    @Test
    public void playerNonExistent(){
        try {
            partie.scorePour("Jacques"); 
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Le joueur Jacques n'existe pas");
        }
    }


    @Test
    public void pasDeJoueur(){
        try {
            partie.demarreNouvellePartie(null); 
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Il n'y a pas de joueur");
        }
    }


    @Test
    public void partieDejaTerminee(){
        for(int i = 0; i < 12; i++){
            partie.enregistreLancer(10); 
            partie.enregistreLancer(10);
        }
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            partie.enregistreLancer(5);
        });
        Assertions.assertThat(exception).hasMessage("La partie est terminée");
    }



    private void faireUnStrike() {
		partie.enregistreLancer(10);
	}

    private void faireUnSpare() {
		partie.enregistreLancer(7);
		partie.enregistreLancer(3);
	}

}
