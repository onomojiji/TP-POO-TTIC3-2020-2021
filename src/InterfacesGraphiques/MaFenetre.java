package InterfacesGraphiques;

import javax.swing.*;

public class MaFenetre extends JFrame {

    public static final long serialVersionUIO = -49284545456464L;


    public MaFenetre(){
        super("ma première application");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        MaFenetre maFenetre = new MaFenetre();
        maFenetre.setVisible(true);
    }

}
