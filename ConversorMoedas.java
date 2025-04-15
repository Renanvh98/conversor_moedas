import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.InputStreamReader;
import java.util.Scanner;

public class ConversorMoedas {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // Substitua pela URL da API

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu
        System.out.println("Selecione a conversão:");
        System.out.println("1. Dólar para Real");
        System.out.println("2. Real para Dólar");
        System.out.println("3. Euro para Real");
        System.out.println("4. Real para Euro");
        System.out.println("5. Libra para Real");
        System.out.println("6. Real para Libra");

        int opcao = scanner.nextInt();

        System.out.print("Digite o valor a ser convertido: ");
        double valor = scanner.nextDouble();

        double resultado = 0;

        switch (opcao) {
            case 1:
                resultado = converter("USD", "BRL", valor);
                System.out.printf("%.2f USD = %.2f BRL\n", valor, resultado);
                break;
            case 2:
                resultado = converter("BRL", "USD", valor);
                System.out.printf("%.2f BRL = %.2f USD\n", valor, resultado);
                break;
            case 3:
                resultado = converter("EUR", "BRL", valor);
                System.out.printf("%.2f EUR = %.2f BRL\n", valor, resultado);
                break;
            case 4:
                resultado = converter("BRL", "EUR", valor);
                System.out.printf("%.2f BRL = %.2f EUR\n", valor, resultado);
                break;
            case 5:
                resultado = converter("GBP", "BRL", valor);
                System.out.printf("%.2f GBP = %.2f BRL\n", valor, resultado);
                break;
            case 6:
                resultado = converter("BRL", "GBP", valor);
                System.out.printf("%.2f BRL = %.2f GBP\n", valor, resultado);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Método para fazer a requisição na API e converter as moedas
    public static double converter(String moedaOrigem, String moedaDestino, double valor) {
        try {
            String url = API_URL + "?base=" + moedaOrigem;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());
            JsonObject jsonResponse = gson.fromJson(reader, JsonObject.class);
            JsonObject taxas = jsonResponse.getAsJsonObject("rates");
            double taxaConversao = taxas.get(moedaDestino).getAsDouble();
            return valor * taxaConversao;
        } catch (Exception e) {
            System.out.println("Erro ao fazer a conversão: " + e.getMessage());
            return 0;
        }
    }
}