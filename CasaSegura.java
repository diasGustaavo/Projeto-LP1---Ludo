/**
 * Escreva a descrição da classe CasaSegura aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class CasaSegura extends Casa{
    /**
     * Construtor padrão da casa de inicio da zona segura
     */
    public CasaSegura(String cor){
        super(cor, null);
    }
    
    /**
     * Construtor padrão de todas as casas da zona segura que possuem casa anterior 
     */
    public CasaSegura(String cor, Casa anterior){
        super(cor, anterior);
    }
    
    public boolean ehCasaFinal(){
        if(casaSeguinte == null)
            return true;
        return false;
    }
    
    public Casa getProximaCasa(Peca peca){
        if(casaSeguinte == null){
            peca.inverterDirecao();
            return casaAnterior;
        }
        else if(casaAnterior == null){
            if(!peca.getSeguirEmFrente()){
                peca.inverterDirecao();
            }
            return casaSeguinte;
        }
        if(peca.getSeguirEmFrente()){
            return casaSeguinte;
        }
        return casaAnterior;
    }
}
