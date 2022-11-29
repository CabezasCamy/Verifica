package itis.meucci;

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ClientStr {
    String nomeServer = "localhost";
    int portaServer = 2018;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    BufferedReader inDalServer;
    DataOutputStream outVersoServer;
    
    XmlMapper xmlMapper = new XmlMapper();

    public Socket connetti() 
    {
        System.out.println("2: il cliente e' partito in esecuzione..." + '\n');
        try
        {
            //creo un socket
            miosocket = new Socket(nomeServer, portaServer);
            //stream di strittura e lettura 
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            System.out.println("Host sconosciuto");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        //
        return miosocket;
    }

    public void comunica()
    {
        for(;;)
        {
            try
            {
                //System.out.println("4: inserire la stringa da trasmettere al server: ");
                //stringaUtente = tastiera.readLine(); //lettura da tastiera
                //spedisco la stringa al server
                System.out.println("5: invio dell'Array al server e attendo...");
                String xml = xmlMapper.writeValueAsString(new Messaggi().listaBiglietti);
                outVersoServer.writeBytes(xml + '\n');

                //visualizzazione dei biglietti disponibili
                System.out.println("visualizzazione dei biglietti disponibili");
                //risposta del server
                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println(stringaRicevutaDalServer + '\n');

                //l'elenco dei biglietti da acquistare
                System.out.println("inserire numero di identificazione del biglietto da acquistare, inserire FINE se hai concluso l'acquisto");
                do{
                    stringaUtente = tastiera.readLine();
                    outVersoServer.writeBytes(stringaUtente + '\n');

                }while(stringaUtente != "FINE");
                outVersoServer.writeBytes("FINE" + '\n');
                
                //ricevere carrello con i biglietti acquistati
                System.out.println("visualizzazione dei biglietti acquistati");
                stringaRicevutaDalServer = inDalServer.readLine();

                //chiudo la connessione
                System.out.println("9: termine elaborazione e chiusura connessione");
                miosocket.close();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione con il server" + e);
                System.exit(1);
            }
        }
    }
}

