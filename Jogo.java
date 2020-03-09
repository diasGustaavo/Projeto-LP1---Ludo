/**
 * Implementa as mecânicas e regras do jogo Ludo.
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
    
    //Controle de saida da guarita
    boolean saiuGuarita = false;

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

        // corDaVez = 0;
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
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando para jogar os dados.
     * Aqui deve-se jogar os dados e fazer todas as verificações necessárias.
     */
    public void rolarDados() {

        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR NO DADO DESENHADO NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        
        if(rolouDados){
            return;
        }
        
        // Aqui percorremos cada dado para lançá-lo individualmente.
        for (Dado dado : dados) {
            dado.rolar();
        }
        rolouDados = true;
        
        if(!possivelJogar()){
            setJogadorDaVez();
            return;
        }
    }
    
    private boolean dadosIguais(){
        if(dados[0].getValor() == dados[1].getValor()){
            return true;
        }
        return false;
    }
    
    public void moverParaGuarita(Peca pecaCapturada){
        Guarita guaritaPecaCapturada = tabuleiro.getGuarita(pecaCapturada.obterCor());
        for (Casa casaGuarita : guaritaPecaCapturada.obterTodasAsCasas()) {
           if(casaGuarita.getPeca() == null){
                pecaCapturada.mover(casaGuarita);
                break;
            }
        }
    }    
    
    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando quando escolhida uma peça.
     * Aqui deve-se mover a peça e fazer todas as verificações necessárias.
     * @param casa Casa escolhida pelo usuário/jogador.
     */
    public void escolherCasa(Casa casa) {   
        if(!rolouDados){
            return;
        }
        
        if (!casa.possuiPeca()) {
            return;
        }
        
        if(casa.pertenceGuarita() && !dadosIguais()){
            return;
        }
        
        if(casa.ehCasaFinal()){
            return;
        }
        
        // Perguntamos à casa qual é a peça.
        Peca peca = casa.getPeca();
        
        String corPeca = peca.obterCor();
        if(!(getJogadorDaVez() == corPeca)){
            return;
        }
        
        Casa casaInicioDoJogador = tabuleiro.getCasaInicio(corPeca);
        if(saiuGuarita && casaInicioDoJogador != casa){
            return;
        }
        
        Casa proximaCasa;
        boolean saiuCasaInicio = false;
        if(casa.pertenceGuarita() && dadosIguais()){
            proximaCasa = casaInicioDoJogador;
            saiuGuarita = true;
        }
        else{
            proximaCasa = percorrerCasas(casa);
            saiuCasaInicio = true;
        }
       
        if (proximaCasa != null) {
            if(proximaCasa.getPeca() != null) {
                Peca outraPeca = proximaCasa.getPeca();
                if(outraPeca.obterCor() != peca.obterCor()) {
                    moverParaGuarita(outraPeca);
                    peca.mover(proximaCasa);        
                }
                else if(proximaCasa.ehCasaFinal()){
                    int quantidadePecas = proximaCasa.getQuantidadePecas();
                    peca.mover(proximaCasa);
                    proximaCasa.setQuantidadePecas(quantidadePecas + 1);
                }
                else{
                    return;
                }
            }
            else {
                peca.mover(proximaCasa);
            }
            if(saiuCasaInicio){
                saiuGuarita = false;
            }
        }    
        setJogadorDaVez();
    }
    
    private int somaDados(){
        int somaDados = 0;
        for (Dado dado : dados) {
            somaDados += dado.getValor();
        }
        
        return somaDados;
    }
    
    
    private Casa percorrerCasas(Casa proximaCasa){
        // Percorreremos N casas.
        Peca peca = proximaCasa.getPeca();
        boolean reverse = false;
        for (int i = 0; i < somaDados() && proximaCasa != null; i++) {
            if(proximaCasa.ehEntradaZonaSegura() && proximaCasa.getCasaSegura().getCor() == peca.obterCor()){
                proximaCasa = proximaCasa.getCasaSegura();
            }
            else if(proximaCasa.getCasaAnterior() == null){
                proximaCasa = proximaCasa.getCasaSeguinte();
                reverse = false;
            }
            else if(proximaCasa.ehCasaFinal() || reverse){
                proximaCasa = proximaCasa.getCasaAnterior();
                reverse = true;
            }   
            else{
                proximaCasa = proximaCasa.getCasaSeguinte();
            }
        }
        
        return proximaCasa;
    }
    
    private int quemGanhou(){
        int quantosVerdes = 0;
        int quantosVermelhos = 0;
        int quantosAmarelos = 0;
        int quantosAzul = 0;
        for (Peca peca : pecasDoJogo){
            Casa casaDaPeca = peca.obterCasa();
            if(casaDaPeca.ehCasaFinal() && peca.obterCor() == "VERDE"){
                ++quantosVerdes;
            }
            else if(casaDaPeca.ehCasaFinal() && peca.obterCor() == "VERMELHO"){
                ++quantosVermelhos;
            }
            else if(casaDaPeca.ehCasaFinal() && peca.obterCor() == "AZUL"){
                ++quantosAzul;
            }
            else if(casaDaPeca.ehCasaFinal() && peca.obterCor() == "AMARELO"){
                ++quantosAmarelos;
            }
        }
        
        if(quantosVerdes == 4){
            return 0;
        }
        else if(quantosVermelhos == 4){
            return 1;
        }
        else if(quantosAzul == 4){
            return 2;
        }
        else if(quantosAmarelos == 4){
            return 3;
        }
        else{  
            return 666;
        }
    }
    
    private boolean possivelJogar(){
        int jogadasValidas = 4;
        for (Peca peca : pecasDoJogo) {
            if(peca.obterCor() == getJogadorDaVez()){
                Casa casaDaPeca = peca.obterCasa();
                if(casaDaPeca.pertenceGuarita() && !dadosIguais()){
                    jogadasValidas--;
                }
                else if(casaDaPeca.ehCasaFinal()){
                    jogadasValidas--;
                }
                else{
                    Casa proximaCasa = percorrerCasas(casaDaPeca);
                    if(proximaCasa != null && proximaCasa.getPeca() != null) {
                        Peca outraPeca = proximaCasa.getPeca();
                        boolean coresIguais = peca.obterCor() == outraPeca.obterCor();
                        if(!proximaCasa.ehCasaFinal() && coresIguais) {  
                            jogadasValidas--;      
                        }
                    }
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
     * 
     */
    public void setJogadorDaVez(){
        if(quemGanhou() != 666){
            indiceTurno = quemGanhou();
        }
        else if(!dadosIguais() || !possivelJogar()){
            if(indiceTurno == 3)
                indiceTurno = 0;
            else{
                indiceTurno++; 
            }
        }
        rolouDados = false;
    }

    /**
     * Retorna o jogador que deve jogar os dados ou escolher uma peça.
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
     * Retorna o i-ésimo dado deste jogo entre 0 (inclusivo) e N (exclusivo).
     * Consulte obterQuantidadeDeDados() para verificar o valor de N
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
