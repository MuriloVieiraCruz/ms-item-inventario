package com.murilo.TesteFarolShopping.domains.enums;

public enum Status {

    INATIVO(0, 'I'),
    ATIVO(1, 'A');

    private final int codigo;
    private final char descricaoFormatada;

    Status(int codigo, char descricaoFormatada) {
        this.codigo = codigo;
        this.descricaoFormatada = descricaoFormatada;
    }

    public static Disponibilidade encontrarPeloCodigo(int codigo) {
        for (Disponibilidade disponibilidade : Disponibilidade.values()) {
            if (disponibilidade.getCodigo() == codigo) {
                return disponibilidade;
            }
        }

        throw new IllegalArgumentException("Disponibilidade inexistente: " + codigo);
    }

    public int getCodigo() {
        return codigo;
    }

    public char getDescricaoFormatada() {
        return descricaoFormatada;
    }
}
