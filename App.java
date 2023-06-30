import java.util.List;
import java.util.Scanner;

//Main basica, menu igual o que a gente faz em todos os trabalhos
// Switch 0 finaliza
// Switch 1 chama o metodo searchAll para procurar as palavras com o prefixo e retorna um array de Word
// Depois faz um foreach para printar todas.
public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final ReadCSV rd = new ReadCSV();

    public static void main(String[] args){
        rd.execute();
        DictionaryTree tree = rd.getTree();
        int userChoice;

        do {
            userChoice = menu();
            switch (userChoice) {
                case 0:
                    System.out.println("Fim do Programa");
                    break;
                case 1:
                    System.out.println("Digite o prefixo a ser buscado:");
                    String prefix = sc.next();
                    List<Word> palavras = tree.searchAll(prefix);
                    if(palavras==null){
                        System.out.println("Palavra não encontrada.");
                    }
                    else{
                        System.out.println("Palavras com o prefixo '" + prefix + "':");
                        for (Word palavra : palavras) {
                            System.out.println("Palavra: " + palavra.getWord() + " | Significado: " + palavra.getMeaning());
                        }
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while(userChoice != 0);
    }

    private static int menu() {
        int option;

        do {
            System.out.println("Menu de Escolha:");
            System.out.println("1. Procurar Palavra.");
            System.out.println("0. Finalizar Programa.");
            option = sc.nextInt();

            if (option < 0 || option > 1) {
                System.out.println("Opção Inválida.");
            }
        } while (option < 0 || option > 1);

        return option;
    }
}