package br.net.agroinvestapp.model;

public class Preferencias {

    private String sitePreferido;
    private String emailPreferido;
    private String ultAtualizacao;

    public String getSitePreferido() {
        return sitePreferido;
    }

    public void setSitePreferido(String sitePreferido) {
        this.sitePreferido = sitePreferido;
    }

    public String getEmailPreferido() {
        return emailPreferido;
    }

    public void setEmailPreferido(String emailPreferido) {
        this.emailPreferido = emailPreferido;
    }

    public String getUltAtualizacao() {
        return ultAtualizacao;
    }

    public void setUltAtualizacao(String ultAtualizacao) {
        this.ultAtualizacao = ultAtualizacao;
    }
}
