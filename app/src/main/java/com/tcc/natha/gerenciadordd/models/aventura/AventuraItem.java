package com.tcc.natha.gerenciadordd.models.aventura;

/**
 * Created by nalmeida on 24/08/2017.
 */

public class AventuraItem {

    private String idAventura;

    private String nomeAventura;

    private int seqAventura;

    public AventuraItem() {
        this.idAventura = "";
        this.nomeAventura = "";
    }

    public AventuraItem( String idAventura,String nomeAventura, int seqAventura) {
        this.idAventura = idAventura;
        this.nomeAventura = nomeAventura;
        this.seqAventura = seqAventura;
    }

    public String getIdAventura() {
        return idAventura;
    }

    public void setIdAventura(String idAventura) {
        this.idAventura = idAventura;
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
