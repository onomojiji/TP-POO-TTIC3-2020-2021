package Interfaces;

import java.io.IOException;

public interface CompteInterface {
    int getNumero_compte();

    String getNom_titulaire();

    String getSituation();

    long getSolde();

    void setSolde(long solde);

    long getDebit_max();

    void setDebit_max(long debit_max);

    void setDecouvert_max(long decouvert_max);

    long getDecouvert_max();

    String getDate_creation();

    void crediter_compte(long montant) throws IOException;

    boolean debiter_compte(long montant) throws IOException;

    void virement(CompteInterface recepteur, long montant) throws IOException;

    void modifier_decouvert(long decouvert_max) throws IOException;
    void modifier_debit(long debit_max) throws IOException;


}
