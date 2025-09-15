package mycompany.genericlists.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.ArrayList;

public class GeradorArquivosBalanceados {
    private static final String NOME_ARQUIVO = "alunosBalanceados.txt";
    
    private static final String[] PRENOMES = {
        "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Isabela", "João",
        "Juliana", "Lucas", "Mariana", "Nathan", "Olivia", "Paulo", "Quésia", "Rafael", "Sofia", "Thiago",
        "Victor", "William", "Xavier", "Yasmin", "Zuleica", "Alfredo", "Beatriz", "Caio", "Denise", "Eliana",
        "Felipe", "Gustavo", "Heitor", "Igor", "Jéssica", "Kevin", "Larissa", "Mateus", "Natália", "Otávio",
        "Patrícia", "Renato", "Sandra", "Tadeu", "Ursula", "Vinícius", "Wellington", "Zilda", "Adriana", "Benício",
        "Cristina", "Davi", "Emanuel", "Flávia", "Geraldo", "Heloísa", "Ícaro", "Jaqueline", "Leonardo", "Marta",
        "Nelson", "Orlando", "Priscila", "Raquel", "Saulo", "Tatiane", "Ubirajara", "Vera", "Wesley", "Zenaide",
        "Alice", "Brenda", "Caetano", "Danilo", "Enzo", "Fabiana", "Gilberto", "Henrique", "Isadora", "José",
        "Kátia", "Lorena", "Maurício", "Natanael", "Osvaldo", "Pamela", "Regina", "Sandro", "Tânia", "Ulisses",
        "Vânia", "Wilson", "Yago", "Zélia", "Amélia", "Bernardo", "Celso", "Dulce", "Edson", "Fátima", "Gilmar",
        "Humberto", "Irene", "Jorge", "Kleber", "Luciana", "Marcelo", "Nadir", "Otacílio", "Paula", "Renata"
    };
    
    private static final String[] SOBRENOMES = {
        "Almeida", "Barbosa", "Campos", "Dias", "Evangelista", "Ferreira", "Gomes", "Henrique", "Iglesias", "Junqueira",
        "Klein", "Lima", "Medeiros", "Nascimento", "Oliveira", "Pereira", "Queiroz", "Rodrigues", "Silva", "Teixeira",
        "Uchoa", "Vasconcelos", "Watanabe", "Ximenes", "Yamamoto", "Zanetti", "Araújo", "Borges", "Coelho", "Dantas",
        "Esteves", "Farias", "Guimarães", "Holanda", "Ivo", "Jardim", "Krieger", "Lacerda", "Monteiro", "Neves",
        "Oliveira", "Porto", "Quintana", "Ramos", "Sanches", "Torrico", "Urbano", "Vieira", "Wanderley", "Xavier",
        "Yunes", "Zampieri", "Abreu", "Barreto", "Coutinho", "Delgado", "Elias", "França", "Godoy", "Haddad",
        "Ibrahim", "Jacob", "Lopes", "Moura", "Nogueira", "Ortega", "Pinto", "Quaresma", "Reis", "Souto",
        "Torres", "Ubaldo", "Valente", "Weber", "Ximenes", "Yamaguchi", "Zanella", "Alvarenga", "Bittencourt", "Carvalho",
        "Duarte", "Espíndola", "Freitas", "Gonçalves", "Herrera", "Ishikawa", "Junqueira", "Lacerda", "Mancini", "Noronha",
        "Orsini", "Paz", "Quevedo", "Rangel", "Souza", "Tavares", "Uchoa", "Vilela", "Werneck", "Xisto"
    };
    
    public static void main(String[] args) {
        gerarArquivo(500);
    }
    
    public  static void gerarArquivo(int numRegistros) {
        Random random = new Random();
        ArrayList<Integer> ids = new ArrayList<>();
        gerarIdentificadoresBalanceados(ids, 1, numRegistros);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, StandardCharsets.UTF_8))) {
            
            for (int i = 0; i < numRegistros; i++) {
                int id = ids.get(i);
                String nome = gerarNomeAleatorio(random);
                writer.write(id + "," + nome + "\n");
                
                if ((i + 1) % 1_000_000 == 0) {
                    System.out.println((i + 1) + " registros gerados...");
                }
            }
            
            System.out.println("Arquivo gerado com sucesso: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
    
    private static void gerarIdentificadoresBalanceados(ArrayList<Integer> ids, int inicio, int fim) {
        if (inicio > fim) return;
        int meio = (inicio + fim) / 2;
        ids.add(meio);
        gerarIdentificadoresBalanceados(ids, inicio, meio - 1);
        gerarIdentificadoresBalanceados(ids, meio + 1, fim);
    }
    
    private static String gerarNomeAleatorio(Random random) {
        String primeiroNome = PRENOMES[random.nextInt(PRENOMES.length)];
        String sobrenome = SOBRENOMES[random.nextInt(SOBRENOMES.length)];
        return primeiroNome + " " + sobrenome;
    }
}