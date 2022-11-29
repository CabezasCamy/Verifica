package itis.meucci;

import java.util.ArrayList;

public class Messaggi {
    ArrayList <Biglietti> listaBiglietti = new ArrayList <Biglietti>();

    public Messaggi() 
    {
    }

    public ArrayList<Biglietti> getLista() {
        return listaBiglietti;
    }

    public void setLista(ArrayList<Biglietti> lista) {
        listaBiglietti = lista;
    }
}
