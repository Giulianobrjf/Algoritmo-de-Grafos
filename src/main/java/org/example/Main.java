package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner criagrafo = new Scanner(System.in);
        System.out.print("Digite um para criar um grafo ou qualquer outra tecla para sair do menu: ");
        int valor = criagrafo.nextInt();
        if (valor == 1) {
        } else {
            System.out.print("Adeus! ");
            System.exit(1406);
        }
        System.out.print("Digite true para o grafo ser orientado ou false tecla para não orientado:");
        boolean orient = criagrafo.nextBoolean();

        System.out.print("Digite a quantidade de vertices que você quer:");
        int quantverti = criagrafo.nextInt();


        Grafo g = new Grafo(orient);

        for (int i = 1; i <= quantverti; i++) {

            System.out.print("Qual o nome do " + i + "° vertice?:");
            String nomevert = criagrafo.next();

            g.addVertice(new Vertice(nomevert));
        }
        System.out.print("Digite a quantidade de arestas que você quer:");

        int quantarest = criagrafo.nextInt();

        for (int i = 1; i <= quantarest; i++) {
            System.out.print("Qual o vertice de entrada da " + i + "° aresta?:");
            String vertentrada = criagrafo.next();

            System.out.print("Qual o vertice de saída da " + i + "° aresta?:");
            String vertsaida = criagrafo.next();

            System.out.print("Qual o peso da " + i + "° aresta?:");
            double pesovar = criagrafo.nextDouble();
            g.addAresta(new Aresta(vertentrada, vertsaida, pesovar));

        }
        int opcao;
        do {
            g.menu();
            opcao = criagrafo.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Escolha um vertice para dizermos seu grau: ");
                    String grauvert = criagrafo.next();
                    System.out.println("O grau do vertice " + grauvert + " é: " + g.getGrauvertice(grauvert) + "\n");
                }
                case 2 -> System.out.println("A ordem do Grafo é: "+g.getOrdem());

                case 3 ->  {
                            int[][] matrix = g.mostraMatriz();
                            for (int i = 0; i < g.getOrdem(); i++) {
                            for (int j = 0; j < g.getOrdem(); j++) {
                            System.out.print(matrix[i][j] + " ");
                           }
                             System.out.println();
                          }
                }
                case 4 -> {
                    System.out.print("Nome do arquivo: ");
                    String nome = criagrafo.next();
                    g.exporta(nome);
                }
                case 5 -> {
                    System.out.print("Qual o caminho do arquivo:");
                    String caminho = criagrafo.next();
                    g = g.importa(caminho);
                }
                case 6 -> {
                    if (!g.verificasimplicidade()) {
                        System.out.print("Este grafo é simples\n");
                    }else{
                        System.out.print("Este grafo não é simples \n");
                    }
                }
                case 7 -> {
                    if (!g.verificaregularidade()) {
                        System.out.print("Este grafo é regular\n");
                    }else{
                        System.out.print("Este grafo não é regular \n");
                    }
                }
                case 8-> {
                    if (!g.verificacompletude()) {
                        System.out.print("Este grafo é completo\n");
                    }else{
                        System.out.print("Este grafo não é completo \n");
                    }

                }
                case 9-> {

                    System.out.print("Qual o vertice de entrada:");
                    String caminhoentrada = criagrafo.next();
                    System.out.print("Qual o vertice de saída:");
                    String caminhosaida = criagrafo.next();
                    if (g.finalizacaminho(caminhoentrada,caminhosaida)) {
                        System.out.print("Este Vertice tem caminho\n");
                    }else{
                        System.out.print("Este Vertice não tem caminho\n");
                    }

                }
                case 10 ->{
                    System.out.println("O sumidouro é:\n "+ g.verificasumidouro());
                }
                case 11 ->{
                    System.out.println("A fonte é:\n "+ g.verificafonte());
                }
                case 12 ->{
                    System.out.print("Qual o vertice você quer a lista?\n ");
                    String transitivodireto = criagrafo.next();
                    System.out.println("Transitivo direto é: \n"+ g.verificatransitivodireto(transitivodireto));

                }
                case 13 ->{
                System.out.print("Qual o vertice você quer a lista?\n ");
                String transitivoindireto = criagrafo.next();
                System.out.println("Transitivo Indireto é: "+ g.verificatransitivoindireto(transitivoindireto));
                }
                case 14 ->{
                    if (g.finalizaConexao()) {
                        System.out.print("Esse Grafo é conexo\n");
                    }else{
                        System.out.print("Esse Grafo não é conexo\n");
                    }
                }
                case 15 ->{
                    System.out.print("Qual o vertice você quer começar?\n ");
                    String vertice = criagrafo.next();
                    g.printDijkstra(vertice);

                }
                default -> System.exit(1406);
            }

        }while (opcao != 0);

    }


}
