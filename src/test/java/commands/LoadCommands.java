package commands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class LoadCommands {

    public static void executarTesteCarga(Supplier<Integer> metodoTeste, int totalRequisicoes) {
        int limitePorExecucao = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10); 

        try {
            int quantidadeAtual = Math.min(limitePorExecucao, totalRequisicoes);
            System.out.println("Executando lote de " + quantidadeAtual + " requisições...");

            List<Future<Integer>> resultados = new ArrayList<>();

            for (int i = 0; i < quantidadeAtual; i++) {
                resultados.add(executor.submit(metodoTeste::get));
            }

            for (Future<Integer> resultado : resultados) {
                try {
                    int statusCode = resultado.get();
                    if (statusCode == 429) {
                        System.out.println("Limite de requisições atingido! (429 Too Many Requests)");
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar requisição: " + e.getMessage());
                }
            }

        } finally {
            executor.shutdown();
        }
    }
}