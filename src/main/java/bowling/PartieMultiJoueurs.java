package bowling;
import java.util.Hashtable;

public class PartieMultiJoueurs implements IPartieMultiJoueurs{
    private Hashtable<String, PartieMonoJoueur> partieDesJoueurs = new Hashtable<String, PartieMonoJoueur>();
    private int numJoueurCourant;
    private String[] listeJoueurs;
    private String joueurCourant;
    private PartieMonoJoueur partieJoueurCourant;

    public PartieMultiJoueurs(){
    }

    public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
        if(nomsDesJoueurs == null){
            throw new IllegalArgumentException("Il n'y a pas de joueur");
        }
        this.listeJoueurs = nomsDesJoueurs;
        joueurCourant = nomsDesJoueurs[0];
        numJoueurCourant = 0;
        for(String joueur: nomsDesJoueurs){
            partieDesJoueurs.put(joueur, new PartieMonoJoueur());
        }
        partieJoueurCourant = partieDesJoueurs.get(joueurCourant);
        return "Le jeu a commencé avec " + nomsDesJoueurs.length + " joueurs.";
    }

    public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException{
        if(estTermine()){
            throw new IllegalStateException("La partie est terminée");
        }
        String texte = "Le joueur " + joueurCourant + " a lancé et abattu " + nombreDeQuillesAbattues + " quilles.";
        boolean doitRelancer = partieJoueurCourant.enregistreLancer(nombreDeQuillesAbattues);
        if(!doitRelancer){
            changerJoueurCourant();
        }
        return texte;
        
    }

    public void changerJoueurCourant(){
        numJoueurCourant = (numJoueurCourant + 1) % listeJoueurs.length;
        joueurCourant = listeJoueurs[numJoueurCourant];
        partieJoueurCourant = partieDesJoueurs.get(joueurCourant);
    }

    public int getNumJoueurCourant(){
        return numJoueurCourant;
    }

    public boolean estTermine(){
        boolean statut = true;
        for(PartieMonoJoueur joueur: partieDesJoueurs.values()){
            statut = statut && joueur.estTerminee();
        }
        return statut;
    }

    public String getJouerCourant(){
        return joueurCourant;
    }

    @Override
    public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
        if(!partieDesJoueurs.containsKey(nomDuJoueur)){
            throw new IllegalArgumentException("Le joueur "+ nomDuJoueur + " n'existe pas");
        }
        return partieDesJoueurs.get(nomDuJoueur).score();
    }

    @Override
    public String toString(){
        return "Prochain tir : joueur "+ joueurCourant + ", tour n°"+partieJoueurCourant.numeroTourCourant() +", boule n° "+ partieJoueurCourant.numeroProchainLancer();
    }


}
