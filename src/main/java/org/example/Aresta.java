package org.example;

public class Aresta {
  private  String vertice_entrada;
  private   String vertice_saida;

    double peso;

    public Aresta(String vertice_entrada,String vertice_saida,double peso) {

        this.vertice_entrada=vertice_entrada;
        this.vertice_saida=vertice_saida;
        this.peso=peso;

    }
    public String getVertice_entrada() {
        return vertice_entrada;
    }
    public String getVertice_saida() {
        return vertice_saida;
    }
}
