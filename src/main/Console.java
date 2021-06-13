package main;

import Classes.BankAppLogger;
import Interfaces.BankAppLoggerInterface;
import Classes.Compte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class Console {


    // Liste des Comptes Bancaire
    private final static List<Compte> listeCompte = new ArrayList<Compte>();


    /**
     * La méthode afficher() permet tout simplement d'afficher un compte sous la forme visible et
     * comprehensible par une personne physique...
     * elle prend en paramètre :
     * @param numero : int
     *               qui représente l'identifiant du compte dans notre liste de compte.
     *
     * **/
    private static void afficherCompte(int numero) {

        System.out.println(
                "-----------------------------------\n" +
                "Numéro du compte : " + listeCompte.get(numero).getNumero_compte() + "\n" +
                "Nom du propriétaire : " + listeCompte.get(numero).getNom_titulaire() + "\n" +
                "Solde du compte : " + Math.abs(listeCompte.get(numero).getSolde()) + " Fcfa\n" +
                "Situation : " + listeCompte.get(numero).getSituation() + "\n" +
                "Découvert max : " + Math.abs(listeCompte.get(numero).getDecouvert_max()) + " Fcfa\n" +
                "Débit max : " + Math.abs(listeCompte.get(numero).getDebit_max()) + " Fcfa\n" +
                "Date de création : " + listeCompte.get(numero).getDate_creation() + "\n" +
                "------------------------------------"
        );

    }


    /**
     * La méthode charger_compte() permet tout simplement lire la base de donnéees des comptes existant et de les charger dans l'application
     *
     * **/
    public static void charger_comptes() throws IOException {
        File fichier =new File("src/Fichiers/liste_des_comptes.txt");
        //Création du FileReader (voir doc)
        FileReader fileR = new FileReader(fichier);
        //Création du BufferedReader (voir doc)
        BufferedReader bufferedR = new BufferedReader(fileR);
        //Initilisation de l'objet String utilisé pour la lecture
        String s;
        //Initilisation du tableau des mots
        String[] mots =null;
        //On lit chaque ligne jusqu'à ce qu'il n'y en ai plus
        while((s=bufferedR.readLine())!=null)
        {
            //Split des mots par point virgule
            mots=s.split(";");
            listeCompte.add(new Compte(parseInt(mots[0]), mots[1], Long.valueOf(mots[2]).longValue(), Long.valueOf(mots[3]).longValue(),
                    Long.valueOf(mots[4]).longValue(), mots[5]));
        }

    }


    /**
     * La méthode stocker_comptes() permet tout simplement stocker la liste de nos comptes dans la base de donnéees des comptes
     * existante dans l'application
     *
     * **/
    public static void stocker_comptes() throws IOException {

        // Ici on indique le chemin du fichier de la liste des comptes à notre programme
        Path path = Paths.get("src/Fichiers/liste_des_comptes.txt");

        String contenu = "";

        // on récupère d'abord la liste des comptes que
        for (int i = 0; i<listeCompte.size(); i++)
            contenu += listeCompte.get(i).getNumero_compte() + ";" + listeCompte.get(i).getNom_titulaire() + ";" + listeCompte.get(i).getSolde() +
                    ";" + listeCompte.get(i).getDebit_max() + ";" + listeCompte.get(i).getDecouvert_max() + ";" + listeCompte.get(i).getDate_creation() + "\n";

        Files.write(path, contenu.getBytes(StandardCharsets.UTF_8)); // On écrit chaque ligne du contenu_apres dans le fichier

    }


    public static void main(String[] args) throws IOException {

        charger_comptes();

        boolean apk_on = true;
        Scanner clavier = new Scanner(System.in);

        System.out.println(
                "**************************************\n" +
                "BIENVENUE SUR NOTRE APPLICATION Amigo\n" +
                "**************************************");

        while (apk_on){

            System.out.println(
                    "Que voulez vous faire.?\n" +
                            "\t1-) Creer un nouveau compte\n" +
                            "\t2-) Crediter un compte\n" +
                            "\t3-) Debiter un compte\n" +
                            "\t4-) Effectuer un Virement\n" +
                            "\t5-) Modifier un compte\n" +
                            "\t6-) Afficher un compte\n" +
                            "\t7-) Afficher tous les comptes\n" +
                            "\t8-) Afficher l'historique des actions sur un compte\n" +
                            "\t9-) Rien je veux sortir");

            System.out.print("Taper le numéro correspondant : ");
            int reponse = clavier.nextInt();
            switch (reponse){

//////////////////----------------------------------------------------------------------------------------------------------------------------------------
                case 1: // creer un compte
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "--------------------------------\n" +
                            "****** CREATION DE COMPTE ******\n" +
                            "--------------------------------\n"
                    );

                    System.out.println(
                            "Quel type de compte voulez vous créer .?\n" +
                            "1-) Sans Solde Initial\n" +
                            "2-) Avec Solde Initial\n" +
                            "3-) Retour au menu principal"
                    );
                    System.out.print("tapez votre réponse : ");
                    int reponse1 = clavier.nextInt();
                    clavier.nextLine(); // on consomme le retour à la ligne causé par la validation de l'entrée...

                    switch (reponse1){
                        case 1:
                            System.out.print("\nTapez le nom du propriétaire du compte : ");
                            String nom1 = clavier.nextLine();

                            listeCompte.add(new Compte(nom1));
                            break;

                        case 2 :
                            System.out.print("\nTapez le nom du propriétaire du compte : ");
                            String nom2 = clavier.nextLine();
                            System.out.print("\nTapez le solde du compte : ");
                            long solde = clavier.nextLong();
                            System.out.print("\nTapez le débit maximal du compte : ");
                            long debit = clavier.nextLong();
                            System.out.print("\nTapez le découvert maximal du compte : ");
                            long decouvert = clavier.nextLong();

                            listeCompte.add(new Compte(nom2, solde, debit, decouvert));
                            break;
                    }

                    stocker_comptes();

                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;

///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 2: // créditer un compte
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "--------------------------------\n" +
                            "****** CREDITER UN COMPTE ******\n" +
                            "--------------------------------\n"
                    );

                    System.out.print("Tapez l'identifiant du compte : ");
                    int numCompte1 = clavier.nextInt();
                    System.out.print("Solde actuel du compte : " + listeCompte.get(numCompte1).getSolde() + " FCFA\nTapez le montant du crédit : ");
                    int montant = clavier.nextInt();
                    listeCompte.get(numCompte1).crediter_compte(montant);

                    stocker_comptes();

                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;

///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 3: // débiter un compte
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "--------------------------------\n" +
                            "****** DEBITER UN COMPTE ******\n" +
                            "--------------------------------\n"
                    );

                    System.out.print("Tapez l'identifiant du compte : ");
                    int numCompte2 = clavier.nextInt();
                    System.out.print("Solde actuel du compte : " + listeCompte.get(numCompte2).getSolde() + " FCFA\n" +
                            "Montant du débit max : " + listeCompte.get(numCompte2).getDebit_max() + " FCFA\n" +
                            "Montant du découvert max : " + Math.abs(listeCompte.get(numCompte2).getDecouvert_max()) + " FCFA\n" +
                            "Tapez le montant du débit : ");
                    int montant2 = clavier.nextInt();
                    listeCompte.get(numCompte2).debiter_compte(montant2);

                    stocker_comptes();

                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;

///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 4: // éffectuer un virement
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "--------------------------------\n" +
                            "****** VIREMENT BANCAIRE ******\n" +
                            "--------------------------------\n"
                    );

                    System.out.print("Tapez l'identifiant du compte emetteur : ");
                    int idEmet = clavier.nextInt();
                    System.out.print("Tapez l'identifiant du compte recepteur : ");
                    int idRecep = clavier.nextInt();
                    System.out.print("Tapez le montant à transférer : ");
                    int montant3 = clavier.nextInt();

                    listeCompte.get(idEmet).virement(listeCompte.get(idRecep), montant3);

                    stocker_comptes();

                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;


///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 5: // modifier un compte
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "----------------------------------------\n" +
                            "****** MODIFICATION SUR UN COMPTE ******\n" +
                            "----------------------------------------\n"
                    );

                    System.out.print("Tapez l'identifiant du compte : ");
                    int numCompte3 = clavier.nextInt();
                    System.out.print(
                            "Que voulez vous modifier.?\n" +
                                    "\t1-) Le Solde du débit maximal\n" +
                                    "\t2-) Le solde du découvert maximal\n" +
                                    "\t3-) Retour au menu principal\n" +
                            "Tapez votre choix : ");
                    int choix = clavier.nextInt();

                    switch (choix){
                        case 1 :
                            System.out.print("Tapez la nouvelle valeur : ");
                            int montant4 = clavier.nextInt();
                            clavier.nextLine();
                            listeCompte.get(numCompte3).modifier_debit(montant4);
                            System.out.print("Continuer .? Oui(o)/Non(n) ");
                            String rep = clavier.next();
                            switch (rep){
                                case "o":
                                case "O":
                                    continue;
                                case "n":
                                case "N":
                                    break;
                            }


                        case 2 :
                            System.out.print("Tapez la nouvelle valeur : ");
                            int montant5 = clavier.nextInt();
                            clavier.nextLine();
                            listeCompte.get(numCompte3).modifier_decouvert(montant5);
                            System.out.print("Continuer .? Oui(o)/Non(n) ");
                            String rep2 = clavier.next();
                            switch (rep2){
                                case "o":
                                case "O":
                                    continue;
                                case "n":
                                case "N":
                                    break;
                            }
                    }
                    stocker_comptes();
                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;

///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 6: // afficher un compte
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "----------------------------------------\n" +
                            "****** AFFICHAGE D'UN COMPTE ******\n" +
                            "----------------------------------------\n"
                    );

                    System.out.print("Tapez l'identifiant du compte : ");
                    int numCompte4 = clavier.nextInt();
                    afficherCompte(numCompte4);

                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;


///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 7: // afficher la liste de tous les comptes existants
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    System.out.println(
                            "----------------------------------------\n" +
                            "****** LISTE DES COMPTES EXISTANTS ******\n" +
                            "----------------------------------------\n"
                    );

                    for (int id = 0 ; id < listeCompte.size(); id++) {
                        System.out.println(
                        id + "- Compte N°" + listeCompte.get(id).getNumero_compte() + " Appartenant à M/mme " + listeCompte.get(id).getNom_titulaire()
                        );
                    }

                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier
                    continue;


///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 8: // afficher l'historique des actions sur un compte
                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    BankAppLoggerInterface bankAppLoggerInterface = new BankAppLogger();
                    bankAppLoggerInterface.showLog();
                    System.out.println("\n");
                    System.out.println("\nAppuyez sur entrée pour continuer ");
                    clavier.nextLine();clavier.nextLine(); // on crèè une pause en attendant que l'utilisateur valide sur le clavier

                    continue;


///////////////////////////----------------------------------------------------------------------------------------------------------------------------
                case 9: // sortir de l'application

                    // On balai d'abord l'écran
                    try {
                        if (System.getProperty("os.name").startsWith("Windows"))
                            Runtime.getRuntime().exec("cls");
                        else
                            Runtime.getRuntime().exec("clear");

                    }catch (Exception e) {e.printStackTrace();}

                    stocker_comptes();

                    System.out.println(
                            "'''''''''''''''''''''''\n" +
                            "Asta la bista amigo...\n" +
                            ",,,,,,,,,,,,,,,,,,,,,,,");
                    apk_on = false;

            }

        }

    }



}
