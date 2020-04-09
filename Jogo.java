/**
 * Implementa as mecÃ¢nicas e regras do jogo Ludo.
 *
 * @author Alan Moraes / alan@ci.ufpb.br
 * @author Victor Koehler / koehlervictor@cc.ci.ufpb.br
 */

import java.util.ArrayList;

public class Jogo {
    // Tabuleiro do jogo
    private final Tabuleiro tabuleiro;
 
    //Turnos
    private final String[] turnos = {"VERDE", "VERMELHO", "AZUL", "AMARELO"};
    private int indiceTurno = 0;
   
    //Pecas do Jogo
    private ArrayList<Peca> pecasDoJogo = new ArrayList();
    
    // Dados do jogo.
    private final Dado[] dados;
    private boolean rolouDados = false;
    private boolean fimDeJogo = false;

    /**
     * Construtor padrÃ£o do Jogo Ludo.
     * Isto Ã©, um jogo de Ludo convencional com dois dados.
     */
    public Jogo() {
        this(2);
    }
   
    /**
     * Construtor do Jogo Ludo para inserÃ§Ã£o de um nÃºmero arbitrÃ¡rio de dados.
     * @param numeroDados NÃºmero de dados do jogo.
     */
    public Jogo(int numeroDados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = new Dado[numeroDados];
        
        for (int i = 0; i < this.dados.length; i++) {
            // remover parÃ¢metro do construtor para dado nÃ£o batizado
            this.dados[i] = new Dado(i);
        }

        inicializaJogo();
    }

    /**
     * Construtor do Jogo Ludo para inserÃ§Ã£o de dados arbitrÃ¡rios.
     * Ãštil para inserir dados "batizados" e fazer testes.
     * @param dados Dados
     */
    public Jogo(Dado[] dados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = dados;
        assert dados.length > 0; // TO BE REMOVED

        inicializaJogo();
    }
    
    
    /**
     * Inicializa o jogo, preechendo todas as guaritad com as suas respectivas peças
     */
    private void inicializaJogo() {
        Guarita guarita;
        String[] cores = {"VERDE", "AZUL", "AMARELO", "VERMELHO"};
        
        for (int i = 0; i < cores.length; i++) {
            guarita = tabuleiro.getGuarita(cores[i]);
            for (Casa casaGuarita : guarita.obterTodasAsCasas()) {
                Peca novaPeca = new Peca(cores[i]);
                novaPeca.mover(casaGuarita);
                pecasDoJogo.add(novaPeca);
            }
        }
    }

    /**
     * MÃ©todo invocado pelo usuÃ¡rio atravÃ©s da interface grÃ¡fica ou da linha de comando para jogar os dados.
     * Aqui deve-se jogar os dados e fazer todas as verificaÃ§Ãµes necessÃ¡rias.
     */
    public void rolarDados() {
        // AQUI SE IMPLEMENTARÃ� AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÃ�RIO CLICAR NO DADO DESENHADO NA INTERFACE GRÃ�FICA,
        // ESTE MÃ‰TODO SERÃ� INVOCADO.

        if(rolouDados || fimDeJogo){
            return;
        }

        // Aqui percorremos cada dado para lanÃ§Ã¡-lo individualmente.
        for (Dado dado : dados) {
            dado.rolar();
        }
        rolouDados = true;
        
        if(!possivelJogar()){
            setJogadorDaVez();
            return;
        }
    }
    
    
    /**
     * Método responsável por capturar a peça inimiga
     * Todas as peças capturadas são devolvidas a sua recpetiva guarita
     */
    public void capturarPeca(Peca pecaCapturada){
        Guarita guaritaPecaCapturada = tabuleiro.getGuarita(pecaCapturada.obterCor());
        int qtdPecasCapt = pecaCapturada.getQtdPecas();
        
        pecaCapturada.capturar();
        
        String corPecaCapt = pecaCapturada.obterCor();
        Casa casasDaGuarita[] = guaritaPecaCapturada.obterTodasAsCasas();
        int contador = 0;
        for (Peca peca : pecasDoJogo) {
            if(peca.obterCor() == corPecaCapt && peca.obterCasa() == null){
                for (int i = contador; i < 4; i++){
                    Casa casaGuarita = casasDaGuarita[i];
                    if(casaGuarita.getPeca() == null){
                        peca.mover(casaGuarita);
                        qtdPecasCapt--;
                        contador = i + 1;
                        break;
                    }
                }
            }  
            if(qtdPecasCapt == 0){
                break;
            }
        }
    }
    
    /**
     * MÃ©todo invocado pelo usuÃ¡rio atravÃ©s da interface grÃ¡fica ou da linha de comando quando escolhida uma peÃ§a.
     * Aqui deve-se mover a peÃ§a e fazer todas as verificaÃ§Ãµes necessÃ¡rias.
     * @param casa Casa escolhida pelo usuÃ¡rio/jogador.
     */
    public void escolherCasa(Casa casa) {   
        if(acaoInvalida(casa)){
            return;
        }
       
        // Perguntamos a casa qual é a sua peça.
        Peca peca = casa.getPeca();
        Casa proximaCasa = casa.obterCasaDestino(this);
        if(proximaCasa != null){
            if(proximaCasa.getPeca() != null) {
                Peca outraPeca = proximaCasa.getPeca();
                if(outraPeca.obterCor() != peca.obterCor()) 
                    capturarPeca(outraPeca);        
            }
            peca.mover(proximaCasa);
            if(proximaCasa.getQuantidadePecas() == 4)
                fimDeJogo = true;
            setJogadorDaVez();
        }
    }
    
    
    /**
     * Verifica se a ação do jogador diante do estado do jogo é valida
     */
    private boolean acaoInvalida(Casa casa){
        if(!rolouDados || !casa.possuiPeca()){
            return true;
        }
        Peca peca = casa.getPeca();
        
        if(!(getJogadorDaVez() == peca.obterCor())) {
            return true;
        }
        
        return false;
    }
        
    /**
     * Verifica se o jogador possui peças que podem ser jogadas
     */
    private boolean possivelJogar(){
        int jogadasValidas = 4;
        for (Peca peca : pecasDoJogo) {
            if(peca.obterCor() == getJogadorDaVez()){
                Casa casaDaPeca = peca.obterCasa();
                if(casaDaPeca == null || casaDaPeca.obterCasaDestino(this) == null){
                    jogadasValidas--;
                }
            }
        }
      
        if(jogadasValidas != 0){
            return true;
        }
        return false;
    }
   
    /**
     * Controla o turno dos jogadores.
     */
    public void setJogadorDaVez(){
        if(!dadosIguais() || !possivelJogar()){
            if(indiceTurno == 3)
                indiceTurno = 0;
            else{
                indiceTurno++; 
            }
        }
        rolouDados = false;
    }

    /**
     * Retorna o jogador que deve jogar os dados ou escolher uma peÃ§a.
     * @return Cor do jogador.
     */
    public String getJogadorDaVez() {
        return turnos[indiceTurno];
    }
    
    /**
     * O tabuleiro deste jogo.
     * @return O tabuleiro deste jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Retorna se os dados são iguais
     */
    public boolean dadosIguais(){
        if(dados[0].getValor() == dados[1].getValor()){
            return true;
        }
        return false;
    }

    /**
     * Retorna resultado da soma de todos os dados do array de dados.
     */
    public int somaDados(){
        int somaDados = 0;
        for (Dado dado : dados) {
            somaDados += dado.getValor();
        }
        
        return somaDados;
    }
    
    /**
     * Retorna o valor do menor do array de dados.
     */
    public int menorDado(){
        int menorValor = 6;
        for (Dado dado : dados) {
            int valorDoDado = dado.getValor();
            if(valorDoDado < menorValor ){
                menorValor = valorDoDado;
            }
        }
        
        return menorValor;
    }
    
    /**
     * Retorna o i-Ã©simo dado deste jogo entre 0 (inclusivo) e N (exclusivo).
     * Consulte obterQuantidadeDeDados() para verificar o valor de N
     * (isto Ã©, a quantidade de dados presentes).
     * @param i Indice do dado.
     * @return O i-Ã©simo dado deste jogo.
     */
    public Dado getDado(int i) {
        return dados[i];
    }
    
    /**
     * ObtÃ©m a quantidade de dados sendo usados neste jogo.
     * @return Quantidade de dados.
     */
    public int getQuantidadeDados() {
        return dados.length;
    }
}
