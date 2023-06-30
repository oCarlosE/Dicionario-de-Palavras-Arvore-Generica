import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//Le o arquivo
//pega os campos 0 e 1 de aux, e adiciona na arvore
public class ReadCSV {
    private DictionaryTree tree;

    public ReadCSV() {
        this.tree = new DictionaryTree();
    }

    public void execute() {
        String[] aux;

        Path path = Paths.get("dicionario.csv");

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String line = reader.readLine();
            while (line != null) {
                aux = line.split(";");

                if (aux.length >= 2) { // Verifica se há pelo menos duas colunas
                    String word = aux[0].toLowerCase().trim();
                    String meaning = aux[1].toLowerCase().trim();
                    this.tree.addWord(word, meaning);
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: %s%n", e);
        }
    }

    public DictionaryTree getTree() {
        return this.tree;
    }
}