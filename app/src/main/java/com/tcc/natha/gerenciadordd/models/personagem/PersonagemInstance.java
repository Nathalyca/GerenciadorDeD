package com.tcc.natha.gerenciadordd.models.personagem;

/**
 * Created by rafae on 31/10/2017.
 */

public class PersonagemInstance {
    private static final PersonagemInstance ourInstance = new PersonagemInstance();

    public static PersonagemInstance getInstance() {
        return ourInstance;
    }

    private PersonagemInstance() {
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    private Personagem personagem;

}
