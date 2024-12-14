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

    public static Status encontrarPelaDescricao(char descricaoFormatada) {
        for (Status status : Status.values()) {
            if (status.getDescricaoFormatada() == descricaoFormatada) {
                return status;
            }
        }

        throw new IllegalArgumentException("Status inexistente: " + descricaoFormatada + ", informar A para ativo ou I para inativo");
    }

    public int getCodigo() {
        return codigo;
    }

    public char getDescricaoFormatada() {
        return descricaoFormatada;
    }
}
