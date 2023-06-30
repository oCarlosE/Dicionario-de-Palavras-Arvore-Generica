import java.util.ArrayList;
import java.util.List;

class DictionaryTree {
    //Nodo que recebe os atributos da palavra
    private class Node {
        private final char letter;
        private final String meaning;
        private final boolean isFinal;
        private Node father;
        private final List<Node> children;

        public Node(char letter, boolean isFinal, String meaning) {
            this.letter = letter;
            this.isFinal = isFinal;
            this.meaning = meaning;
            this.children = new ArrayList<>();
            this.father = null;
        }

        //Adiciona o nodo newNode nos filhos do nodo que chama esse método
        public void addChild(Node newNode) {
            this.children.add(newNode);
            newNode.father = this;
        }

        //tu chama isso de um nodo, e ele vai visitar todos os filhos desse nodo
        // se o filho for um nodo final ele usa o método getword para pegar a palavra e adiciona na lista do parametro
        //retorna a lista de palavras
        public List<Word> traverseBranches(List<Word> words) {
            if (this.isFinal) {
                words.add(this.getWord());
            }

            for (Node child : this.children) {
                child.traverseBranches(words);
            }
            return words;
        }

        //vai subindo a arvore enquando father não for null, e vai pegando a letra de cada nodo que passa
        //no fim o stringBuilder contem a palavra toda, então retorna ele em forma de string

        private Word getWord(){
            //Só chama este método quando o nodo for final;
             StringBuilder wordBuilder = new StringBuilder();
            Node currentNode = this;
            String Sig= this.meaning;

            while (currentNode.father != null) {
                wordBuilder.insert(0, currentNode.letter);
                currentNode = currentNode.father;
            }

            return new Word(wordBuilder.toString(), Sig);
        }

        //encontra o filho de um nodo atraves da letra que ele guarda
        //se ele existe então retorrna ele, senao retorna null
        public Node findChildChar(char letter) {
            for (Node child : this.children) {
                if (child.letter == letter) {
                    return child;
                }
            }
            return null;
        }

        //printa todos os filhos de um nodo
        //criei só para conferir como tava sendo adicionado os nodos
        public void printChilds(){
            for (Node node : this.children) {
                System.out.print(node.letter);
            }
        }
    }

    private Node root;
    private int totalNodes;
    private int totalWords;

    public DictionaryTree() {
        this.root = new Node((char) 0, false, null);
        this.totalNodes = 0;
        this.totalWords = 0;
    }

    //pega o total de palavras
    public int getTotalWords() {
        return this.totalWords;
    }

    // pega o total de nodos
    public int getTotalNodes() {
        return this.totalNodes;
    }

    //adiciona uma palavra na arvore
    //roda um for igual o numero de char que a string tem, atraves do .length
    //o contador serve para dizer em qual indice da palavra estamos.
    //auxfather armazena a referencia do pai do nodo que vai ser adicionado
    //sempre confere para ver se a letra a ser adicionada ja não existe nos filhos do nodo pai, 
    //se exitir não adiciona o nodo, apenas passa a referencia dele para o auxfather
    //se não existir então adiciona como filho do nodo auxfather.
    //a cada rodada do for aumenta o numero de nodos, se for final aumenta o numero de palavras na lista

    public void addWord(String word, String meaning) {
        Node node;
        Node auxFather = null;
        int contador=0;

        for (Character chart : word.toCharArray()) {
            if (contador == word.length()-1) {
                node = new Node(word.charAt(contador), true, meaning);
            } else {
                node = new Node(word.charAt(contador), false, null);
            }

            if(contador==0){
                Node np= root.findChildChar(word.charAt(contador));
                if(np!=null){
                    auxFather= np;
                }
                else{
                    root.addChild(node);
                    node.father= root;
                    auxFather= node;
                }
            }
            else{
                Node aux= auxFather.findChildChar(node.letter);
                if(aux!=null){
                    auxFather= aux;
                }
                else{
                    node.father=auxFather;
                    auxFather.addChild(node);
                    auxFather=node;
                }
            }
            totalNodes++;  // Incrementa o número total de nós a cada iteração
            if (node.isFinal) {
                totalWords++;  // Incrementa o número total de palavras após adicionar a última letra
            }
            contador++;
        }
    }


    //recebe um prefixo, chama o metodo getlastnodeofprefix para viajar até o ultimo nodo do prefixo
    //se o ultimo nodo não existe então a palavra não existe e retorna null
    //senão, para cada nodo filho do lastNode ele chama o traversebranche para percorrer todos os filhos do lastNode
    //a listaAux recebe a lista to traversebranche, e caso não seja nula a lista Word adiciona cada palavra da lista aux nela
    // depois de adicionar ele limpa a lista Aux e repete o laço até acabar, por fim retorna a lista completa de palavras.
    public List<Word> searchAll(String prefix){
        List<Word> words = new ArrayList<>();
        List<Word> listaAux = new ArrayList<>();
        Node aux = getLastNodeOfPrefix(prefix);
        if(aux==null){
            return null;
        }
        for (Node node : aux.children) {
            listaAux= node.traverseBranches(listaAux);

            if(listaAux.size()!=0){
                for (Word word : listaAux) {
                    words.add(word);
                }
                listaAux.clear();
            }
        }

        return words;
    }


    //recebe o prefixo, e então a partir da raiz ele vai indo procurando a letra na lista de filhos do nodo current
    // repete um numero de vezes igual ao tamanho da palavra
    // se o nodo não existe então retorna null
    //se existe então retorna a referencia para o ultimo nodo do prefixo
    private Node getLastNodeOfPrefix(String prefix) {
        Node currentNode = root;

        for (int i = 0; i < prefix.length(); i++) {
            char letter = prefix.charAt(i);
            currentNode = currentNode.findChildChar(letter);

            if (currentNode == null) {
                return null; // Prefixo não encontrado na árvore
            }
        }
        return currentNode;
    }


    //a partir da raiz, printa todos nodos filhos da raiz, e todos os nodos filhos dos filhos
    public void printAll(){
        for (Node nodo : root.children) {
            System.out.println("Nodo: "+nodo.letter);
            System.out.print("Filhos: ");
            nodo.printChilds();
            System.out.println();
        }
    }
}