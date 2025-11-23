package scenarios;
import bowling.PartieMultiJoueurs;

public class scenario {
    public static void main(String[] args) {
        PartieMultiJoueurs partie = new PartieMultiJoueurs(); 
        String[] players = { "Pierre", "Paul" }; 
        partie.demarreNouvellePartie( players );
        System.out.println(partie);

        partie.enregistreLancer(5);

        System.out.println(partie);

        partie.enregistreLancer(3);

        System.out.println(partie);
        System.out.println("Pierre: " + partie.scorePour("Pierre"));

        partie.enregistreLancer(5);

        System.out.println(partie);

        partie.enregistreLancer(5);

        System.out.println(partie);
        System.out.println("Paul: " + partie.scorePour("Paul"));

        partie.enregistreLancer(3);

        System.out.println(partie);

        System.out.println("Paul: " + partie.scorePour("Paul"));
        System.out.println("Pierre: " + partie.scorePour("Pierre"));
        try {
            System.out.println(partie.scorePour("Jacques")); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
