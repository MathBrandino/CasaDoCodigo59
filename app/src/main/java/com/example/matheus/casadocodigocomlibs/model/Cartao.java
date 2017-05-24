package com.example.matheus.casadocodigocomlibs.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;

/**
 * Created by matheusbrandino on 5/24/17.
 */

@Entity
public class Cartao implements Serializable {

    static final long serialVersionUID = 536871008;

    @Id
    private Long id;

    @NotNull
    private String nomeCompleto;
    @NotNull
    private Long numero;
    private Integer cvv;
    private String validade;


    @Generated(hash = 1431601988)
    public Cartao() {
    }

    @Generated(hash = 115823341)
    public Cartao(Long id, @NotNull String nomeCompleto, @NotNull Long numero,
                  Integer cvv, String validade) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.numero = numero;
        this.cvv = cvv;
        this.validade = validade;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
