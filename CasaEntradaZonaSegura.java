/**
 * Escreva a descrição da classe CasaEntradaZonaSegura aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class CasaEntradaZonaSegura extends Casa{
    private Casa casaSegura;
    
    /**
     * Construtor padrão da casa de entrada da zona segura
     */
    public CasaEntradaZonaSegura(){
        super("BRANCO", null);
    }
    
    public Casa getProximaCasa(Peca peca){
        if(peca.obterCor() == casaSegura.getCor()){
            return casaSegura;  
        }
        return casaSeguinte;
    }
    
    /**
     * Define a casa segura desta classe
     */
    public void setCasaSegura(Casa casa) {
        this.casaSegura = casa;
    }
    
    /**
     * Retorna a casa segura
     */
    public Casa getCasaSegura() {
        return casaSegura;
    }
    
    /**
     * Verifica se existe alguma casa especial de zona segura em frente a esta.
     * Consulte obterCasaSegura() para mais detalhes.
     * @return Se possui casa de zona segura em frente a esta.
     */
    public boolean ehEntradaZonaSegura() {
        return true;
    }
}
