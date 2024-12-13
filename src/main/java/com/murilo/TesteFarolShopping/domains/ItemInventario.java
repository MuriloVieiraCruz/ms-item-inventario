package com.murilo.TesteFarolShopping.domains;

import com.murilo.TesteFarolShopping.domains.enums.Disponibilidade;
import com.murilo.TesteFarolShopping.domains.enums.Status;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "tb_item_inventario")
@Entity
public class ItemInventario {

    private Long id;
    private String codigo;
    private Integer numeroSerie;
    private Disponibilidade disponibilidade;
    private Status status;
    private String descricao;
    private String localizacao;
    private LocalDateTime dataMovimentacao;

    public ItemInventario() {
    }

    public ItemInventario(Long id, String codigo, Integer numeroSerie, Disponibilidade disponibilidade, Status status, String descricao, String localizacao, LocalDateTime dataMovimentacao) {
        this.id = id;
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.disponibilidade = disponibilidade;
        this.status = status;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.dataMovimentacao = dataMovimentacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Disponibilidade disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemInventario)) return false;

        ItemInventario that = (ItemInventario) o;
        return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo) && Objects.equals(numeroSerie, that.numeroSerie) && disponibilidade == that.disponibilidade && status == that.status && Objects.equals(descricao, that.descricao) && Objects.equals(localizacao, that.localizacao) && Objects.equals(dataMovimentacao, that.dataMovimentacao);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(codigo);
        result = 31 * result + Objects.hashCode(numeroSerie);
        result = 31 * result + Objects.hashCode(disponibilidade);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(descricao);
        result = 31 * result + Objects.hashCode(localizacao);
        result = 31 * result + Objects.hashCode(dataMovimentacao);
        return result;
    }
}
