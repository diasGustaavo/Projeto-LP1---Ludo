/**
 * Implementa as mecânicas e regras do jogo Ludo.
 *
 * @author Alan Moraes / alan@ci.ufpb.br
 * @author Victor Koehler / koehlervictor@cc.ci.ufpb.br
 */
public class Jogo {

    // Tabuleiro do jogo
    private final Tabuleiro tabuleiro;
    
    // Dados do jogo.
    private final Dado[] dados;

    /**
     * Construtor padrão do Jogo Ludo.
     * Isto é, um jogo de Ludo convencional com dois dados.
     */
    public Jogo() {
        this(2);
    }
    
    /**
     * Construtor do Jogo Ludo para inserção de um número arbitrário de dados.
     * @param numeroDados Número de dados do jogo.
     */
    public Jogo(int numeroDados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = new Dado[numeroDados];
        
        for (int i = 0; i < this.dados.length; i++) {
            // remover parâmetro do construtor para dado não batizado
            this.dados[i] = new Dado(i);
        }

        inicializaJogo();
    }

    /**
     * Construtor do Jogo Ludo para inserção de dados arbitrários.
     * Útil para inserir dados "batizados" e fazer testes.
     * @param dados Dados
     */
    public Jogo(Dado[] dados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = dados;
        assert dados.length > 0; // TO BE REMOVED

        inicializaJogo();
    }

    
    
    
    private void inicializaJogo() {

        // AQUI SUGERE-SE QUE SE INSIRA A INICIALIZAÇÃO DO JOGO
        // ISTO É, A INSERÇÃO DAS PEÇAS NO TABULEIRO E AS DEFINIÇÕES DOS CAMPOS
        // SE NECESSÁRIO, MODIFIQUE A ASSINATURA DO MÉTODO
        
        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        
        // Vamos inicializar a guarita verde colocando as 4 peças do jogador verde nela.
        //
        // Guarita = espaço onde fica as peças fora do jogo;
        // Consulte a imagem "referencia.png" acompanhada na pasta dessa classe.
        Guarita guaritaVerde;
        Guarita guaritaAzul;
        Guarita guaritaVermelho;
        Guarita guaritaAmarelo;
        
        guaritaVerde = tabuleiro.getGuarita("VERDE");
        for (Casa casaGuarita : guaritaVerde.getTodasAsCasas()) {
            Peca novaPeca = new Peca("VERDE");
            novaPeca.mover(casaGuarita);
        }
        
        guaritaAmarelo = tabuleiro.getGuarita("AMARELO");
        for (Casa casaGuarita : guaritaAmarelo.getTodasAsCasas()) {
            Peca novaPeca = new Peca("AMARELO");
            novaPeca.mover(casaGuarita);
        }
        
        guaritaAzul = tabuleiro.getGuarita("AZUL");
        for (Casa casaGuarita : guaritaAzul.getTodasAsCasas()) {
            Peca novaPeca = new Peca("AZUL");
            novaPeca.mover(casaGuarita);
        }
        
        guaritaVermelho = tabuleiro.getGuarita("VERMELHO");
        for (Casa casaGuarita : guaritaVermelho.getTodasAsCasas()) {
            Peca novaPeca = new Peca("VERMELHO");
            novaPeca.mover(casaGuarita);
        }
        
        

        // Obtemos uma das peças verdes que inicializamos logo acima para usa-la como exemplo.
        // Movemos ela para a casa de inicio do jogador verde.
        // Casa casaGuarita;
        // Casa casaInicio;
        // Peca peca;
                
        // guarita = tabuleiro.getGuarita("VERDE");
        // casaGuarita = guarita.getCasa(0);
        // peca = casaGuarita.getPeca();
        // casaInicio = tabuleiro.getCasaInicio("VERDE");
        // peca.mover(casaInicio);
        
        // // Apenas como um exemplo adicional, colocamos uma peça azul no tabuleiro.
        // peca = new Peca("AZUL");
        // casaInicio = tabuleiro.getCasaInicio("AZUL");
        // peca.mover(casaInicio);

        //
        // TRECHO DE EXEMPLO
        //
    }

    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando para jogar os dados.
     * Aqui deve-se jogar os dados e fazer todas as verificações necessárias.
     */
    public void rolarDados() {

        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR NO DADO DESENHADO NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        // Aqui percorremos cada dado para lançá-lo individualmente.
        for (Dado dado : dados) {
            dado.rolar();
        }
    }
    
    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando quando escolhida uma peça.
     * Aqui deve-se mover a peça e fazer todas as verificações necessárias.
     * @param casa Casa escolhida pelo usuário/jogador.
     */
    public void escolherCasa(Casa casa) {

        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR EM UMA CASA DESENHADA NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        // Perguntamos à casa se ela possui uma peça. 
        // Se não possuir, não há nada para se fazer.
        if (!casa.possuiPeca()) {
            return;
        }
        
        // Perguntamos à casa qual é a peça.
        Peca peca = casa.getPeca();
        
        //NOVO ADICIONADO POR GUSTAVO
        //VERIFICA SE A CASA PERTENCE A GUARITA E SE OS NUMEROS DOS DADOS SAO IGUAIS
        //SE SIM A PECA NA CASA MOVE-SE PARA CASA INICIAL
        
        if(casa.pertenceGuarita()){
            if(dados[0].getValor() == dados[1].getValor()){
                
                peca.mover(tabuleiro.getCasaInicio(peca.getCor()));
            }
        }

        // Percorremos cada dado, somando o valor nele à variável somaDados.
        int somaDados = 0;
        for (Dado dado : dados) {
            somaDados += dado.getValor();
        }
        
        // Percorreremos N casas.
        Casa proximaCasa = casa;
        for (int i = 0; i < somaDados && proximaCasa != null; i++) {
            proximaCasa = proximaCasa.getCasaSeguinte();
        }

        if (proximaCasa != null) {
            // Finalmente, a variável casaN contém a casa que a peça deve ser inserida.
            peca.mover(proximaCasa);
        }
        else {
            // // NÃO HÁ PRÓXIMA CASA!
            // // FIM DO JOGO? A PEÇA ESTÁ NA GUARITA?
            // // Descomente a próxima linha para ser notificado quando isso acontecer:
            // System.err.println("Não há próxima casa!");
        
            // // Descomente as duas próximas linhas para verificar se a peça está na guarita:
            // if (casa.pertenceGuarita())
            //     System.out.println("A peça está na guarita");
        }
    }
    
    /**
     * Retorna o jogador que deve jogar os dados ou escolher uma peça.
     * @return Cor do jogador.
     */
    public String getJogadorDaVez() {
        return null;
    }
    
    /**
     * O tabuleiro deste jogo.
     * @return O tabuleiro deste jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Retorna o i-ésimo dado deste jogo entre 0 (inclusivo) e N (exclusivo).
     * Consulte getQuantidadeDeDados() para verificar o valor de N
     * (isto é, a quantidade de dados presentes).
     * @param i Indice do dado.
     * @return O i-ésimo dado deste jogo.
     */
    public Dado getDado(int i) {
        return dados[i];
    }
    
    /**
     * Obtém a quantidade de dados sendo usados neste jogo.
     * @return Quantidade de dados.
     */
    public int getQuantidadeDados() {
        return dados.length;
    }
}
