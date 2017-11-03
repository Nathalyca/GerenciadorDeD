package com.tcc.natha.gerenciadordd.models.aventura;

/**
 * Created by rafae on 03/11/2017.
 */

public class AventuraInstance {
    private static final AventuraInstance ourInstance = new AventuraInstance();

    public static AventuraInstance getInstance() {
        return ourInstance;
    }

    private AventuraInstance() {
    }

    public Aventura getAventura() {
        return aventura;
    }

    public void setAventura(Aventura aventura) {
        this.aventura = aventura;
    }

    private  Aventura aventura;
}
