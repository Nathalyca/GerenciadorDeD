package com.tcc.natha.gerenciadordd.models;

/**
 * Created by natha on 22/07/2017.
 */

public class PersonagemItem {

        public String persoID;
        public String nomePerso;
        private String classe;
        private String nivel;

    public PersonagemItem() {
        this.persoID = "";
        this.nomePerso = "";
        this.classe = "";
        this.nivel = "";
    }

    public PersonagemItem( String persoID,String nomePerso, String classe, String nivel) {
            this.persoID = persoID;
            this.nomePerso = nomePerso;
            this.classe = classe;
            this.nivel = nivel;
        }

    public String getPersoID() {
        return persoID;
    }

    public void setPersoID(String persoID) {
        this.persoID = persoID;
    }

    public String getNomePerso() {
        return nomePerso;
    }

    public void setNomePerso(String nomePerso) {
        this.nomePerso = nomePerso;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

        @Override
        public String toString() {
            return nomePerso+"\n"+classe;
        }

}
