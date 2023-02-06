package org.example;

public class Vertice {
    String nome;
    int grau;
    int indice;
    public Vertice(String nome) {
        this.nome = nome;
        this.grau = 0;
    }


    public int getGrau() {
        return grau;
    }
    public int getindice() {
        return indice;
    }
    public void setindice(int indice){
        this.indice = indice;
    }
    public void setGrau(){
        this.grau++;
    }
    public String getNome() {
        return nome;
    }
}
