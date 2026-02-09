import java.util.ArrayList; // Import para criar listas dinâmicas
import java.util.Collections;// Import para embaralhar as listas
import java.util.List; // Import para usar as listas
import java.util.Random; // Import para gerar números aleatórios que randomizam as questões
import java.util.Scanner; // Import clássico do Scanner para ler as entradas do usuário

public class QuizJavaNotas{

    private static final int QUESTOES_POR_NIVEL = 12;
    private static final Random random = new Random();


    static class Question { //Classe Questão que armazena o texto, opções e índice correto
        String texto; // Texto da questão em formato String
        List<String> opcoes; // Lista que sempre retorna quatro opções
        int indiceCorreto;   // Índice das opções que vai de 0 a 3

        Question(String texto, String[] opcoesArr, int indiceCorreto) {
            this.texto = texto; // Inicializa o texto anteriormente declarado
            this.opcoes = new ArrayList<>();// Incializa a lista de opções antteriormente declarada
            Collections.addAll(this.opcoes, opcoesArr); //Elege todas as opções do Array para a lista
            this.indiceCorreto = indiceCorreto; // Inicializa o índice correto anteriormente declarado
        }

        ShuffledQuestion shuffledCopy() { // Retorna uma cópia randomizada da questão
            List<String> opCopy = new ArrayList<>(opcoes);// Cria uma cópia da lista de opções
            List<Integer> indices = new ArrayList<>();// Cria uma lista de índices
            for (int i = 0; i < opCopy.size(); i++) indices.add(i);// Adiciona índices de 0 a 3
            Collections.shuffle(indices, random);// Embaralha os índices

            List<String> novasOpcoes = new ArrayList<>();// Cria uma nova lista de opções
            int novoIndiceCorreto = -1;// Inicializa o novo índice correto
            for (int i = 0; i < indices.size(); i++) {// Operador for para percorrer os índices randomizados
                int idx = indices.get(i);// Pega o índice embaralhado
                novasOpcoes.add(opCopy.get(idx));// Adiciona a opção correspondente ao novo índice
                if (idx == indiceCorreto) novoIndiceCorreto = i;// Atualiza o novo índice correto se o índice original for encontrado
            }

            return new ShuffledQuestion(texto, novasOpcoes, novoIndiceCorreto);// Retorna a nova questão randomizada 
        }
    }

    /* RESULTADO DA RANDOMIZAÇÃO */
    static class ShuffledQuestion {
        String texto;
        List<String> opcoes; // 4 
        int indiceCorreto;   // 0..3

        ShuffledQuestion(String texto, List<String> opcoes, int indiceCorreto) {
            this.texto = texto;
            this.opcoes = opcoes;
            this.indiceCorreto = indiceCorreto; 
        }
    }

    public static void main(String[] args) {// Método principal que inicia o quiz
        Scanner scanner = new Scanner(System.in);

        /* CRIAÇÃO DO BANCO DE QUESTÕES E SUAS LISTAS */
        List<Question> bancoFacil = criarBancoFacil(); // Cria o banco de questões fáceis
        List<Question> bancoMedio = criarBancoMedio();// Cria o banco de questões médias
        List<Question> bancoDificil = criarBancoDificil();// Cria o banco de questões difíceis

        ArrayList<String> nomesJogadores = new ArrayList<>();// Lista que armazena os nomes dos jogadores do Quiz
        ArrayList<Integer> pontuacoesFinais = new ArrayList<>(); // Lista que amarmazena as pontuações finais dos jogadores
        boolean continuarGeral = true;// Variável de controle para continuar o quiz caso o usuário queira cadastrar um novo jogador ao término do Quiz.

        while (continuarGeral) {// Loop principal para permitir múltiplos jogadores
            System.out.print("Digite o nome do jogador: "); // Solicita o nome do jogador
            String nome1 = scanner.nextLine(); // Lê o nome do jogador contido na variável nome1
            nomesJogadores.add(nome1);// Adiciona o nome do jogador à lista de nomes
            pontuacoesFinais.add(0); // inicializa pontuação do jogador atual com 0 para manter sincronização

            System.out.println("\nBem-vindo ao Quiz de Tecnologia e Desenvolvimento de Sistemas, " + nome1 + "!");
            // Mensagem de boas-vindas ao jogador cadastrado

            /* PROGRESSO INDIVIDUAL */
            System.out.println("Jogador: " + nome1);// Mostra o nome do jogador atual
            int ultFacil = -1, totFacil = Math.min(QUESTOES_POR_NIVEL, bancoFacil.size());// Variáveis para rastrear o progresso no nível fácil
            int ultMedio = -1, totMedio = Math.min(QUESTOES_POR_NIVEL, bancoMedio.size());// Variáveis para rastrear o progresso no nível médio
            int ultDificil = -1, totDificil = Math.min(QUESTOES_POR_NIVEL, bancoDificil.size());// Variáveis para rastrear o progresso no nível difícil
            boolean continuar = true; // Variável de controle para o menu do jogador atual
            int opcao; // Variável para armazenar a opção escolhida no menu

            while (continuar) { // Loop do menu para o jogador atual que se diferencia de continuarGeral
                System.out.println("\n----- MENU -----");
                System.out.println("1 - Jogar nível fácil (" + totFacil + " questões)");
                System.out.println("2 - Jogar nível médio (" + totMedio + " questões)");
                System.out.println("3 - Jogar nível difícil (" + totDificil + " questões)");
                System.out.println("4 - Mostrar progresso");
                System.out.println("5 - Mostrar instruções");
                System.out.println("6 - Trocar de Jogador");
                System.out.println ();
                System.out.print("Escolha uma opção: ");

                if (scanner.hasNextInt()) { // Verifica se a entrada é um inteiro, de acordo com a natureza das opções no menu
                    opcao = scanner.nextInt();// Lê a opção escolhida pelo usuário
                    scanner.nextLine();// Limpa o buffer do scanner (necessário após nextInt para evitar problemas na próxima leitura)
                } else {// Condicional de else para tratar entradas inválidas que não sejam inteiros
                    System.out.println("Entrada inválida!");
                    scanner.next();
                    continue;// Retorna ao início do loop do menu caso a entrada seja inválida
                }

                switch (opcao) { //Switch case para tratar as diferentes opções do menu. A entrada do usuário fica armazenada na variável opcao

                    case 1: 
                        ultFacil = executarQuiz(bancoFacil, "NÍVEL FÁCIL", scanner); //Chama a função executarQuiz 
                        // para o nível fácil e armazena a pontuação contida em ultFacil
    
                        int idxJogador = nomesJogadores.indexOf(nome1);
                        if (idxJogador >= 0) pontuacoesFinais.set(idxJogador, Math.max(pontuacoesFinais.get(idxJogador), ultFacil));
                        break;

                    case 2:
                        ultMedio = executarQuiz(bancoMedio, "NÍVEL MÉDIO", scanner);//Chama a função executarQuiz 
                        // para o nível médio e armazena a pontuação contida em ultMedio
                        idxJogador = nomesJogadores.indexOf(nome1);
                        if (idxJogador >= 0) pontuacoesFinais.set(idxJogador, Math.max(pontuacoesFinais.get(idxJogador), ultMedio));
                        break;

                    case 3:
                        ultDificil = executarQuiz(bancoDificil, "NÍVEL DIFÍCIL", scanner);//Chama a função executarQuiz para o nível difícil e armazena a 
                        // pontuação contida em ultDificil
                        idxJogador = nomesJogadores.indexOf(nome1);
                        if (idxJogador >= 0) pontuacoesFinais.set(idxJogador, Math.max(pontuacoesFinais.get(idxJogador), ultDificil));
                        break;

                    case 4: 
                        System.out.println("\n===== PROGRESSO ====="); // Mostra o progresso do jogador atual dividido entre os três níveis de dificuldade

                        // Fácil
                        if (ultFacil < 0) {
                            System.out.println("Fácil:  ainda não jogou");
                        } else {
                            System.out.println("Fácil:  " + ultFacil + "/" + totFacil); // Mostra a pontuação do jogador no nível fácil, 
                            // chamando as variáveis ultFacil e totFacil
                        }

                        // Médio
                        if (ultMedio < 0) {
                            System.out.println("Médio:  ainda não jogou");
                        } else {
                            System.out.println("Médio:  " + ultMedio + "/" + totMedio); // Mostra a pontuação do jogador no nível médio, 
                            // chamando as variáveis ultMedio e totMedio
                        }

                        // Difícil
                        if (ultDificil < 0) {
                            System.out.println("Difícil: ainda não jogou");
                        } else {
                            System.out.println("Difícil: " + ultDificil + "/" + totDificil); // Mostra a pontuação do jogador no nível difícil, 
                            // chamando as variáveis ultDificil e totDificil
                        }

                        break;

                    case 5: 
                        System.out.println("\n===== INSTRUÇÕES =====");
                        System.out.println(" Bem vindo ao Quiz de Tecnologia e Desenvolvimento de Sistemas!");
                        System.out.println("O quiz é dividido em três níveis de dificuldade: fácil, médio e difícil e contemplam Lógica de Programação, Banco de Dados e UI/UX");
                        System.out.println("Cada nível contém um conjunto de perguntas relacionadas a conceitos de tecnologia e desenvolvimento de sistemas.");
                        System.out.println(" São doze perguntas que se dividem igualmente entre os três tópicos por nível (4 por tópico).");
                        System.out.println("Cada pergunta tem 4 alternativas (a, b, c, d).");
                        System.out.println("Digite apenas a letra da alternativa escolhida.");
                        System.out.println("Se digitar algo diferente, a pergunta será repetida até você escolher a, b, c ou d.");
                        System.out.println("Ao final, sua pontuação é exibida.");
                        break;

                    case 6: 
                        // ao trocar de jogador mantemos pontuação já atualizada no pontuacoesFinais
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            }

            while (true) { // Loop para perguntar se o usuário deseja cadastrar um novo jogador, dentro da opção 6 do menu
                System.out.print("\nDeseja cadastrar novo jogador? (s/n): ");
                String resposta = scanner.nextLine().trim().toLowerCase(); // Lê a resposta do usuário e normaliza para minúsculas para facilitar a comparação de valores

                if (resposta.equals("s")) { // Condicional que testa se o valor da resposta do usuário contém "s"
                    System.out.println("Novo jogador será cadastrado!\n");
                    continuarGeral = true; // Mantém o loop principal para cadastrar um novo jogador enquanto a variável continuarGeral for true

                    break;

                } else if (resposta.equals("n")){
                    continuarGeral = false; // Encerra o loop principal para cadastrar novos jogadores quando a variável continuarGeral for false

                    break;

                } else {
                    System.out.println("Resposta não identificada. Responda com 's' ou 'n'.\n"); // Mensagem de erro para entradas
                    // inválidas que não sejam "s" ou "n"
                }
            }
        }

        // ===== PLACAR FINAL =====
        System.out.println("\n===== PLACAR FINAL =====");

        for (int i = 0; i < nomesJogadores.size(); i++) {// Percorre todos os jogadores cadastrados
            int pts; // variável para armazenar a pontuação do jogador

            if (i < pontuacoesFinais.size()) {  // Verifica se existe uma pontuação correspondente ao jogador
                pts = pontuacoesFinais.get(i); // pontuação existente 
            } else {
                pts = 0; // Caso o jogador não tenha jogado, a pontuação é zero
            }

            System.out.println(nomesJogadores.get(i) + ": " + pts + " pontos"); // Exibe o nome do jogador e sua pontuação
        }

        scanner.close();
    }

    /* EXECUÇÃO DAS QUESTÕES */
    public static int executarQuiz(List<Question> banco, String nivel, Scanner scanner) {
        List<Question> copiaBanco = new ArrayList<>(banco); // Cria uma cópia do banco de questões para evitar modificar o original,
        // mantendo assim a integridade de dados a cada partida (isolamento, segurança e previsibilidade_)
        Collections.shuffle(copiaBanco, random); // Embaralha a cópia do banco de questões para garantir a randomização das perguntas
        // a cada execução do quiz
        int totalPerguntas = Math.min(QUESTOES_POR_NIVEL, copiaBanco.size()); // Determina o número total de perguntas a serem feitas,
        // limitado pelo tamanho do banco de questões
        int pontuacao = 0; // Inicializa a pontuação do jogador em 0, pois esse é o valor inicial e também o valor mínimo possível

        for (int i = 0; i < totalPerguntas; i++) { // Loop para percorrer o número total de perguntas a serem feitas
            Question questaoAtual = copiaBanco.get(i); // Pega a questão atual da cópia do banco com a função get
            ShuffledQuestion questaoRandomizada = questaoAtual.shuffledCopy(); // Gera uma versão randomizada da questão atual para embaralhar as opções

            int respostaUsuario = -1; // Variável para armazenar a resposta do usuário, inicia com -1 para indicar "sem resposta válida"

            while (true) { // Loop para garantir que o usuário insira uma resposta válida, repetindo a pergunta se necessário
                System.out.println("\nQuestão " + (i+1) + ": " + questaoRandomizada.texto); // i +1 para exibir a questão começando de 1 em vez de 0
                char letra = 'a'; // Inicializa a variável letra com 'a' para exibir as opções de resposta
                for (String op : questaoRandomizada.opcoes) {
                    System.out.println(letra + " - " + op); // Exibe cada opção de resposta com a letra correspondente, 
                    // incrementando a letra a cada iteração
                    letra++;
                }
                System.out.print("Sua resposta (a, b, c ou d): "); // Solicita a resposta do usuário entre a,b,c ou d

                String respostaNormal = scanner.nextLine().trim().toLowerCase(); // Lê a resposta do usuário, 
                // remove espaços em branco e converte para minúsculas

                if (respostaNormal.length() == 1) {// Verifica se a resposta tem exatamente um caractere
                    char ch = respostaNormal.charAt(0);// Pega o primeiro caractere da resposta e amarzena em ch, um acrônimo de character

                    if (ch >= 'a' && ch <= 'd') { //Cria a condição que verifica se a resposta está entre 'a' e 'd'

                        if (ch == 'a') { // Testa a condição se a resposta é 'a'
                            respostaUsuario = 0; 
                        } else if (ch == 'b') { //Caso não seja, cria a condição para as demais alternativas
                            respostaUsuario = 1;
                        } else if (ch == 'c') {
                            respostaUsuario = 2;
                        } else if (ch == 'd') {
                            respostaUsuario = 3;
                        }

                        break; 

                        /*NOTA DE RODAPÉ SOBRE CÓDIGOS ASCII */
                        // Isso permite verificar se a resposta do usuário está dentro do intervalo válido
                        // usando comparações de caracteres
                        // Aonde, a' → 0 ,'b' → 1,'c' → 2,'d' → 3
                        // Da mesma forma que as variáveis de início, a contagem se inicia em 0

                    }
                }

                System.out.println("Opção inválida! Digite apenas a, b, c ou d."); // Mensagem de erro para entradas inválidas
            }

            if (respostaUsuario == questaoRandomizada.indiceCorreto) { // Verifica se a resposta do usuário está correta 
            // comparando com o índice correto da questão randomizada
                System.out.println("CORRETO!"); // Mensagem de confirmação para resposta correta
                pontuacao++; // Incrementa a pontuação do jogador em 1 para cada resposta correta
            } else {
                System.out.println("ERRADO! A resposta correta é: " + converterResposta(questaoRandomizada.indiceCorreto)); // A função converterResposta converte 
                // o índice correto para a letra correspondente
            }
        }

        System.out.println("\nVocê terminou o " + nivel + ". Pontuação: " + pontuacao + "/" + totalPerguntas); // Exibe a pontuação final do jogador 
        // ao término do nível
        return pontuacao; // Retorna a pontuação final do jogador ao término do nível
    }

    public static String converterResposta(int n) { // Uma função auxiliar que converte o índice da resposta correta 
    // em sua representação de letra correspondente
        switch (n) { // Switch case para mapear os índices para letras, 
        // onde 0 = a, 1 = b, 2 = c, 3 = d. O retorno é usado para buscar a resposta correta na função executarQuiz
            case 0: return "a";
            case 1: return "b";
            case 2: return "c";
            case 3: return "d";
            default: return "?";
        }
    }

    // ===== BANCO DE QUESTÕES TOTAIS =====

    /*LISTA DE QUESTÕES DO NÍVEL FÁCIL */

    private static List<Question> criarBancoFacil() {
        List<Question> lista = new ArrayList<>();

        // --- Lógica/Java (6)
        lista.add(new Question(
                "O que é uma variável em programação?",
                new String[] {"Um comando que executa uma ação específica."
                , "Um tipo de dado que armazena apenas números inteiros."
                , "Um espaço na memória reservado para armazenar um valor que pode ser modificado."
                , "Uma palavra reservada da linguagem."},
                2 // c
        ));

        lista.add(new Question(
                "Qual tipo em Java é mais apropriado para números com casas decimais?",
                new String[] {"int","char","double","boolean"},
                2 // c
        ));

        lista.add(new Question(
                "Qual laço é ideal quando se sabe o número exato de repetições?",
                new String[] {"if-else","for","do-while","while"},
                1 // b
        ));

        lista.add(new Question(
                "Como obtemos o tamanho de um array em Java?",
                new String[] {"array.length()","array.size","array.length","array.getSize()"},
                2 // c
        ));

        lista.add(new Question(
        "O que faz a palavra-chave 'final' aplicada a uma variável em Java?",
        new String[] {"Permite que a variável seja modificada",
        "Impede que a variável seja modificada após a inicialização",
        "Transforma a variável em estática",
        "Faz a variável ser visível apenas dentro do pacote"},
        1 // b
        ));

        lista.add(new Question(
                "Qual é o propósito do modificador 'static' em Java?",
                new String[] {"Forçar que o método seja privado"
                ,"Indicar que o membro pertence a uma instância específica"
                ,"Transformar o método em abstrato"},
                2 // c
        ));




        // --- Banco de Dados (6)
        lista.add(new Question(
                "Qual comando SQL insere registros em uma tabela?",
                new String[] {"SELECT","INSERT","DELETE","UPDATE"},
                1 // b
        ));

        lista.add(new Question(
                "O que é uma chave primária (PK)?",
                new String[] {"Um campo que pode repetir",
                "Um identificador único",
                "Uma tabela",
                "Um índice de performance"},
                1 // b
        ));

        lista.add(new Question(
                "No modelo ER, uma entidade é geralmente representada por:",
                new String[] {"Círculo","Losango","Retângulo","Triângulo"},
                2 // c (retângulo)
        ));

        lista.add(new Question(
                "O que faz o comando SELECT?",
                new String[] {"Remove dados","Insere dados","Consulta dados","Altera dados"},
                2 // c
        ));

        lista.add(new Question(
                "O que significa normalização em banco de dados?",
                new String[] {"Duplicar tabelas para otimizar leitura",
                "Organizar dados para reduzir redundância e dependências indesejadas",
                "Converter dados para formato JSON",
                "Criar índices em todas as colunas"},
                1 // b
        ));

        lista.add(new Question(
    "O que faz um JOIN INNER entre duas tabelas?",
    new String[] {
        "Retorna todas as linhas de ambas as tabelas, mesmo sem correspondência",
        "Retorna apenas as linhas da primeira tabela",
        "Retorna apenas as linhas que têm correspondência em ambas as tabelas",
        "Cria uma nova tabela física contendo todas as colunas"},
    2 // c
));

        // --- UI/UX (6)
        lista.add(new Question(
                "O que significa UX?",
                new String[] {"User Interface",
                "User Experience",
                "Universal Xperience",
                "User Execution"},
                1 // b
        ));

        lista.add(new Question(
                "O que é prototipagem no Design Thinking?",
                new String[] {"Criar o produto final",
                "Testar ideias rapidamente",
                "Fazer apenas design visual",
                "Documentar requisitos"},
                1 // b
        ));

        lista.add(new Question(
                "O que é 'UI'?",
                new String[] {"User Interface",
                "User Interaction",
                "Unique Identity",
                "Universal Innovation"},
                0 // a
        ));

        lista.add(new Question(
                "Qual o principal objetivo do Design Thinking?",
                new String[] {"Maximizar lucro",
                "Resolver problemas centrados no usuário",
                "Somente estética",
                "Somente tecnologia"},
                1 // b
        ));

        lista.add(new Question(
        "O que é um protótipo de baixa fidelidade?",
        new String[] {
        "Um protótipo interativo com aparência final",
        "Rascunhos ou wireframes simples usados para testar fluxos e ideias rapidamente",
        "Um protótipo que roda em produção",
        "Um modelo estatístico de comportamento do usuário"},
    1 // b
));

lista.add(new Question(
    "O que é uma heurística em avaliação de usabilidade?",
    new String[] {
        "Uma regra ou princípio prático usada para avaliar problemas de usabilidade",
        "Um tipo de protótipo de alta fidelidade",
        "Uma métrica de performance de servidores",
        "Um padrão de codificação em JavaScript"},
    0 // a
));

        return lista;
    }

    /*LISTA DE QUESTÕES DO NÍVEL MÉDIO */
    private static List<Question> criarBancoMedio() {
        List<Question> lista = new ArrayList<>();

        // --- Lógica/Java (6)
        lista.add(new Question(
                "Em POO, o que é um objeto?",
                new String[] {"Uma variável global",
                "Uma instância de uma classe",
                "Uma função",
                "Um pacote"},
                1 // b
        ));

        lista.add(new Question(
                "Qual estrutura usa LIFO?",
                new String[] {"Fila","Lista","Pilha","Árvore"},
                2 // c
        ));

        lista.add(new Question(
                "O que faz um compilador?",
                new String[] {"Executa o programa",
                "Tradução para linguagem de máquina",
                "Edita código",
                "Refatora código"},
                1 // b
        ));

        lista.add(new Question(
                "Como testar 'a' diferente de 'b' em Java?",
                new String[] {"a = b","a == b","a != b","a <> b"},
                2 // c
        ));

        lista.add(new Question(
    "O que é um construtor em Java?",
    new String[] {
        "Um método especial executado ao criar um objeto",
        "Um método que destrói objetos",
        "Um método que só aceita tipos primitivos",
        "Um método responsável por coleta de lixo"},
    0 // a
));

lista.add(new Question(
    "O que o modificador 'private' faz em Java?",
    new String[] {
        "Permite acesso apenas dentro da mesma classe",
        "Permite acesso em qualquer classe",
        "Permite acesso apenas em subclasses",
        "Permite acesso apenas no mesmo pacote"
    },
    0 // a
));



        // --- Banco de Dados (6)
        lista.add(new Question(
                "O que é uma chave estrangeira (FK)?",
                new String[] {"Uma cópia da PK de outra tabela",
                "Um índice",
                "Um tipo de dado",
                "Uma view"},
                0 // a
        ));

        lista.add(new Question(
                "O que faz a cláusula WHERE?",
                new String[] {"Agrupa registros",
                "Filtra registros",
                "Ordena registros",
                "Cria índices"},
                1 // b
        ));

        lista.add(new Question(
    "Qual comando SQL retorna registros sem repetição?",
    new String[] {
        "UNIQUE",
        "DISTINCT",
        "FILTER",
        "ONLY"},
    1 // b
));

        lista.add(new Question(
                "O comando SQL para apagar uma tabela é:",
                new String[] {"DELETE TABLE","DROP TABLE","REMOVE TABLE","ERASE TABLE"},
                1 // b
        ));

        lista.add(new Question(
    "O que caracteriza um relacionamento 1:N?",
    new String[] {
        "Uma linha em cada tabela se relaciona somente com uma da outra",
        "Uma linha na tabela A pode se relacionar com muitas na tabela B",
        "Ambas as tabelas têm registros duplicados",
        "Apenas tabelas com chave composta podem formar 1:N"},
    1 // b
));


lista.add(new Question(
    "Qual é o objetivo da integridade referencial?",
    new String[] {
        "Evitar a criação de índices não usados",
        "Garantir que valores de chave estrangeira existam na tabela relacionada",
        "Aumentar o desempenho em consultas",
        "Eliminar redundância de atributos"
    },
    1 // b
));
        

        // --- UI/UX (6)
        lista.add(new Question(
                "Heurística que mantém o usuário informado:",
                new String[] {"Compatibilidade com o mundo real"
                ,"Visibilidade do status do sistema",
                "Estética",
                "Controle do usuário"},
                1 // b
        ));

        lista.add(new Question(
                "O que são pontos de contato na jornada do usuário?",
                new String[] {"Apenas interações online",
                "Todos os momentos de interação com a marca",
                "Somente pontos positivos",
                "Somente ações de marketing"},
                1 // b
        ));

        lista.add(new Question(
                "Qual a diferença principal entre UX e UI?",
                new String[] {"UX é visual, UI é experiência",
                "UX é mais ampla; UI foca na interface visual",
                "São sinônimos",
                "UX é só para apps"},
                1 // b
        ));

        lista.add(new Question(
                "No Design Thinking, a fase de Definição serve para:",
                new String[] {"Gerar muitas ideias",
                "Criar protótipos",
                "Sintetizar insights e definir o problema central"
                ,"Testar o mercado"},
                2 // c
        ));

        lista.add(new Question(
    "O que é um wireframe?",
    new String[] {
        "Um modelo de baixa fidelidade que mostra a estrutura da interface",
        "Um protótipo finalizado com design completo",
        "Um documento de engenharia de software",
        "Uma análise de dados de marketing"},
    0 // a
));

lista.add(new Question(
    "O que define uma boa arquitetura de informação?",
    new String[] {
        "Uso intensivo de imagens",
        "Organização clara e lógica do conteúdo para facilitar navegação",
        "Remoção de menus",
        "Uso obrigatório de ícones minimalistas"
    },
    1 // b
));



        return lista;
    }

    /*LISTA DE QUESTÕES DO NÍVEL DIFÍCIL */

    private static List<Question> criarBancoDificil() {
        List<Question> lista = new ArrayList<>();

        // --- Lógica/Java (6)
        lista.add(new Question(
                "Em Java, qual é o componente que executa bytecode?",
                new String[] {"javac","JRE","JVM","JDK"},
                2 // c
        ));

        lista.add(new Question(
                "O que é deadlock?",
                new String[] {"Uma exceção do Java",
                "Bloqueio mútuo entre processos",
                "Loop infinito",
                "Garbage collector acionado"},
                1 // b
        ));

        lista.add(new Question(
                "Qual expressão retorna número de elementos em vetor 'a'?",
                new String[] {"a.size()","a.length","a.count()","a.getLength()"},
                1 // b
        ));

        lista.add(new Question(
                "Qual operador tem precedência maior em (a + b > c) && d * 3 ?",
                new String[] {"&&","*","+","\">\""},
                2 // c
        ));

        lista.add(new Question(
    "O que significa 'Thread-safe'?",
    new String[] {
        "A classe funciona apenas com uma thread",
        "A classe pode ser usada de forma segura em ambientes com múltiplas threads simultâneas",
        "A classe nunca lança exceções",
        "A classe é protegida contra acesso externo"},
    1 // b
));

lista.add(new Question(
    "O que caracteriza o polimorfismo de sobrescrita?",
    new String[] {
        "Criar múltiplos métodos com o mesmo nome",
        "Alterar implementação de um método herdado mantendo a mesma assinatura",
        "Herança múltipla entre classes",
        "Executar várias threads simultaneamente"},
    1 // b
));



        // --- Banco de Dados (6)
        lista.add(new Question(
                "O que é dependência funcional transitiva?",
                new String[] {"X→Y e Y→Z então X→Z",
                "X determina Y",
                "Y determina X",
                "Independência total"},
                0 // a
        ));

        lista.add(new Question(
                "O relacionamento N:N é normalmente implementado por:",
                new String[] {"Uma view",
                "Uma tabela associativa (junction)",
                "Uma trigger","Uma constraint"},
                1 // b
        ));

        lista.add(new Question(
                "Qual forma normal elimina dependências parciais?",
                new String[] {"1ª FN","2ª FN","3ª FN","BCNF"},
                1 // b
        ));

        lista.add(new Question(
                "O que o comando DROP DATABASE faz?",
                new String[] {"Cria database",
                "Remove todo o banco",
                "Apaga registros de uma tabela",
                "Renomeia database"},
                1 // b
        ));

        lista.add(new Question(
    "Qual problema ocorre quando duas transações dependem de recursos bloqueados uma pela outra?",
    new String[] {
        "Rollback automático",
        "Deadlock",
        "Starvation",
        "Shadow copying"},
    1 // b
));

lista.add(new Question(
    "O que é uma tabela de junção (junction table)?",
    new String[] {
        "Uma tabela temporária",
        "Uma tabela usada para implementar relacionamentos N:N",
        "Um índice composto",
        "Uma view materializada"},
    1 // b
));

        // --- UI/UX (4)
        lista.add(new Question(
                "O que é empatia no Design Thinking?",
                new String[] {"Garantir viabilidade tecnológica"
                ,"Assegurar lucratividade",
                "Entender profundamente as necessidades das pessoas",
                "Focar apenas no produto"},
                2 // c
        ));

        lista.add(new Question(
                "Por que mapear emoções na jornada do usuário?",
                new String[] {"Só para documentação",
                "Para identificar dores e oportunidades",
                "Para aumentar cores","Porque é obrigatório"},
                1 // b
        ));

        lista.add(new Question(
                "O que são 'aceleradores' na heurística de eficiência de uso?",
                new String[] {"Animações longas",
                "Atalhos para usuários experientes",
                "Desabilitar funções","Remover funcionalidades"},
                1 // b
        ));

        lista.add(new Question(
                "Qual crítica ao alinhamento justificado em textos longos?",
                new String[] {"Melhora legibilidade",
                "Pode criar espaçamentos irregulares entre palavras",
                "Sempre preferível","É obrigatório em UI"},
                1 // b
        ));

        lista.add(new Question(
    "O que significa design responsivo?",
    new String[] {
        "Design que responde a perguntas do usuário",
        "Design que se adapta automaticamente a diferentes tamanhos de tela e dispositivos",
        "Design somente para desktop",
        "Design focado apenas em performance"},
    1 // b
));

lista.add(new Question(
    "O que é um protótipo de alta fidelidade?",
    new String[] {
        "Um esboço simples sem interatividade",
        "Um modelo quase idêntico ao produto final, com visual detalhado e interações",
        "Um rascunho sem regras de design",
        "Um documento técnico"},
    1 // b
));

        return lista;
    }
}
