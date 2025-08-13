package com.emakers.api_biblioteca.Enums;

public enum LivroCategoria {
    Ação("ação"),
    Ficção("ficção"),
    Romance("romance"),
    Infantil("infantil"),
    Terror("terror");

    private String categoria;


    LivroCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getCategoria(){
        return categoria;
    }
}

