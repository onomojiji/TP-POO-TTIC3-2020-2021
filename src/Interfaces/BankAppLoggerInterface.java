package Interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BankAppLoggerInterface {
    void showLog() throws IOException;

    void addLog(String contenu) throws IOException;

}
