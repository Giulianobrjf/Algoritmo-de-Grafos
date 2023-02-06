package org.example;

import java.io.*;
import java.util.*;

public class Grafo {

    private Map<String, Vertice> nomevertice;
    private int[][] matrizadj;
    int quantidadevertice;
    boolean orientado;
    private ArrayList<Vertice> listaVertice;
    private ArrayList<Aresta> listaAresta;

    public Grafo(boolean orientado) {
        this.orientado = orientado;
        this.quantidadevertice = 0;
        this.listaAresta = new ArrayList<>();
        this.listaVertice = new ArrayList<>();
        this.nomevertice = new HashMap<>();
    }

    private void atualizamatriz(Aresta aresta) {

        this.matrizadj[this.nomevertice.get(aresta.getVertice_entrada()).getindice()]
                [this.nomevertice.get(aresta.getVertice_saida()).getindice()] += 1;
        if (!orientado) {
            this.matrizadj[this.nomevertice.get(aresta.getVertice_saida()).getindice()]
                    [this.nomevertice.get(aresta.getVertice_entrada()).getindice()] += 1;
        }
    }

    public void iniciamatriz() {
        this.matrizadj = new int[quantidadevertice][quantidadevertice];
        for (int i = 0; i < quantidadevertice; i++) {
            for (int j = 0; j < quantidadevertice; j++) {
                this.matrizadj[i][j] = 0;                                //Inicialização da matriz
            }
        }
    }

    public int[][] mostraMatriz() {
        this.iniciamatriz();
        int i = 0;
        for (Vertice v : nomevertice.values()) {
            v.setindice(i);
            i++;

        }
        ;
        for (Aresta a : listaAresta) {
            this.atualizamatriz(a);
        }
        ;
        return this.matrizadj;

    }


    public void addAresta(Aresta a) {
        this.listaAresta.add(a);
        this.nomevertice.get(a.getVertice_entrada()).setGrau();
        this.nomevertice.get(a.getVertice_saida()).setGrau();
    }

    public void addVertice(Vertice v) {
        if (!nomevertice.containsKey(v.getNome())) {
            this.listaVertice.add(v);
            this.nomevertice.put(v.getNome(), v);
            this.quantidadevertice++;

        }
    }

    public int getOrdem() {
        return quantidadevertice;
    }

    public int getGrauvertice(String vertice) {
        return this.nomevertice.get(vertice).getGrau();
    }

    public static void menu() {
        System.out.println("Menu");
        System.out.println("\t 0. Sair");
        System.out.println("\t 1. Grau");
        System.out.println("\t 2. Ordem");
        System.out.println("\t 3. Matriz");
        System.out.println("\t 4. Exportar Grafo");
        System.out.println("\t 5. Importar Grafo");
        System.out.println("\t 6. Simplicidade do Grafo");
        System.out.println("\t 7. Regularidade do Grafo");
        System.out.println("\t 8. Completude do Grafo");
        System.out.println("\t 9. Caminho de um Vertice");
        System.out.println("\t 10. Sumidouro");
        System.out.println("\t 11. Fonte");
        System.out.println("\t 12. Transitivo Direto");
        System.out.println("\t 13. Transitivo Indireto");
        System.out.println("\t 14. Conexão");
        System.out.println("\t 15. Dijkstra");

        System.out.println("Opcao: ");
    }


    public boolean verificasimplicidade(){
        mostraMatriz();
        for (int i = 0; i<this.quantidadevertice; i++){
            for (int j = 0; j<this.quantidadevertice; i++){
                if (orientado){
                    if(this.matrizadj[i][j] + this.matrizadj[j][i] > 1){
                        return true;
                    }
                }
                if (this.matrizadj[i][j]>1){
                    return true;
                }
            }
        }
        for (Aresta a : listaAresta) {
            if ((Objects.equals(a.getVertice_saida(), a.getVertice_entrada()))){
                return true;
            }
        }
        return false;
    }
    public boolean verificaregularidade(){
        for (int i=0; i<listaVertice.size(); i++) {
                if (i != 0){
                    if(listaVertice.get(i).getGrau() != listaVertice.get(i-1).getGrau()){
                         return true;
                    }
                }
        }
        return false;
    }
    public boolean verificacompletude(){
        for (int i=0; i<listaVertice.size(); i++) {
                if(listaVertice.get(i).getGrau() != quantidadevertice-1){
                    return true;
                }
        }
        return false;
    }
    public boolean finalizacaminho(String vertice_entrada,String vertice_saida){

        boolean[] vertvisitado = new boolean[quantidadevertice];
        verificacaminho(nomevertice.get(vertice_entrada).getindice(),vertvisitado);
        if (vertvisitado[nomevertice.get(vertice_saida).getindice()]){
            return true;
        }
        return false;
    }
    public void verificacaminho(int vertice_entrada,boolean[] vertice_visitado){
        int matriz[][] = mostraMatriz();
        if (vertice_visitado[vertice_entrada]){
            return;
        }else {
            vertice_visitado[vertice_entrada] = true;
        }
        for (int i =0; i<matriz[vertice_entrada].length; i++) {
            if (matriz[vertice_entrada][i] == 1){
                verificacaminho(i,vertice_visitado);
            }
        }
    }
    public List<String> verificasumidouro(){
        ArrayList<String> listaSumidouro = new ArrayList<>();
        mostraMatriz();
        int soma = 0;
        if(orientado) {
            for (int i = 0; i < quantidadevertice; i++) {
                for (int j = 0; j < quantidadevertice; j++) {
                    soma = soma + this.matrizadj[i][j];
                }
                if(soma == 0){
                    listaSumidouro.add(listaVertice.get(i).getNome());
                }
                soma = 0;
            }

        }
        return listaSumidouro;
    }
    public List<String> verificafonte(){
        ArrayList<String> listaFonte = new ArrayList<>();
        mostraMatriz();
        int soma = 0;
        if(orientado) {

            for (int j = 0; j < quantidadevertice; j++) {
                for (int i = 0; i < quantidadevertice; i++) {
                    soma = soma + this.matrizadj[i][j];

                }
                if(soma == 0){
                    listaFonte.add(listaVertice.get(j).getNome());

                }
                soma=0;
            }


        }
        return listaFonte;
    }
    public List<String> verificatransitivodireto(String vertice) {
        mostraMatriz();
        boolean[] vertvisitado = new boolean[quantidadevertice];
        ArrayList<String> listaTransitivoDireto = new ArrayList<>();
        verificacaminho(nomevertice.get(vertice).getindice(),vertvisitado);
        for (int i=0; i<quantidadevertice; i++) {
            if(vertvisitado[i]){
                listaTransitivoDireto.add(listaVertice.get(i).getNome());
            }
        }

        return listaTransitivoDireto;
    }
    public List<String> verificatransitivoindireto(String vertice) {
        mostraMatriz();
        ArrayList<String> listaTransitivoIndireto = new ArrayList<>();
        for (int i=0; i<quantidadevertice; i++) {
            if(finalizacaminho(listaVertice.get(i).getNome(),vertice)){
                listaTransitivoIndireto.add(listaVertice.get(i).getNome());


            }
        }
        return listaTransitivoIndireto;
    }

    public boolean finalizaConexao(){
        mostraMatriz();
        boolean[] vertvisitado = new boolean[quantidadevertice];
        verificaConexao(0,vertvisitado);
        for (int i=0; i<quantidadevertice; i++){
               if(!vertvisitado[i]){
                   return false;
               }
        }
        return true;
    }
    public void verificaConexao(int vertice_entrada,boolean[] vertice_visitado){
        int matriz[][] = mostraMatriz();
        if (vertice_visitado[vertice_entrada]){
            return;
        }else {
            vertice_visitado[vertice_entrada] = true;
        }
        for (int i =0; i<matriz[vertice_entrada].length; i++) {
            if (matriz[vertice_entrada][i] == 1){
                verificaConexao(i,vertice_visitado);
            }
            if (matriz[i][vertice_entrada] == 1){
                verificaConexao(i,vertice_visitado);
            }
        }
    }

    public void printDijkstra(String vertice){
        double[] lista = this.getDijkstra(vertice);
        for (int i = 0; i < this.quantidadevertice; i++){
            System.out.println(this.listaVertice.get(i).getNome() + " - " + lista[i]);
        }
    }

    public double[] getDijkstra(String vertice) {
        this.mostraMatriz();
        double[] listaDijkstra = new double[this.quantidadevertice];
        boolean[] vertice_visitado = new boolean[this.quantidadevertice];
        for (int i = 0; i < this.quantidadevertice; i++) {
            listaDijkstra[i] = Integer.MAX_VALUE;
        }
        listaDijkstra[nomevertice.get(vertice).getindice()] = 0;
        this.verificacaminho(nomevertice.get(vertice).getindice(), vertice_visitado, listaDijkstra);
        return listaDijkstra;
    }

    public void verificacaminho(int vertice_entrada, boolean[] vertice_visitado, double[] listaDijkstra){
        double minValor = Integer.MAX_VALUE;
        int minValorVertice = 0;

        if (vertice_visitado[vertice_entrada]){
            return;
        }
        else{
            vertice_visitado[vertice_entrada] = true;
        }

        for (int i = 0; i < this.quantidadevertice; i++){
            if (this.mostraMatriz()[vertice_entrada][i] >= 1){
                for (Aresta a: listaAresta) {
                    if (Objects.equals(a.getVertice_entrada() + a.getVertice_saida(), listaVertice.get(vertice_entrada).getNome() +
                            listaVertice.get(i).getNome())){
                        if (listaDijkstra[i] > a.peso + listaDijkstra[vertice_entrada]){
                            listaDijkstra[i] = a.peso + listaDijkstra[vertice_entrada];
                        }
                    }
                }
            }
            if (listaDijkstra[i] < minValor && !vertice_visitado[i]){
                minValor = listaDijkstra[i];
                minValorVertice = i;
            }
        }
        this.verificacaminho(minValorVertice, vertice_visitado, listaDijkstra);
    }

    public void exporta(String nome) throws IOException {
        File Grafoarqui = new File("C:/Users/giuli/Desktop/Sistemas de Informação/Grafos/grafogiu/src/main/java/org/example/" + nome + ".dot");

        if (!Grafoarqui.exists()) {
            Grafoarqui.createNewFile();
            FileWriter fw = new FileWriter(Grafoarqui, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (orientado) {
                bw.write("digraph {");
                bw.newLine();
                for (Vertice v : listaVertice) {
                    if (v.getGrau() == 0) {
                        bw.write("  " + v.getNome() + " ;");
                        bw.newLine();
                    }
                    ;
                }
                for (Aresta a : listaAresta) {
                    bw.write("  " + a.getVertice_entrada() + " ->1 " + a.getVertice_saida() + " [label=\"" + a.peso + "\"];");
                    bw.newLine();
                }
                bw.write(" }");
            } else {
                bw.write("graph {");
                bw.newLine();
                for (Vertice v : listaVertice) {
                    if (v.getGrau() == 0) {
                        bw.write("  " + v.getNome() + " ;");
                        bw.newLine();
                    }
                    ;
                }
                for (Aresta a : listaAresta) {
                    bw.write("  " + a.getVertice_entrada() + " -- " + a.getVertice_saida() + " [label=\"" + a.peso + "\"];");
                    bw.newLine();
                }
                bw.write(" }");


            }
            bw.close();
            fw.close();
        }
    }
    public Grafo importa(String diretorio) throws IOException {
        Grafo g = new Grafo(true);
        diretorio = diretorio.replaceAll("\\\\", "/");
        File Grafodir = new File(diretorio);
        FileReader fr = new FileReader(Grafodir);
        BufferedReader br = new BufferedReader(fr);
        String linha = br.readLine();
        if (!linha.contains("digraph")) {
            g = new Grafo(false);
            while (br.ready()) {
                linha = br.readLine();
                if (linha.contains("}")) {
                    return g;
                }
                if (!linha.contains("--")) {
                    linha = linha.replaceAll(";", " ");
                    g.addVertice(new Vertice(linha.trim()));

                } else {
                    String[] array = linha.split("--");
                    array[0] = array[0].trim();

                    g.addVertice(new Vertice(array[0]));
                    array[1] = array[1].trim();
                    g.addVertice(new Vertice(array[1].charAt(0) + ""));
                    if (linha.contains("label")) {
                        char[] chars = linha.toCharArray();
                        String guarda = "";
                        for (char c : chars) {
                            if (Character.isDigit(c) || c == '.') {
                                guarda = guarda + c;
                            }
                        }
                        float peso = Float.parseFloat(guarda);

                        g.addAresta(new Aresta(array[0], array[1].charAt(0) + "", peso));

                    } else {
                        double peso = 0;
                        g.addAresta(new Aresta(array[0], array[1].charAt(0) + "", peso));
                    }
                }
            }

        } else {
            g = new Grafo(true);
            while (br.ready()) {
                linha = br.readLine();
                if (!linha.contains("->")) {
                    linha = linha.replaceAll(";", " ");
                    g.addVertice(new Vertice(linha.trim()));
                } else {
                    String[] array = linha.split("->");
                    array[0] = array[0].trim();

                    g.addVertice(new Vertice(array[0]));
                    array[1] = array[1].trim();
                    g.addVertice(new Vertice(array[1].charAt(0) + ""));
                    if (linha.contains("label")) {
                        char[] chars = linha.toCharArray();
                        String guarda = "";
                        for (char c : chars) {
                            if (Character.isDigit(c) || c == '.') {
                                guarda = guarda + c;
                            }
                            float peso = Float.parseFloat(guarda);
                            ;
                            g.addAresta(new Aresta(array[0], array[1].charAt(0) + "", peso));
                        }
                    } else {
                        double peso = 0;
                        g.addAresta(new Aresta(array[0], array[1].charAt(0) + "", peso));
                    }

                }

            }
        }
        return g;
    }

}

