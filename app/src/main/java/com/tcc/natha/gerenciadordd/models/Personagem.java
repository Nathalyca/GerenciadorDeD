package com.tcc.natha.gerenciadordd.models;

/**
 * Created by natha on 01/02/2017.
 */

public class Personagem {

    private String uID;

    private String nomePerso;

    private String classe;

    private String raca;

    private String subRaca;

    private String antecedente;

    private String tendencia;

    private String nivel;

    private String pv;

    private String iniciativa;

    private String forca;

    private String destreza;

    private String constituicao;

    private String inteligencia;

    private String sabedoria;

    private String carisma;

    public Personagem(){
    }

    public Personagem(String uID){
        this.uID = uID;

    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSubRaca() {
        return subRaca;
    }

    public void setSubRaca(String subRaca) {
        subRaca = subRaca;
    }

    public String getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(String antecedente) {
        antecedente = antecedente;
    }

    public String getTendencia() {
        return tendencia;
    }

    public void setTendencia(String tendencia) {
        tendencia = tendencia;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        nivel = nivel;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        pv = pv;
    }

    public String getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(String iniciativa) {
        iniciativa = iniciativa;
    }

    public String getForca() {
        return forca;
    }

    public void setForca(String forca) {
        forca = forca;
    }

    public String getDestreza() {
        return destreza;
    }

    public void setDestreza(String destreza) {
        destreza = destreza;
    }

    public String getConstituicao() {
        return constituicao;
    }

    public void setConstituicao(String constituicao) {
        constituicao = constituicao;
    }

    public String getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(String inteligencia) {
        inteligencia = inteligencia;
    }

    public String getSabedoria() {
        return sabedoria;
    }

    public void setSabedoria(String sabedoria) {
        sabedoria = sabedoria;
    }

    public String getCarisma() {
        return carisma;
    }

    public void setCarisma(String carisma) {
        carisma = carisma;
    }
}
