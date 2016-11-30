
import java.util.ArrayList;

import resources.Partido;
/**
 * 
 */
import resources.Superficie;

/**
 * @author manu
 *
 */
public class Jugador {
	
	private String nombre = "";
	private String mano = "";
	private String nacionalidad = "";
	private double edad = 0.0;
	private double ganar1Saque = 0.0;
	private double ganar2Saque = 0.0;
	private double primerSaque = 0.0;
	private double segundoSaque = 0.0;
	private double ganarPuntoSacando = 0.0;
	private double ganarPuntoRestando = 0.0;
	private double probAce = 0.0;
	private Superficie clay = new Superficie("Clay");
	private Superficie grass = new Superficie("Grass");
	private Superficie hard = new Superficie("Hard");
	private Superficie other = new Superficie("Other");
	private ArrayList<Partido> partidos = new ArrayList<Partido>();	

	
	
	public void setProbGanarClay(Double prob) {
		this.clay.setProbVictoria(prob);
	}
	
	public void setProbGanarGrass(Double prob) {
		this.grass.setProbVictoria(prob);
	}
	
	public void setProbGanarHard(Double prob) {
		this.hard.setProbVictoria(prob);
	}
	
	public void setProbGanarOther(Double prob) {
		this.other.setProbVictoria(prob);
	}
	
	
	/**
	 * @return the grass
	 */
	public Superficie getGrass() {
		return grass;
	}

	/**
	 * @param grass the grass to set
	 */
	public void setGrass(Superficie grass) {
		this.grass = grass;
	}

	/**
	 * @return the hard
	 */
	public Superficie getHard() {
		return hard;
	}

	/**
	 * @param hard the hard to set
	 */
	public void setHard(Superficie hard) {
		this.hard = hard;
	}

	/**
	 * @return the other
	 */
	public Superficie getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(Superficie other) {
		this.other = other;
	}

	/**
	 * @return the clay
	 */
	public Superficie getClay() {
		return clay;
	}

	/**
	 * @param clay the clay to set
	 */
	public void setClay(Superficie clay) {
		this.clay = clay;
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

	/**
	 * @return the ganarPuntoRestando
	 */
	public double getGanarPuntoRestando() {
		return ganarPuntoRestando;
	}

	/**
	 * @param ganarPuntoRestando the ganarPuntoRestando to set
	 */
	public void setGanarPuntoRestando(double ganarPuntoRestando) {
		this.ganarPuntoRestando = ganarPuntoRestando;
	}


	
	/**
	 * @return the ganarSacando
	 */
	public double getGanarPuntoSacando() {
		return ganarPuntoSacando;
	}

	/**
	 * @param ganarSacando the ganarSacando to set
	 */
	public void setGanarPuntoSacando(double ganarSacando) {
		this.ganarPuntoSacando = ganarSacando;
	}

	/**
	 * @return the ganar1Saque
	 */
	public double getGanar1Saque() {
		return ganar1Saque;
	}

	/**
	 * @param ganar1Saque the ganar1Saque to set
	 */
	public void setGanar1Saque(double ganar1Saque) {
		this.ganar1Saque = ganar1Saque;
	}

	/**
	 * @return the ganar2Saque
	 */
	public double getGanar2Saque() {
		return ganar2Saque;
	}

	/**
	 * @param ganar2Saque the ganar2Saque to set
	 */
	public void setGanar2Saque(double ganar2Saque) {
		this.ganar2Saque = ganar2Saque;
	}

	/**
	 * @return the primerSaque
	 */
	public double getPrimerSaque() {
		return primerSaque;
	}

	/**
	 * @param primerSaque the primerSaque to set
	 */
	public void setPrimerSaque(double primerSaque) {
		this.primerSaque = primerSaque;
	}

	/**
	 * @return the segundoSaque
	 */
	public double getSegundoSaque() {
		return segundoSaque;
	}

	/**
	 * @param segundoSaque the segundoSaque to set
	 */
	public void setSegundoSaque(double segundoSaque) {
		this.segundoSaque = segundoSaque;
	}

	/**
	 * @return the partidos
	 */
	public ArrayList<Partido> getPartidos() {
		return partidos;
	}
	
	/**
	 * @param partidos the partidos to set
	 */
	public void setPartidos(ArrayList<Partido> partidos) {
		this.partidos = partidos;
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
	 * @return the mano
	 */
	public String getMano() {
		return mano;
	}
	
	/**
	 * @param mano the mano to set
	 */
	public void setMano(String mano) {
		this.mano = mano;
	}
	
	/**
	 * @return the nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}
	
	/**
	 * @param nacionalidad the nacionalidad to set
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	/**
	 * @return the edad
	 */
	public double getEdad() {
		return edad;
	}
	
	/**
	 * @param edad the edad to set
	 */
	public void setEdad(double edad) {
		this.edad = edad;
	}
	
	public void anadirPartido(Partido partido) {
		
		this.partidos.add(partido);
	}
	
	public ArrayList<String> obtenerContrincantes() {
		
		ArrayList<String> contrincantes = new ArrayList<String>();
		
		for ( Partido partido: partidos) {
			contrincantes.add(partido.getContrincante());
			System.out.println(partido.getContrincante());	
		}

		return contrincantes;
		
	}

} // Cierre class
