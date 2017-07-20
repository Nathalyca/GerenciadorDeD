package com.tcc.natha.gerenciadordd.models;

/**
 * Created by natha on 01/02/2017.
 */

public class Personagem {

    private String uID;

    private String personagem;

    private String classe;

    private String raca;

    private String SubRaca;

    private String Antecedente;

    private String Tendencia;

    private String Nivel;

    private String Pv;

    private String Iniciativa;

    private String Forca;

    private String Destreza;

    private String Constituicao;

    private String Inteligencia;

    private String Sabedoria;

    private String Carisma;

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

    public String getPersonagem() {
        return personagem;
    }

    public void setPersonagem(String personagem) {
        this.personagem = personagem;
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
        return SubRaca;
    }

    public void setSubRaca(String subRaca) {
        SubRaca = subRaca;
    }

    public String getAntecedente() {
        return Antecedente;
    }

    public void setAntecedente(String antecedente) {
        Antecedente = antecedente;
    }

    public String getTendencia() {
        return Tendencia;
    }

    public void setTendencia(String tendencia) {
        Tendencia = tendencia;
    }

    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String nivel) {
        Nivel = nivel;
    }

    public String getPv() {
        return Pv;
    }

    public void setPv(String pv) {
        Pv = pv;
    }

    public String getIniciativa() {
        return Iniciativa;
    }

    public void setIniciativa(String iniciativa) {
        Iniciativa = iniciativa;
    }

    public String getForca() {
        return Forca;
    }

    public void setForca(String forca) {
        Forca = forca;
    }

    public String getDestreza() {
        return Destreza;
    }

    public void setDestreza(String destreza) {
        Destreza = destreza;
    }

    public String getConstituicao() {
        return Constituicao;
    }

    public void setConstituicao(String constituicao) {
        Constituicao = constituicao;
    }

    public String getInteligencia() {
        return Inteligencia;
    }

    public void setInteligencia(String inteligencia) {
        Inteligencia = inteligencia;
    }

    public String getSabedoria() {
        return Sabedoria;
    }

    public void setSabedoria(String sabedoria) {
        Sabedoria = sabedoria;
    }

    public String getCarisma() {
        return Carisma;
    }

    public void setCarisma(String carisma) {
        Carisma = carisma;
    }
}
