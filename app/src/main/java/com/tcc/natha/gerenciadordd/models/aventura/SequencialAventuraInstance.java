package com.tcc.natha.gerenciadordd.models.aventura;

/**
 * Created by rafae on 03/11/2017.
 */

public class SequencialAventuraInstance {
    private static final SequencialAventuraInstance ourInstance = new SequencialAventuraInstance();

    public static SequencialAventuraInstance getInstance() {
        return ourInstance;
    }

    private SequencialAventuraInstance() {
    }

    public SequencialAventura getSequencialAventura() {
        return sequencialAventura;
    }

    public void setSequencialAventura(SequencialAventura sequencialAventura) {
        this.sequencialAventura = sequencialAventura;
    }

    private SequencialAventura sequencialAventura;
}
