/**
 * Representa uma peça do jogo.
 * 
 * @author Alan Moraes / alan@ci.ufpb.br
 * @author Victor Koehler / koehlervictor@cc.ci.ufpb.br
 */
public class Peca {
    
    // Cor da peça
    private final String cor;
    
    // Quantidade de peças que essa peça representa
    private int qtdPeças;
    
    // Direção que a peça deve seguir
    // true indica andar para frente
    // false indica andar para trá
    private boolean direcao;
    
    // Casa na qual a peça se encontra
    private Casa casa;
    
    /**
     * Construtor padrão para peças.
     * @param cor Cor da peça
     */
    public Peca(String cor) {
        this.cor = cor;
        this.casa = null;
        this.qtdPeças = 1;
        this.direcao = true;
    }
    
    
    /**
     * Cor da peça.
     * @return Cor da peça.
     */
    public String obterCor() {
        return cor;
    }
    
    /**
     * Casa na qual a peça se encontra.
     * @return Casa na qual a peça se encontra.
     */
    public Casa obterCasa() {
        return casa;
    }
    
    /**
     * Define o atributo casa desta classe
     */
    public void setCasa(Casa novaCasa){
        this.casa = novaCasa;
    }
    
    /**
     * Retira a peça da casa atual e coloca-a na casa de destino.
     * @param casaDestino 
     */
    public void mover(Casa casaDestino) {
        if (casa != null) {
            casa.setPeca(null);
        }
        if(casaDestino.possuiPeca()){
            Peca outraPeca = casaDestino.getPeca();
            outraPeca.criarCastelo(this);
            return;
        }
        casaDestino.setPeca(this);
        casa = casaDestino;
    }
    
    /**
     * Define o valor do atributo qtdPeças desta classe
     */
    public void setQtdPecas(int qtd){
        this.qtdPeças = qtd;
    }
    
    /**
     * Retorna a quantidade de peças que esta peça representa
     */
    public int getQtdPecas(){
        return this.qtdPeças;
    }
    
    /**
     * Método responsável pela criação de um castelo
     */
    public void criarCastelo(Peca pecaMovida){
       int alturaCastelo = pecaMovida.getQtdPecas() + this.getQtdPecas();       
       this.setQtdPecas(alturaCastelo);
       pecaMovida.setQtdPecas(1);
              
       Casa casaDePartida = pecaMovida.obterCasa();
       casaDePartida.setPeca(null); 
       pecaMovida.setCasa(null);
       
       Casa casaAtual = casa;
       casaAtual.setQuantidadePecas(alturaCastelo);
    }
    
    /**
     * Altera o estatus da peça para capturado
     */
    public void capturar(){    
       casa.setPeca(null);
       casa = null;
       qtdPeças = 1;
    }
    
    /**
     * Retorna se a peça deve andar para frente ou voltar
     */
    public boolean getSeguirEmFrente(){
        return direcao;
    }
    
    /**
     * Define se a peça deve andar para frente ou voltar
     */
    public void setAdiante(){
        this.direcao = true;
    }
    
    /**
     * Inverte a direção que a peça deve andar
     */
    public void inverterDirecao(){
        this.direcao = !direcao;
    }
}
