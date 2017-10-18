package com.tcc.natha.gerenciadordd.models.personagem;

/**
 * Created by natha on 01/02/2017.
 */

public class Personagem {

    private String userID;

    private String nomePerso;

    private String raca;

    private String subRaca;

    private String classe;

    private String nivel;

    private String antecedente;

    private String tendencia;

    private String classeArmad;

    private String iniciativa;

    private String desloc;

    private String jogador;

    private String xp;

    private String pvTotal;

    private String pvAtual;

    private String pvTemp;

    private String forca;

    private String forca2;

    private String destreza;

    private String destreza2;

    private String constituicao;

    private String constituicao2;

    private String inteligencia;

    private String inteligencia2;

    private String sabedoria;

    private String sabedoria2;

    private String carisma;

    private String carisma2;

    private String resForca;

    private boolean resForcaBool;

    private String resSabedoria;

    private boolean resSabedoriaBool;

    private String resConstituicao;

    private boolean resConstituicaoBool;

    private String resCarisma;

    private boolean resCarismaBool;

    private String resDestreza;

    private boolean resDestrezaBool;

    private String resInteligencia;

    private boolean resInteligenciaBool;

    private String acrobacia;

    private boolean acrobaciaBool;

    private String arcanismo;

    private boolean arcanismoBool;

    private String atletismo;

    private boolean atletismoBool;

    private String atuacao;

    private boolean atuacaoBool;

    private String blefar;

    private boolean blefarBool;

    private String furtividade;

    private boolean furtividadeBool;

    private String historia;

    private boolean historiaBool;

    private String intimidacao;

    private boolean intimidacaoBool;

    private String intuicao;

    private boolean intuicaoBool;

    private String investigacao;

    private boolean investigacaoBool;

    private String lidarAnimais;

    private boolean lidarAnimaisBool;

    private String medicina;

    private boolean medicinaBool;

    private String natureza;

    private boolean naturezaBool;

    private String percepcao;

    private boolean percepcaoBool;

    private String predestinacao;

    private boolean predestinacaoBool;

    private String persuasao;

    private boolean persuasaoBool;

    private String religiao;

    private boolean religiaoBool;

    private String sobrevivencia;

    private boolean sobrevivenciaBool;


    public Personagem(){
    }

    public Personagem(String userID){
        this.userID = userID;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        this.subRaca = subRaca;
    }

    public String getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(String antecedente) {
        this.antecedente = antecedente;
    }

    public String getTendencia() {
        return tendencia;
    }

    public void setTendencia(String tendencia) {
        this.tendencia = tendencia;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getPvTotal() {
        return pvTotal;
    }

    public void setPvTotal(String pvTotal) {
        this.pvTotal = pvTotal;
    }

    public String getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(String iniciativa) {
        this.iniciativa = iniciativa;
    }

    public String getClasseArmad() {
        return classeArmad;
    }

    public void setClasseArmad(String classeArmad) {
        this.classeArmad = classeArmad;
    }

    public String getDesloc() {
        return desloc;
    }

    public void setDesloc(String desloc) {
        this.desloc = desloc;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }

    public String getPvAtual() {
        return pvAtual;
    }

    public void setPvAtual(String pvAtual) {
        this.pvAtual = pvAtual;
    }

    public String getPvTemp() {
        return pvTemp;
    }

    public void setPvTemp(String pvTemp) {
        this.pvTemp = pvTemp;
    }

    public String getForca() {
        return forca;
    }

    public void setForca(String forca) {
        this.forca = forca;
    }

    public String getDestreza() {
        return destreza;
    }

    public void setDestreza(String destreza) {
        this.destreza = destreza;
    }

    public String getConstituicao() {
        return constituicao;
    }

    public void setConstituicao(String constituicao) {
        this.constituicao = constituicao;
    }

    public String getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(String inteligencia) {
        this.inteligencia = inteligencia;
    }

    public String getSabedoria() {
        return sabedoria;
    }

    public void setSabedoria(String sabedoria) {
        this.sabedoria = sabedoria;
    }

    public String getCarisma() {
        return carisma;
    }

    public void setCarisma(String carisma) {
        this.carisma = carisma;
    }

    public String getForca2() {
        return forca2;
    }

    public void setForca2(String forca2) {
        this.forca2 = forca2;
    }

    public String getDestreza2() {
        return destreza2;
    }

    public void setDestreza2(String destreza2) {
        this.destreza2 = destreza2;
    }

    public String getConstituicao2() {
        return constituicao2;
    }

    public void setConstituicao2(String constituicao2) {
        this.constituicao2 = constituicao2;
    }

    public String getInteligencia2() {
        return inteligencia2;
    }

    public void setInteligencia2(String inteligencia2) {
        this.inteligencia2 = inteligencia2;
    }

    public String getSabedoria2() {
        return sabedoria2;
    }

    public void setSabedoria2(String sabedoria2) {
        this.sabedoria2 = sabedoria2;
    }

    public String getCarisma2() {
        return carisma2;
    }

    public void setCarisma2(String carisma2) {
        this.carisma2 = carisma2;
    }

    public String getResForca() {
        return resForca;
    }

    public void setResForca(String resForca) {
        this.resForca = resForca;
    }

    public String getResSabedoria() {
        return resSabedoria;
    }

    public void setResSabedoria(String resSabedoria) {
        this.resSabedoria = resSabedoria;
    }

    public String getResConstituicao() {
        return resConstituicao;
    }

    public void setResConstituicao(String resConstituicao) {
        this.resConstituicao = resConstituicao;
    }

    public String getResCarisma() {
        return resCarisma;
    }

    public void setResCarisma(String resCarisma) {
        this.resCarisma = resCarisma;
    }

    public String getResDestreza() {
        return resDestreza;
    }

    public void setResDestreza(String resDestreza) {
        this.resDestreza = resDestreza;
    }

    public String getResInteligencia() {
        return resInteligencia;
    }

    public void setResInteligencia(String resInteligencia) {
        this.resInteligencia = resInteligencia;
    }

    public String getAcrobacia() { return acrobacia; }

    public void setAcrobacia(String acrobacia) {
        this.acrobacia = acrobacia;
    }

    public String getArcanismo() {
        return arcanismo;
    }

    public void setArcanismo(String arcanismo) {
        this.arcanismo = arcanismo;
    }

    public String getAtletismo() {
        return atletismo;
    }

    public void setAtletismo(String atletismo) {
        this.atletismo = atletismo;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }

    public String getBlefar() {
        return blefar;
    }

    public void setBlefar(String blefar) {
        this.blefar = blefar;
    }

    public String getFurtividade() {
        return furtividade;
    }

    public void setFurtividade(String furtividade) {
        this.furtividade = furtividade;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getIntimidacao() {
        return intimidacao;
    }

    public void setIntimidacao(String intimidacao) {
        this.intimidacao = intimidacao;
    }

    public String getIntuicao() {
        return intuicao;
    }

    public void setIntuicao(String intuicao) {
        this.intuicao = intuicao;
    }

    public String getInvestigacao() {
        return investigacao;
    }

    public void setInvestigacao(String investigacao) {
        this.investigacao = investigacao;
    }

    public String getLidarAnimais() {
        return lidarAnimais;
    }

    public void setLidarAnimais(String lidarAnimais) {
        this.lidarAnimais = lidarAnimais;
    }

    public String getMedicina() {
        return medicina;
    }

    public void setMedicina(String medicina) {
        this.medicina = medicina;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getPercepcao() {
        return percepcao;
    }

    public void setPercepcao(String percepcao) {
        this.percepcao = percepcao;
    }

    public String getPredestinacao() {
        return predestinacao;
    }

    public void setPredestinacao(String predestinacao) {
        this.predestinacao = predestinacao;
    }

    public String getPersuasao() {
        return persuasao;
    }

    public void setPersuasao(String persuasao) {
        this.persuasao = persuasao;
    }

    public String getReligiao() {
        return religiao;
    }

    public void setReligiao(String religiao) {
        this.religiao = religiao;
    }

    public String getSobrevivencia() {
        return sobrevivencia;
    }

    public void setSobrevivencia(String sobrevivencia) {
        this.sobrevivencia = sobrevivencia;
    }

    public Boolean isResForcaBool() {
        return resForcaBool;
    }

    public void setResForcaBool(Boolean resForcaBool) {
        this.resForcaBool = resForcaBool;
    }

    public Boolean isResCarismaBool() {
        return resCarismaBool;
    }

    public void setResCarismaBool(Boolean resCarismaBool) {
        this.resCarismaBool = resCarismaBool;
    }

    public Boolean isResConstituicaoBool() {
        return resConstituicaoBool;
    }

    public void setResConstituicaoBool(Boolean resConstituicaoBool) {
        this.resConstituicaoBool = resConstituicaoBool;
    }

    public Boolean isResDestrezaBool() {
        return resDestrezaBool;
    }

    public void setResDestrezaBool(Boolean resDestrezaBool) {
        this.resDestrezaBool = resDestrezaBool;
    }

    public Boolean isResInteligenciaBool() {
        return resInteligenciaBool;
    }

    public void setResInteligenciaBool(Boolean resInteligenciaBool) {
        this.resInteligenciaBool = resInteligenciaBool;
    }

    public Boolean isResSabedoriaBool() {
        return resSabedoriaBool;
    }

    public void setResSabedoriaBool(Boolean resSabedoriaBool) {
        this.resSabedoriaBool = resSabedoriaBool;
    }

    public boolean isAcrobaciaBool() {
        return acrobaciaBool;
    }

    public void setAcrobaciaBool(boolean acrobaciaBool) {
        this.acrobaciaBool = acrobaciaBool;
    }

    public boolean isArcanismoBool() {
        return arcanismoBool;
    }

    public void setArcanismoBool(boolean arcanismoBool) {
        this.arcanismoBool = arcanismoBool;
    }

    public boolean isAtletismoBool() {
        return atletismoBool;
    }

    public void setAtletismoBool(boolean atletismoBool) {
        this.atletismoBool = atletismoBool;
    }

    public boolean isAtuacaoBool() {
        return atuacaoBool;
    }

    public void setAtuacaoBool(boolean atuacaoBool) {
        this.atuacaoBool = atuacaoBool;
    }

    public boolean isBlefarBool() {
        return blefarBool;
    }

    public void setBlefarBool(boolean blefarBool) {
        this.blefarBool = blefarBool;
    }

    public boolean isFurtividadeBool() {
        return furtividadeBool;
    }

    public void setFurtividadeBool(boolean furtividadeBool) {
        this.furtividadeBool = furtividadeBool;
    }

    public boolean isHistoriaBool() {
        return historiaBool;
    }

    public void setHistoriaBool(boolean historiaBool) {
        this.historiaBool = historiaBool;
    }

    public boolean isIntimidacaoBool() {
        return intimidacaoBool;
    }

    public void setIntimidacaoBool(boolean intimidacaoBool) {
        this.intimidacaoBool = intimidacaoBool;
    }

    public boolean isIntuicaoBool() {
        return intuicaoBool;
    }

    public void setIntuicaoBool(boolean intuicaoBool) {
        this.intuicaoBool = intuicaoBool;
    }

    public boolean isInvestigacaoBool() {
        return investigacaoBool;
    }

    public void setInvestigacaoBool(boolean investigacaoBool) {
        this.investigacaoBool = investigacaoBool;
    }

    public boolean isLidarAnimaisBool() {
        return lidarAnimaisBool;
    }

    public void setLidarAnimaisBool(boolean lidarAnimaisBool) {
        this.lidarAnimaisBool = lidarAnimaisBool;
    }

    public boolean isMedicinaBool() {
        return medicinaBool;
    }

    public void setMedicinaBool(boolean medicinaBool) {
        this.medicinaBool = medicinaBool;
    }

    public boolean isNaturezaBool() {
        return naturezaBool;
    }

    public void setNaturezaBool(boolean naturezaBool) {
        this.naturezaBool = naturezaBool;
    }

    public boolean isPercepcaoBool() {
        return percepcaoBool;
    }

    public void setPercepcaoBool(boolean percepcaoBool) {
        this.percepcaoBool = percepcaoBool;
    }

    public boolean isPredestinacaoBool() {
        return predestinacaoBool;
    }

    public void setPredestinacaoBool(boolean predestinacaoBool) {
        this.predestinacaoBool = predestinacaoBool;
    }

    public boolean isPersuasaoBool() {
        return persuasaoBool;
    }

    public void setPersuasaoBool(boolean persuasaoBool) {
        this.persuasaoBool = persuasaoBool;
    }

    public boolean isReligiaoBool() {
        return religiaoBool;
    }

    public void setReligiaoBool(boolean religiaoBool) {
        this.religiaoBool = religiaoBool;
    }

    public boolean isSobrevivenciaBool() {
        return sobrevivenciaBool;
    }

    public void setSobrevivenciaBool(boolean sobrevivenciaBool) {
        this.sobrevivenciaBool = sobrevivenciaBool;
    }
}

