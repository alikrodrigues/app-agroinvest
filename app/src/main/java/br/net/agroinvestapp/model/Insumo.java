package br.net.agroinvestapp.model;

import br.com.rafael.jpdroid.annotations.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Entity
public class Insumo implements Serializable {


    private static final long serialVersionUID = 1031624177537342820L;
    @PrimaryKey
    @Column(name = "_id")
    private long _id;
    @Column
    private String descricao;
    @Column
    private String unidadeMedida;
    @Column
    private String valorCanoinhas;

    @Column
    private String categoria;

    @Column
    private String valorChapeco;

    @Column
    private String valorJaragua;

    @Column
    private String valorJoacaba;

    @Column
    private String valorLages;

    @Column
    private String valorRioSul;

    @Column
    private String valorSulCatarinense;

    @Column
    private String valorSaoMiguelOeste;


    @Ignorable
    @JsonIgnore
    private int checado;

    @Column
    private String periodo;

    @ForeignKey(joinEntity=Orcamento.class,joinPrimaryKey="_id",deleteCascade=true)
    @Column
    private Long idOrcamento;


    public long get_Id() {
        return _id;
    }

    public void set_Id(long _id) {
        this._id = _id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getValorCanoinhas() {
        return valorCanoinhas;
    }

    public void setValorCanoinhas(String valorCanoinhas) {
        this.valorCanoinhas = valorCanoinhas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValorChapeco() {
        return valorChapeco;
    }

    public void setValorChapeco(String valorChapeco) {
        this.valorChapeco = valorChapeco;
    }

    public String getValorJaragua() {
        return valorJaragua;
    }

    public void setValorJaragua(String valorJaragua) {
        this.valorJaragua = valorJaragua;
    }

    public String getValorJoacaba() {
        return valorJoacaba;
    }

    public void setValorJoacaba(String valorJoacaba) {
        this.valorJoacaba = valorJoacaba;
    }

    public String getValorLages() {
        return valorLages;
    }

    public void setValorLages(String valorLages) {
        this.valorLages = valorLages;
    }

    public String getValorRioSul() {
        return valorRioSul;
    }

    public void setValorRioSul(String valorRioSul) {
        this.valorRioSul = valorRioSul;
    }

    public String getValorSulCatarinense() {
        return valorSulCatarinense;
    }

    public void setValorSulCatarinense(String valorSulCatarinense) {
        this.valorSulCatarinense = valorSulCatarinense;
    }

    public String getValorSaoMiguelO() {
        return valorSaoMiguelOeste;
    }

    public void setValorSaoMiguelO(String valorSaoMiguelOeste) {
        this.valorSaoMiguelOeste = valorSaoMiguelOeste;
    }

    @JsonIgnore
    public Long getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(Long idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public int getChecado() {
        return checado;
    }

    public void setChecado(int checado) {
        this.checado = checado;
    }

    @Override
    public String toString() {
        return "Insumo{" +
                "_id=" + _id +
                ", descricao='" + descricao + '\'' +
                ", unidadeMedida='" + unidadeMedida + '\'' +
                ", valorCanoinhas='" + valorCanoinhas + '\'' +
                ", categoria='" + categoria + '\'' +
                ", valorChapeco='" + valorChapeco + '\'' +
                ", valorJaragua='" + valorJaragua + '\'' +
                ", valorJoacaba='" + valorJoacaba + '\'' +
                ", valorLages='" + valorLages + '\'' +
                ", valorRioSul='" + valorRioSul + '\'' +
                ", valorSulCatarinense='" + valorSulCatarinense + '\'' +
                ", valorSaoMiguelOeste='" + valorSaoMiguelOeste + '\'' +
                ", checado=" + checado +
                ", periodo='" + periodo + '\'' +
                ", idOrcamento=" + idOrcamento +
                '}';
    }
}
