/**
 * 
 */
package resources;

/**
 * @author manu
 *
 */
public class Superficie {

	private String nombre = "";
	private double probVictoria = 0.0;
	private double probGanarPrimerSaque = 0.0;
	private double probGanarSegundoSaque = 0.0;
	private double probPrimerSaque = 0.0;
	private double probSegundoSaque = 0.0;
	private double probGanarPuntoSacando = 0.0;
	private double probGanarPuntoRestando = 0.0;
	private double probAce = 0.0;
	
	public Superficie() {
		
	}
	
	
	public Superficie (String nombre) {
		this.setNombre(nombre);
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the probVictoria
	 */
	public double getProbVictoria() {
		return probVictoria;
	}
	/**
	 * @param probVictoria the probVictoria to set
	 */
	public void setProbVictoria(double probVictoria) {
		this.probVictoria = probVictoria;
	}
	/**
	 * @return the probGanarPrimerSaque
	 */
	public double getProbGanarPrimerSaque() {
		return probGanarPrimerSaque;
	}
	/**
	 * @param probGanarPrimerSaque the probGanarPrimerSaque to set
	 */
	public void setProbGanarPrimerSaque(double probGanarPrimerSaque) {
		this.probGanarPrimerSaque = probGanarPrimerSaque;
	}
	/**
	 * @return the probGanarSegundoSaque
	 */
	public double getProbGanarSegundoSaque() {
		return probGanarSegundoSaque;
	}
	/**
	 * @param probGanarSegundoSaque the probGanarSegundoSaque to set
	 */
	public void setProbGanarSegundoSaque(double probGanarSegundoSaque) {
		this.probGanarSegundoSaque = probGanarSegundoSaque;
	}
	/**
	 * @return the probPrimerSaque
	 */
	public double getProbPrimerSaque() {
		return probPrimerSaque;
	}
	/**
	 * @param probPrimerSaque the probPrimerSaque to set
	 */
	public void setProbPrimerSaque(double probPrimerSaque) {
		this.probPrimerSaque = probPrimerSaque;
	}
	/**
	 * @return the probSegundoSaque
	 */
	public double getProbSegundoSaque() {
		return probSegundoSaque;
	}
	/**
	 * @param probSegundoSaque the probSegundoSaque to set
	 */
	public void setProbSegundoSaque(double probSegundoSaque) {
		this.probSegundoSaque = probSegundoSaque;
	}
	/**
	 * @return the probGanarPuntoSacando
	 */
	public double getProbGanarPuntoSacando() {
		return probGanarPuntoSacando;
	}
	/**
	 * @param probGanarPuntoSacando the probGanarPuntoSacando to set
	 */
	public void setProbGanarPuntoSacando(double probGanarPuntoSacando) {
		this.probGanarPuntoSacando = probGanarPuntoSacando;
	}
	/**
	 * @return the probGanarPuntoRestando
	 */
	public double getProbGanarPuntoRestando() {
		return probGanarPuntoRestando;
	}
	/**
	 * @param probGanarPuntoRestando the probGanarPuntoRestando to set
	 */
	public void setProbGanarPuntoRestando(double probGanarPuntoRestando) {
		this.probGanarPuntoRestando = probGanarPuntoRestando;
	}
	/**
	 * @return the probAce
	 */
	public double getProbAce() {
		return probAce;
	}
	/**
	 * @param probAce the probAce to set
	 */
	public void setProbAce(double probAce) {
		this.probAce = probAce;
	}
	
	
	
	
}
