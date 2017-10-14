package com.tcc.natha.gerenciadordd.models.aventura;

/**
 * Created by rafae on 14/10/2017.
 */

public class Aventura {

    private String userID;
    private String nomeAventura;
    private int seqAventura;

    public Aventura(){
    }

    public Aventura(String userID){
        this.userID = userID;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNomeAventura() {
        return nomeAventura;
    }

    public void setNomeAventura(String nomeAventura) {
        this.nomeAventura = nomeAventura;
    }

    public int getSeqAventura() {
        return seqAventura;
    }

    public void setSeqAventura(int seqAventura) {
        this.seqAventura = seqAventura;
    }
}
