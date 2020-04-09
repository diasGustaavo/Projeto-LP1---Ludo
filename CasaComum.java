/**
 * Escreva a descrição da classe CasaBranca aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class CasaComum extends Casa { 
    /**
     * Construtor padrão de todas as peças brancas 
     */
    public CasaComum(){
        super("BRANCO", null);
    }
    
    /**
     * Construtor padrão de todas as peças de cor que possuem como proxima casa a casa seguinte
     */
    public CasaComum(String cor){
        super(cor, null);
    }
    
    public Casa getProximaCasa(Peca peca){
        return casaSeguinte;
    }
}
    