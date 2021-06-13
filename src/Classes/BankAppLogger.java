package Classes;

import Interfaces.BankAppLoggerInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * @author Groupe 4
 * @version JDK V11
 *
 *  Cette classe nous permet d'enregistrer toutes les actions de l'administrateur sur les comptes bancaires.
* */

public class BankAppLogger implements BankAppLoggerInterface {

    // Ici on indique le chemin du fichier Log à notre programme
    Path path = Paths.get("src/Fichiers/log.txt");


    SimpleDateFormat s = new SimpleDateFormat("EE dd/MM/YYYY HH:mm:ss");
    Date date = new Date();

    // Liste des Comptes Bancaire
    private final static List<Compte> listeCompte = new ArrayList<Compte>();

    /**
     * Méthode permettant d'afficher le contenu du fichier des Logs sur la console
     * **/
    @Override
    public void showLog() throws IOException {

        for (String ligne : Files.readAllLines(path, StandardCharsets.UTF_8)) { // Pour chaque ligne du fichier,
            System.out.println(ligne); // Affiche cette ligne sur la console
        }

    }


    /**
     * Méthode permettant d'écrire dans notre fichier des Logs, elle prend en paramètre :
     * @param contenu : String
     * **/
    @Override
    public void addLog(String contenu) throws IOException {
        String contenu_avant = ""; // On crée d'abord une variable vide dans laquelle on va introduire le texte contenu dans le fichier
        for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) // Pour chaque ligne du ficgier
             contenu_avant += line + "\n";

        String contenu_apres = contenu_avant + "\n" + "[" + s.format(date) + "] " + contenu;

        Files.write(path, contenu_apres.getBytes(StandardCharsets.UTF_8)); // On écrit chaque ligne du contenu_apres dans le fichier

    }

}
