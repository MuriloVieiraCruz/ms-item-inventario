package com.murilo.TesteFarolShopping.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ItemInventarioResponseDTO implements Serializable {

    private Long id;
    private String codigo;
    private String numeroSerie;
    private char disponibilidade;
    private char status;
    private String descricao;
    private String localizacao;
    private String dataMovimentacao;

    public ItemInventarioResponseDTO() {
    }

    public ItemInventarioResponseDTO(Long id, String codigo, String numeroSerie, char disponibilidade, char status, String descricao, String localizacao, String dataMovimentacao) {
        this.id = id;
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.disponibilidade = disponibilidade;
        this.status = status;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(String dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(char disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}