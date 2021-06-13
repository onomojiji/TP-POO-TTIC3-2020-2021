package Classes;

import Interfaces.BankAppLoggerInterface;
import Interfaces.CompteInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Groupe 4
 * @version JDK V11
 *
 * **/



public class Compte implements CompteInterface {

    /** Il est question de créer une classe Classes.Compte qui est un compte bancaire...
     * Un compte a les attributs suivants :
     *   @param  numero_compte : int
     *   @param nom_titulaire : String
     *   @param solde : long
     *   @param decouvert_max : long
     *   @param debit_max : long
     *   @param date_creation : String
     *   @param situation : String
     *
     *
     * Un administrateur de la banque peut effectuer les actions suivantes :
     *  Creer_compte()
     *  Modifier_compte()
     *  Crediter_compte()
     *  Debiter_compte()
     *  Virement()
     *  Afficher_compte()
     *
     **/


    // -------- ATTRIBUTS --------------------

    private int numero_compte;
    private String nom_titulaire, situation, date_creation;
    private long solde, debit_max, decouvert_max;


    // Procédure de génération de la date et l'heure...
    SimpleDateFormat s = new SimpleDateFormat("EE dd/MM/YYYY HH:mm:ss");
    Date date = new Date();
    BankAppLoggerInterface bankAppLoggerInterface = new BankAppLogger();


    /*-------------- Constructeurs *****************/

    // Constructeur utilisé si la banque veut assigner des informations par défauts au compte....
    public Compte(String nom_proprietaire, long solde, long debit_max, long decouvert_max) {
        if ( (int)(hashCode()/1000000000) != 0) this.numero_compte = hashCode() / 100;
        else this.numero_compte = hashCode()/10;
        this.nom_titulaire = nom_proprietaire;
        this.solde = Math.abs(solde);
        this.debit_max = Math.abs(debit_max);
        this.decouvert_max = - decouvert_max;
        this.date_creation = s.format(date);
        System.out.println("\nCompte N°" + this.getNumero_compte() + " créé avec succès...");
        try {
            bankAppLoggerInterface.addLog( "Compte N°" + this.getNumero_compte() + " créé avec succès et appartient à M/Mme " + getNom_titulaire() + "\n");
        }catch (Exception e) {e.printStackTrace();}

    }

    // Constructeur Utilisé si la banque ne veut pas assigner plus d'informations que le nom du propriétaire du compte
    public Compte(String nom_titulaire ){
        if ((int)(hashCode()/1000000000) != 0) this.numero_compte = hashCode() / 100;
        else this.numero_compte = hashCode()/10;
        this.nom_titulaire = nom_titulaire;
        this.solde = 0;
        this.debit_max = 800;
        this.decouvert_max = - 2000;
        this.date_creation = s.format(date);
        System.out.println("\nCompte N°" + this.getNumero_compte() + " créé avec succès...");
        try {
            // On écrit sur le fichier
            bankAppLoggerInterface.addLog( "Compte N°" + this.getNumero_compte() + " créé avec succès et appartient à M/Mme " + getNom_titulaire() + "\n");
        }catch (Exception e) {e.printStackTrace();}
    }


    // Constructeur utilisé lorsque le système doit chargé le contenu d'un fichier pour afficher les comptes existant...
    public Compte(int numero_compte, String nom_proprietaire, long solde, long debit_max, long decouvert_max, String date_creation) {
        if ((int)(hashCode()/1000000000) != 0) this.numero_compte = hashCode() / 100;
        else this.numero_compte = hashCode()/10;
        this.nom_titulaire = nom_proprietaire;
        this.solde = Math.abs(solde);
        this.debit_max = Math.abs(debit_max);
        this.decouvert_max = - decouvert_max;
        this.date_creation = date_creation;
        try {
            bankAppLoggerInterface.addLog( "Compte N°" + this.getNumero_compte() + " Apparteneant à M/Mme " + getNom_titulaire() + " chargé avec succès \n");
        }catch (Exception e) {e.printStackTrace();}

    }



    /*---------- METHODES -----------------------*/

    // Getters et setters --------------------

    @Override
    public int getNumero_compte() {
        return numero_compte;
    }

    @Override
    public String getNom_titulaire() {
        return nom_titulaire;
    }

    @Override
    public String getSituation() {
        // le compte renvoi la situation du compte en fonction du solde ...
        if (getSolde() < 0) return situation = "Découvert";
        else return situation = "Normale";
    }

    @Override
    public long getSolde() {
        return solde;
    }

    @Override
    public void setSolde(long solde) {
        this.solde = solde;
    }

    @Override
    public long getDebit_max() {
        return debit_max;
    }

    @Override
    public void setDebit_max(long debit_max) {
        this.debit_max = debit_max;
    }

    @Override
    public void setDecouvert_max(long decouvert_max) {
        this.decouvert_max = decouvert_max;
    }

    @Override
    public long getDecouvert_max() {
        return decouvert_max;
    }

    @Override
    public String getDate_creation() {
        return date_creation;
    }

    // Les autres méthodes ---------------

    /**
     * La méthode crediter_compte(long montant) qui permet d'ajouter un montant à la somme
     * déjà existante dans le compte...
     * elle prend en paramètre :
     * @param montant : long
     * **/
    @Override
    public void crediter_compte(long montant) throws IOException {

        setSolde(getSolde() + Math.abs(montant));
        System.out.println("\nCompte N°" + this.getNumero_compte() + " crédité avec succès...");
        bankAppLoggerInterface.addLog(" Compte Nº" + this.getNumero_compte() + " crédité de " + Math.abs(montant) + " Fcfa. Le nouveau solde est : " + getSolde() + " Fcfa"
        );
    }

    /**
     * La méthode debiter_compte(long montant) qui permet de retirer un montant à la somme existante dans un compte
     * mais pour ce faire, il y'a deux conditions à respecter à savoir :
     * 1-) le montant demandé doit être inférieur au debit_max
     * 2-) la soustraction du solde par le montant demandé doit être supérieur au découvert maximal autorisé
     * elle prend en paramètre :
     * @param montant : long
     * @return**/
    @Override
    public boolean debiter_compte(long montant) throws IOException {
        if (montant > getDebit_max()) {
            System.err.println("\nEchec : le montant de debit non autorisé...");
            bankAppLoggerInterface.addLog("Echec de débit sur le Compte N°" + this.getNumero_compte() + " -- Motif : Montant du débit non autorisé ");
            return false;
        }
        else if ((getSolde() - montant) < decouvert_max) {
            System.err.println("\nEchec : le montant de découvert maximal atteint...");
            bankAppLoggerInterface.addLog( "Echec de débit sur le Compte N°" + this.getNumero_compte() + " -- Motif : Montant de découvert maximal atteint... ");
            return false;
        } else {
            setSolde(getSolde() - montant);
            System.out.println("\nCompte N°" + this.getNumero_compte() + " débité avec succès...");
            bankAppLoggerInterface.addLog("Compte N°" + this.getNumero_compte() + " débité de " + Math.abs(montant) + " Fcfa. Le nouveau solde est : " + getSolde() + " Fcfa"
            );
            return true;
        }
    }

    /**
     * La méthode virement() qui permet de transférer de l'argent de ce compte vers
     * un autre.
     * Bien évidemment l'on va debiter le compte initial et l'on creditera son destinataire. à condition que les
     * conditions de debit de compte soient repectées.
     * elle prend en paramètre :
     * @param recepteur : Classes.Compte
     * @param montant : long
     * **/
    @Override
    public void virement(CompteInterface recepteur, long montant) throws IOException {
        if (this.debiter_compte(montant)) {
            recepteur.crediter_compte(montant);}
        else {
            System.err.println("\nEchec de virement sur le Compte N°" + recepteur.getNumero_compte() + " -- Motif : Montant trop élévé ");
            bankAppLoggerInterface.addLog("Echec de virement sur le Compte N°" + recepteur.getNumero_compte() + " -- Motif : Montant trop élévé ");
        }
    }

    /**
     * La methode modifier_decouvert() qui permet de modifier le découvert maximal après création du compte.
     * elle prend en paramètre :
     * @param decouvert_maxM : long
     * **/
    @Override
    public void modifier_decouvert(long decouvert_maxM) throws IOException {
        this.setDecouvert_max(decouvert_maxM);
        System.out.println("\nDécouvert maximal mis à jour avec succès...\n");
        bankAppLoggerInterface.addLog("Découvert maximal du Compte N°" + this.getNumero_compte() + " Modifé, Le nouveau" +
                " découvert maximal est : " + decouvert_maxM + " Fcfa");

    }

    /**
     * La methode modifier_debit() qui permet de modifier le débit maximal après création du compte.
     * elle prend en paramètre :
     * @param debit_maxM : long
     * **/
    @Override
    public void modifier_debit(long debit_maxM) throws IOException {
        this.setDebit_max(debit_maxM);
        System.out.println("\nDebit maximal mis à jour avec succès...\n");
        bankAppLoggerInterface.addLog("Débit maximal du Compte N°" + this.getNumero_compte() + " Modifé, Le nouveau " +
                "débit maximal est : " + debit_maxM + " Fcfa");
    }


}
