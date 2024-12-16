package com.murilo.TesteFarolShopping.domains;

import com.murilo.TesteFarolShopping.domains.enums.Disponibilidade;
import com.murilo.TesteFarolShopping.domains.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "tb_item_inventario")
@Entity
public class ItemInventario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String codigo;
    private String numeroSerie;
    private Disponibilidade disponibilidade;
    private Status status;
    private String descricao;
    private String localizacao;
    private LocalDateTime dataMovimentacao;

    public ItemInventario() {
    }

    public ItemInventario(Long id, String codigo, String numeroSerie, Disponibilidade disponibilidade, Status status, String descricao, String localizacao, LocalDateTime dataMovimentacao) {
        this.id = id;
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.disponibilidade = disponibilidade;
        this.status = status;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.dataMovimentacao = dataMovimentacao;
    }

    public ItemInventario(Builder builder) {
        this.id = builder.id;
        this.codigo = builder.codigo;
        this.numeroSerie = builder.numeroSerie;
        this.disponibilidade = builder.disponibilidade;
        this.status = builder.status;
        this.descricao = builder.descricao;
        this.localizacao = builder.localizacao;
        this.dataMovimentacao = builder.dataMovimentacao;
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

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
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

    public static class Builder {
        private Long id;
        private String codigo;
        private String numeroSerie;
        private Disponibilidade disponibilidade;
        private Status status;
        private String descricao;
        private String localizacao;
        private LocalDateTime dataMovimentacao;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder numeroSerie(String numeroSerie) {
            this.numeroSerie = numeroSerie;
            return this;
        }

        public Builder disponibilidade(Disponibilidade disponibilidade) {
            this.disponibilidade = disponibilidade;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder localizacao(String localizacao) {
            this.localizacao = localizacao;
            return this;
        }

        public Builder dataMovimentacao(LocalDateTime dataMovimentacao) {
            this.dataMovimentacao = dataMovimentacao;
            return this;
        }

        public ItemInventario build() {
            return new ItemInventario(this);
        }
    }
}
