/**
 * 
 */
package resources;

/**
 * @author manu
 *
 */
public class Partido {
	
	private String torneo = "";
	private String fecha = "";
	private String superficie = "";
	private String resultado = "";
	private String contrincante = "";
	private Boolean ganador = true;
	
	private Integer winnerAces = 0;
	private Integer winnerPrimerSaqueDentro = 0;
	private Integer winnerPrimerSaqueGanado = 0;
	private Integer winnerSegundoSaqueGanado = 0;
	private Integer winnerDobleFalta = 0;
	private Integer winnerServingPoints = 0;
	private Integer winnerServingGames = 0;
	
	
	private Integer loserAces = 0;	
	private Integer loserPrimerSaqueDentro = 0;
	private Integer loserPrimerSaqueGanado = 0;
	private Integer loserSegundoSaqueGanado = 0;
	private Integer loserDobleFalta = 0;
	private Integer loserServingPoints = 0;
	private Integer loserServingGames = 0;

	
	
	
	/**
	 * @return the winnerDobleFalta
	 */
	public Integer getWinnerDobleFalta() {
		return winnerDobleFalta;
	}
	/**
	 * @param winnerDobleFalta the winnerDobleFalta to set
	 */
	public void setWinnerDobleFalta(Integer winnerDobleFalta) {
		this.winnerDobleFalta = winnerDobleFalta;
	}
	/**
	 * @return the loserDobleFalta
	 */
	public Integer getLoserDobleFalta() {
		return loserDobleFalta;
	}
	/**
	 * @param loserDobleFalta the loserDobleFalta to set
	 */
	public void setLoserDobleFalta(Integer loserDobleFalta) {
		this.loserDobleFalta = loserDobleFalta;
	}
	/**
	 * @return the winnerServingPoints
	 */
	public Integer getWinnerServingPoints() {
		return winnerServingPoints;
	}
	/**
	 * @param winnerServingPoints the winnerServingPoints to set
	 */
	public void setWinnerServingPoints(Integer winnerServingPoints) {
		this.winnerServingPoints = winnerServingPoints;
	}
	/**
	 * @return the loserServingPoints
	 */
	public Integer getLoserServingPoints() {
		return loserServingPoints;
	}
	/**
	 * @param loserServingPoints the loserServingPoints to set
	 */
	public void setLoserServingPoints(Integer loserServingPoints) {
		this.loserServingPoints = loserServingPoints;
	}
	/**
	 * @return the winnerPrimerSaqueDentro
	 */
	public Integer getWinnerPrimerSaqueDentro() {
		return winnerPrimerSaqueDentro;
	}
	/**
	 * @param winnerPrimerSaqueDentro the winnerPrimerSaqueDentro to set
	 */
	public void setWinnerPrimerSaqueDentro(Integer winnerPrimerSaqueDentro) {
		this.winnerPrimerSaqueDentro = winnerPrimerSaqueDentro;
	}
	/**
	 * @return the winnerPrimerSaqueGanado
	 */
	public Integer getWinnerPrimerSaqueGanado() {
		return winnerPrimerSaqueGanado;
	}
	/**
	 * @param winnerPrimerSaqueGanado the winnerPrimerSaqueGanado to set
	 */
	public void setWinnerPrimerSaqueGanado(Integer winnerPrimerSaqueGanado) {
		this.winnerPrimerSaqueGanado = winnerPrimerSaqueGanado;
	}
	/**
	 * @return the winnerSegundoSaqueGanado
	 */
	public Integer getWinnerSegundoSaqueGanado() {
		return winnerSegundoSaqueGanado;
	}
	/**
	 * @param winnerSegundoSaqueGanado the winnerSegundoSaqueGanado to set
	 */
	public void setWinnerSegundoSaqueGanado(Integer winnerSegundoSaqueGanado) {
		this.winnerSegundoSaqueGanado = winnerSegundoSaqueGanado;
	}
	/**
	 * @return the loserPrimerSaqueDentro
	 */
	public Integer getLoserPrimerSaqueDentro() {
		return loserPrimerSaqueDentro;
	}
	/**
	 * @param loserPrimerSaqueDentro the loserPrimerSaqueDentro to set
	 */
	public void setLoserPrimerSaqueDentro(Integer loserPrimerSaqueDentro) {
		this.loserPrimerSaqueDentro = loserPrimerSaqueDentro;
	}
	/**
	 * @return the loserPrimerSaqueGanado
	 */
	public Integer getLoserPrimerSaqueGanado() {
		return loserPrimerSaqueGanado;
	}
	/**
	 * @param loserPrimerSaqueGanado the loserPrimerSaqueGanado to set
	 */
	public void setLoserPrimerSaqueGanado(Integer loserPrimerSaqueGanado) {
		this.loserPrimerSaqueGanado = loserPrimerSaqueGanado;
	}
	/**
	 * @return the loserSegundoSaqueGanado
	 */
	public Integer getLoserSegundoSaqueGanado() {
		return loserSegundoSaqueGanado;
	}
	/**
	 * @param loserSegundoSaqueGanado the loserSegundoSaqueGanado to set
	 */
	public void setLoserSegundoSaqueGanado(Integer loserSegundoSaqueGanado) {
		this.loserSegundoSaqueGanado = loserSegundoSaqueGanado;
	}
	/**
	 * @return the winnerAces
	 */
	public Integer getWinnerAces() {
		return winnerAces;
	}
	/**
	 * @param winnerAces the winnerAces to set
	 */
	public void setWinnerAces(Integer winnerAces) {
		this.winnerAces = winnerAces;
	}
	/**
	 * @return the loserAces
	 */
	public Integer getLoserAces() {
		return loserAces;
	}
	/**
	 * @param loserAces the loserAces to set
	 */
	public void setLoserAces(Integer loserAces) {
		this.loserAces = loserAces;
	}
	/**
	 * @return the winnerServingGames
	 */
	public Integer getWinnerServingGames() {
		return winnerServingGames;
	}
	/**
	 * @param winnerServingGames the winnerServingGames to set
	 */
	public void setWinnerServingGames(Integer winnerServingGames) {
		this.winnerServingGames = winnerServingGames;
	}
	/**
	 * @return the loserServingGames
	 */
	public Integer getLoserServingGames() {
		return loserServingGames;
	}
	/**
	 * @param loserServingGames the loserServingGames to set
	 */
	public void setLoserServingGames(Integer loserServingGames) {
		this.loserServingGames = loserServingGames;
	}
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the ganador
	 */
	public Boolean getGanador() {
		return ganador;
	}
	/**
	 * @param ganador the ganador to set
	 */
	public void setGanador(Boolean ganador) {
		this.ganador = ganador;
	}

	/**
	 * @return the torneo
	 */
	public String getTorneo() {
		return torneo;
	}
	/**
	 * @param torneo the torneo to set
	 */
	public void setTorneo(String torneo) {
		this.torneo = torneo;
	}
	/**
	 * @return the superficie
	 */
	public String getSuperficie() {
		return superficie;
	}
	/**
	 * @param superficie the superficie to set
	 */
	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}
	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}
	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	/**
	 * @return the contrincante
	 */
	public String getContrincante() {
		return contrincante;
	}
	/**
	 * @param contrincante the contrincante to set
	 */
	public void setContrincante(String contrincante) {
		this.contrincante = contrincante;
	}
	
	
	public boolean equals (Object partido) {
		
		if (((Partido) partido).getFecha().equals(this.fecha) && ((Partido) partido).getContrincante().equals(this.contrincante)) {
			return true;
		} else {
			return false;
		}
	} // Cierre equals

}
