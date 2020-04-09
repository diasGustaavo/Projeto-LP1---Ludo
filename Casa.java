/**
 * Classe abstrata Casa - escreva a descrição da classe aqui
 * 
 * @author (seu nome aqui)
 * @version (versão ou data)
 */
public abstract class Casa
{
    // Semelhante as Listas Encadeadas, armazena ou não a casa seguinte, anterior e ou segura.
    protected Casa casaSeguinte;
    protected Casa casaAnterior;
    
    // Peça presentes neste casa
    protected Peca peca; 
    
    // Quantidade de peças presente na casa (Para casas finais).
    protected int qtdePecas; 
    
    // Consulte a classe Cores; -1 para neutro/nulo.
    protected final String cor;
    
    /**
     * Construtor padrão de todas as casas do jogo 
     */
    protected Casa(String cor, Casa anterior) {
        this.cor = cor;
        this.casaAnterior = anterior;
        this.casaSeguinte = null;
        this.peca = null;
        this.qtdePecas = 0;
    }

    /**
     * Se a casa possui peça.
     *
     * @return true se a casa possui peça; false caso contrário.
     */
    public boolean possuiPeca() {
        return getPeca() != null;
    }

    /**
     * Obtém a peça presente nesta casa, se houver.
     *
     * @return A peça se presente nesta casa ou null caso contrário.
     */
    public Peca getPeca() {
        return peca;
    }

    /**
     * Coloca a peça especificada nesta Casa.
     *
     * @param peca Peça que será inserida.
     * @return A peça que estava anteriormente nesta casa, ou null caso não
     * houvesse alguma.
     */
    public void setPeca(Peca peca) {
        this.peca = peca;
        if (peca == null) {
            this.qtdePecas = 0;
        }
        else{
            this.qtdePecas = peca.getQtdPecas();
        }
    }

    /**
     * Obtém a quantidade de peças presente nesta casa.
     * Este método é designado para casas finais, consulte o método casaFinal()
     * para mais detalhes.
     * @return Quantidade de peças na casa.
     */
    public int getQuantidadePecas() {
        return qtdePecas;
    }

    /**
     * Define a quantidade de peças presente nesta casa.
     * Este método é designado para casas finais, consulte o método casaFinal()
     * para mais detalhes.
     * @param quantidade Quantidade de peças na casa.
     */
    public void setQuantidadePecas(int quantidade) {
        this.qtdePecas = quantidade;
    }
    
    /**
     * Define a casa seguinte a esta.
     * @param seguinte A casa seguinte a esta.
     */
    public Casa getCasaSegura() {
        return null;
    }
    
    /**
     * Define a casa seguinte a esta.
     * @param seguinte A casa seguinte a esta.
     */
    public void setCasaSeguinte(Casa seguinte) {
        this.casaSeguinte = seguinte;
    }
    
    /**
     * Define a casa seguinte a esta.
     * @param seguinte A casa seguinte a esta.
     */
    public Casa getCasaSeguinte() {
        return casaSeguinte;
    }

    /**
     * Define a casa anterior a esta.
     * @param anterior A casa anterior a esta.
     */
    public void setCasaAnterior(Casa anterior) {
        this.casaAnterior = anterior;
    }
    
    /**
     * Método abstrato, responsável por retornar a proxima casa que uma peça deve ir
     * Portanto cada subclasse deverá defini-lo      
     */
    public abstract Casa getProximaCasa(Peca peca);
      
    /**
     * Retorna a casa que a peça passada pro metódo deve ir, de acordo com o estado do jogo
     */
    public Casa obterCasaDestino(Jogo jogo){
        if(ehCasaFinal()){
            return null;
        }
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        Casa proximaCasa = this;
        Casa casaAnterior;
        int resultDados;
        
        resultDados = (peca.getQtdPecas() > 1) ? jogo.menorDado() : jogo.somaDados();
       
        boolean sairGuarita = false;
        peca.setAdiante();
        for (int i = 0; i < resultDados && proximaCasa != null && !sairGuarita; i++) {
            casaAnterior = proximaCasa;
            sairGuarita = pertenceGuarita() && jogo.dadosIguais();
            proximaCasa = (sairGuarita) ? tabuleiro.getCasaInicio(cor) : proximaCasa.getProximaCasa(peca);
            if(proximaCasa != null && proximaCasa.possuiPeca()){
                Peca pecaObstaculo = proximaCasa.getPeca();
                boolean coresIguais = pecaObstaculo.obterCor() == peca.obterCor();
                boolean casteloMaiorAFrente = pecaObstaculo.getQtdPecas() > peca.getQtdPecas();
                if(!coresIguais && casteloMaiorAFrente){
                    if(i == 0){
                        return null;
                    }
                    return casaAnterior;
                }
                else if(peca.equals(pecaObstaculo) && i == resultDados-1){
                    return null;
                }
            }
        }
        return proximaCasa;
    }
   
    /**
     * Verifica se existe alguma casa especial de zona segura em frente a esta.
     * Consulte obterCasaSegura() para mais detalhes.
     * @return Se possui casa de zona segura em frente a esta.
     */
    public boolean ehEntradaZonaSegura() {
        return false;
    }
   
    /**
     * Se a é a última casa do jogador (isto é, se é a casa que o jogador almeja
     * colocar todas as suas peças).
     * Esta casa deve ter algumas propriedades particulares, como empilhar mais
     * de uma peça (isto é, pode conter mais de uma peça).
     * Explicação: Se a casa não tem sucessor mas tem antecessor, então é uma
     * casa terminal.
     * @return true se é a casa final.
     */
    public boolean ehCasaFinal() {
        return false;
    }
    
    /**
     * Se a casa pertence a alguma guarita de algum jogador.
     * Consulte o método obterGuarita().
     * @return True caso pertença, false caso contrário.
     */
    public boolean pertenceGuarita() {
        return false;
    }
    
    /**
     * Obtém a cor desta casa ou "Branco" caso seja neutra.
     * Consulte a classe Cores para mais detalhes.
     * 
     * @return Cor da casa.
     */
    public String getCor() {
        return cor;
    }
}