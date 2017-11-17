package br.net.agroinvestapp.model;

import br.com.rafael.jpdroid.annotations.Column;
import br.com.rafael.jpdroid.annotations.Entity;
import br.com.rafael.jpdroid.annotations.PrimaryKey;
import br.com.rafael.jpdroid.annotations.RelationClass;
import br.com.rafael.jpdroid.enums.RelationType;

import java.util.List;

@Entity
public class Orcamento {

    @PrimaryKey
    @Column
    private Long _id;

    @Column
    private String descricao;

    @Column private String data;

    @Column
    private String parametro;

    @Column
    private int concluido;

    @RelationClass(relationType= RelationType.ManyToOne,joinColumn="idOrcamento")
    private List<Insumo> insumos;


    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int isConcluido() {
        return concluido;
    }

    public void setConcluido(int concluido) {
        this.concluido = concluido;
    }

    public List<Insumo> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Insumo> insumos) {
        this.insumos = insumos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    @Override
    public String toString() {
        return "Orcamento{" +
                "_id=" + _id +
                ", descricao='" + descricao + '\'' +
                ", data='" + data + '\'' +
                ", parametro='" + parametro + '\'' +
                ", concluido=" + concluido +
                ", activity_insumos=" + insumos +
                '}';
    }
}
