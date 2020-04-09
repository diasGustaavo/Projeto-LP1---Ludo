
/**
 * Escreva a descrição da classe CasaGuarita aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class CasaGuarita extends Casa{
    
    private Guarita guarita; 
    
    /**
     * Construtor para objetos da classe CasaGuarita
     */
    public CasaGuarita(Guarita guarita){
       super(guarita.obterCor(), null);
    }
    
    /**
     * Se a casa pertence a alguma guarita de algum jogador.
     * Consulte o método obterGuarita().
     * @return True caso pertença, false caso contrário.
     */
    public boolean pertenceGuarita() {
        return true;
    }
    
    public Casa getProximaCasa(Peca peca){
        return null;
    }
}
