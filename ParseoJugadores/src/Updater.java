import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import resources.Jugador;
import resources.Partido;

/**
 * 
 */

/**
 *
 *
 */
public class Updater {

	private String path = null;
	private String ano = "";
	ObjectMapper mapper = null;
	

	public Updater(String ano) {
		this.mapper = new ObjectMapper();
		this.ano = ano;
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		path = "/home/manu/Uni/PSI/json/jugadores/" + this.ano + "/";
	}
	
	public void update() {
		
		try {
			this.calcularProbPrimerSaque();
			this.calcularProbSegundoSaque();
			this.calcularProbGanarPrimerSaque();
			this.calcularProbGanarSegundoSaque();
			this.calcularProbGanarSacando();
			this.calcularProbGanarRestando();
			this.calcularProbAce();
			this.calcularProbGanarSuperficie();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void calcularProbPrimerSaque() throws JsonParseException, JsonMappingException, IOException {
		
		double primerSaque = 0.0, primerSaqueClay = 0.0, primerSaqueGrass = 0.0, primerSaqueHard = 0.0, primerSaqueOther = 0.0;
		int aux = 0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugadorFichero = mapper.readValue(file, Jugador.class);
				aux = 0;
				primerSaque = 0;
				primerSaqueClay = 0;

				for (Partido part : jugadorFichero.getPartidos()) {

					// Si el jugador es el ganador cogemos los datos del ganador
					if (part.getGanador()) {
						if (part.getWinnerServingPoints() != 0) {
							primerSaque += (float) part.getWinnerPrimerSaqueDentro() / part.getWinnerServingPoints();
							aux++;
							
							switch (part.getSuperficie()) {
							case("Clay"): {
								primerSaqueClay += (float) part.getWinnerPrimerSaqueDentro() / part.getWinnerServingPoints();;
								break;
							}
							
							case("Grass"): {
								primerSaqueGrass += (float) part.getWinnerPrimerSaqueDentro() / part.getWinnerServingPoints();;
								break;
							}
							
							case("Hard"): {
								primerSaqueHard += (float) part.getWinnerPrimerSaqueDentro() / part.getWinnerServingPoints();;
								break;
							}
							
							default: {
								primerSaqueOther += (float) part.getWinnerPrimerSaqueDentro() / part.getWinnerServingPoints();;
								break;
							}
							} // Cierre switch
						}
					} else {
						if (part.getLoserServingPoints() != 0) {
							primerSaque += (float) part.getLoserPrimerSaqueDentro() / part.getLoserServingPoints();
							aux++;
							
							switch (part.getSuperficie()) {
							case("Clay"): {
								primerSaqueClay += (float) part.getLoserPrimerSaqueDentro() / part.getLoserServingPoints();
								break;
							}
							
							case("Grass"): {
								primerSaqueGrass += (float) part.getLoserPrimerSaqueDentro() / part.getLoserServingPoints();
								break;
							}
							
							case("Hard"): {
								primerSaqueHard += (float) part.getLoserPrimerSaqueDentro() / part.getLoserServingPoints();
								break;
							}
							
							default: {
								primerSaqueOther += (float) part.getLoserPrimerSaqueDentro() / part.getLoserServingPoints();
								break;
							}
							} // Cierre switch
						}
					}
				}

				if (aux == 0) {
					aux++;
				}				
				
				jugadorFichero.setPrimerSaque(primerSaque / aux);
				jugadorFichero.setProbPrimerSaqueClay(primerSaqueClay / aux);
				jugadorFichero.setProbPrimerSaqueGrass(primerSaqueGrass / aux);
				jugadorFichero.setProbPrimerSaqueHard(primerSaqueHard / aux);
				jugadorFichero.setProbPrimerSaqueOther(primerSaqueOther / aux);
				
				mapper.writeValue(file, jugadorFichero);
				
				primerSaque = 0.0;
				primerSaqueClay = 0.0;
				primerSaqueGrass = 0.0;
				primerSaqueHard = 0.0;
				primerSaqueOther = 0.0;
			} // Cierre if is file
		}

	} // Cierre calcularProbGanarPrimerSaque

	private void calcularProbSegundoSaque() throws JsonParseException, JsonMappingException, IOException {

		double segundoSaque = 0.0;
		double segundoSaqueClay = 0.0, segundoSaqueGrass = 0.0, segundoSaqueHard = 0.0, segundoSaqueOther = 0.0;
		int secondin = 0, aux = 0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugadorFichero = mapper.readValue(file, Jugador.class);
				aux = 0;
				segundoSaque = 0;

				for (Partido part : jugadorFichero.getPartidos()) {

					// Si el jugador es el ganador cogemos los datos del ganador
					if (part.getGanador()) {
						if (part.getWinnerServingPoints() != 0) {
							secondin = part.getWinnerServingPoints() - part.getWinnerDobleFalta()
									- part.getWinnerPrimerSaqueDentro();
							segundoSaque += (float) secondin / part.getWinnerServingPoints();
							aux++;
							
							switch (part.getSuperficie()) {
							case("Clay"): {
								segundoSaqueClay += (float) secondin / part.getWinnerServingPoints();;
								break;
							}
							
							case("Grass"): {
								segundoSaqueGrass += (float) secondin / part.getWinnerServingPoints();;
								break;
							}
							
							case("Hard"): {
								segundoSaqueHard += (float) secondin / part.getWinnerServingPoints();;
								break;
							}
							
							default: {
								segundoSaqueOther += (float) secondin / part.getWinnerServingPoints();;
								break;
							}
							} // Cierre switch
						}
					} else {
						if (part.getLoserServingPoints() != 0) {
							secondin = part.getLoserServingPoints() - part.getLoserDobleFalta()
									- part.getLoserPrimerSaqueDentro();
							segundoSaque += (float) secondin / part.getLoserServingPoints();
							aux++;
							
							switch (part.getSuperficie()) {
							case("Clay"): {
								segundoSaqueClay += (float) secondin / part.getLoserServingPoints();
								break;
							}
							
							case("Grass"): {
								segundoSaqueGrass += (float) secondin / part.getLoserServingPoints();
								break;
							}
							
							case("Hard"): {
								segundoSaqueHard += (float) secondin / part.getLoserServingPoints();
								break;
							}
							
							default: {
								segundoSaqueOther += (float) secondin / part.getLoserServingPoints();
								break;
							}
							} // Cierre switch
						}
					}

				}

				if (aux == 0) {
					aux++;
				}
				
				jugadorFichero.setSegundoSaque(segundoSaque / aux);
				jugadorFichero.setProbSegundoSaqueClay(segundoSaqueClay / aux);
				jugadorFichero.setProbSegundoSaqueGrass(segundoSaqueGrass / aux);
				jugadorFichero.setProbSegundoSaqueHard(segundoSaqueHard / aux);
				jugadorFichero.setProbSegundoSaqueOther(segundoSaqueOther / aux);
				
				mapper.writeValue(file, jugadorFichero);
				
				segundoSaque = 0.0;
				segundoSaqueClay = 0.0;
				segundoSaqueGrass = 0.0;
				segundoSaqueHard = 0.0;
				segundoSaqueOther = 0.0;
			} // Cierre if is file
		}

	} // Cierre calcularProbSegundoSaque

	private void calcularProbGanarPrimerSaque() throws JsonParseException, JsonMappingException, IOException {

		double ganarPrimerSaque = 0.0;
		double ganarPrimerSaqueClay = 0.0, ganarPrimerSaqueGrass = 0.0, ganarPrimerSaqueHard = 0.0, ganarPrimerSaqueOther = 0.0;
		int aux = 0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugadorFichero = mapper.readValue(file, Jugador.class);
				aux = 0;

				for (Partido part : jugadorFichero.getPartidos()) {

					// Si el jugador es el ganador cogemos los datos del ganador
					if (part.getGanador()) {
						if (part.getWinnerServingPoints() != 0 && part.getWinnerPrimerSaqueDentro() != 0) {
							ganarPrimerSaque += (float) part.getWinnerPrimerSaqueGanado()
									/ part.getWinnerPrimerSaqueDentro();
							aux++;
							
							switch (part.getSuperficie()) {
							case("Clay"): {
								ganarPrimerSaqueClay += (float) part.getWinnerPrimerSaqueGanado()
										/ part.getWinnerPrimerSaqueDentro();
								break;
							}
							
							case("Grass"): {
								ganarPrimerSaqueGrass += (float) part.getWinnerPrimerSaqueGanado()
										/ part.getWinnerPrimerSaqueDentro();
								break;
							}
							
							case("Hard"): {
								ganarPrimerSaqueHard += (float) part.getWinnerPrimerSaqueGanado()
										/ part.getWinnerPrimerSaqueDentro();
								break;
							}
							
							default: {
								ganarPrimerSaqueOther += (float) part.getWinnerPrimerSaqueGanado()
										/ part.getWinnerPrimerSaqueDentro();
								break;
							}
							} // Cierre switch
						}
					} else {
						if (part.getLoserServingPoints() != 0 && part.getLoserPrimerSaqueDentro() != 0) {
							ganarPrimerSaque += (float) part.getLoserPrimerSaqueGanado()
									/ part.getLoserPrimerSaqueDentro();
							aux++;
							
							switch (part.getSuperficie()) {
							case("Clay"): {
								ganarPrimerSaqueClay += (float) part.getLoserPrimerSaqueGanado()
										/ part.getLoserPrimerSaqueDentro();
								break;
							}
							
							case("Grass"): {
								ganarPrimerSaqueGrass += (float) part.getLoserPrimerSaqueGanado()
										/ part.getLoserPrimerSaqueDentro();
								break;
							}
							
							case("Hard"): {
								ganarPrimerSaqueHard += (float) part.getLoserPrimerSaqueGanado()
										/ part.getLoserPrimerSaqueDentro();
								break;
							}
							
							default: {
								ganarPrimerSaqueOther += (float) part.getLoserPrimerSaqueGanado()
										/ part.getLoserPrimerSaqueDentro();
								break;
							}
							} // Cierre switch
						}
					}
				} // Cierre for Partidos

				if (aux == 0) {
					aux++;
				}			
				
				jugadorFichero.setProbGanarPrimerSaqueClay(ganarPrimerSaqueClay / aux);
				jugadorFichero.setProbGanarPrimerSaqueGrass(ganarPrimerSaqueGrass / aux);
				jugadorFichero.setProbGanarPrimerSaqueHard(ganarPrimerSaqueHard / aux);
				jugadorFichero.setProbGanarPrimerSaqueOther(ganarPrimerSaqueOther / aux);
				jugadorFichero.setGanar1Saque(ganarPrimerSaque / aux);
				
				mapper.writeValue(file, jugadorFichero);
				
				ganarPrimerSaque = 0.0;
				ganarPrimerSaqueClay = 0.0;
				ganarPrimerSaqueGrass = 0.0;
				ganarPrimerSaqueHard = 0.0;
				ganarPrimerSaqueOther = 0.0;
			} // Cierre if is file
		}

	} // Cierre calcularProbGanarPrimerSaque

	private void calcularProbGanarSegundoSaque() throws JsonParseException, JsonMappingException, IOException {

		int secondin = 0, aux = 0;
		double ganarSegundoSaque = 0.0;
		double ganarSegundoSaqueClay = 0.0, ganarSegundoSaqueGrass = 0.0, ganarSegundoSaqueHard = 0.0, ganarSegundoSaqueOther = 0.0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugadorFichero = mapper.readValue(file, Jugador.class);
				aux = 0;
				ganarSegundoSaque = 0;

				for (Partido part : jugadorFichero.getPartidos()) {

					// Si el jugador es el ganador cogemos los datos del ganador
					if (part.getGanador()) {
						if (part.getWinnerServingPoints() != 0) {
							secondin = part.getWinnerServingPoints() - part.getWinnerDobleFalta()
									- part.getWinnerPrimerSaqueDentro();

							if (secondin != 0) {
								ganarSegundoSaque += (float) part.getWinnerSegundoSaqueGanado() / secondin;
								aux++;
								
								switch (part.getSuperficie()) {
								case("Clay"): {
									ganarSegundoSaqueClay += (float) part.getWinnerSegundoSaqueGanado() / secondin;
									break;
								}
								
								case("Grass"): {
									ganarSegundoSaqueGrass += (float) part.getWinnerSegundoSaqueGanado() / secondin;
									break;
								}
								
								case("Hard"): {
									ganarSegundoSaqueHard += (float) part.getWinnerSegundoSaqueGanado() / secondin;
									break;
								}
								
								default: {
									ganarSegundoSaqueOther += (float) part.getWinnerSegundoSaqueGanado() / secondin;
									break;
								}
								} // Cierre switch
							}

						}
					} else {
						if (part.getLoserServingPoints() != 0) {
							secondin = part.getLoserServingPoints() - part.getLoserDobleFalta()
									- part.getLoserPrimerSaqueDentro();

							if (secondin != 0) {
								ganarSegundoSaque += (float) part.getLoserSegundoSaqueGanado() / secondin;
								aux++;
								
								switch (part.getSuperficie()) {
								case("Clay"): {
									ganarSegundoSaqueClay += (float) part.getLoserSegundoSaqueGanado() / secondin;
									break;
								}
								
								case("Grass"): {
									ganarSegundoSaqueGrass += (float) part.getLoserSegundoSaqueGanado() / secondin;
									break;
								}
								
								case("Hard"): {
									ganarSegundoSaqueHard += (float) part.getLoserSegundoSaqueGanado() / secondin;
									break;
								}
								
								default: {
									ganarSegundoSaqueOther += (float) part.getLoserSegundoSaqueGanado() / secondin;
									break;
								}
								} // Cierre switch
							}
						}
					}
				} // Cierre for Partido

				if (aux == 0) {
					aux++;
				}
					
				jugadorFichero.setGanar2Saque(ganarSegundoSaque / aux);
				jugadorFichero.setProbGanarSegundoSaqueClay(ganarSegundoSaqueClay / aux);
				jugadorFichero.setProbGanarSegundoSaqueGrass(ganarSegundoSaqueGrass / aux);
				jugadorFichero.setProbGanarSegundoSaqueHard(ganarSegundoSaqueHard / aux);
				jugadorFichero.setProbGanarSegundoSaqueOther(ganarSegundoSaqueOther / aux);

				mapper.writeValue(file, jugadorFichero);
				
				ganarSegundoSaque = 0.0;
				ganarSegundoSaqueClay = 0.0;
				ganarSegundoSaqueGrass = 0.0;
				ganarSegundoSaqueHard = 0.0;
				ganarSegundoSaqueOther = 0.0;
			}
		}

	} // Cierre calcularProbGanarSegundoSaque

	private void calcularProbGanarSacando() throws JsonParseException, JsonMappingException, IOException {

		double ganarPuntoSacando = 0.0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugador = mapper.readValue(file, Jugador.class);

				ganarPuntoSacando = jugador.getGanar1Saque() * jugador.getPrimerSaque();
				ganarPuntoSacando += jugador.getGanar2Saque() * jugador.getSegundoSaque();
				jugador.setGanarPuntoSacando(ganarPuntoSacando);
				
				ganarPuntoSacando = 0.0;
				ganarPuntoSacando = jugador.getClay().getProbGanarPrimerSaque() * jugador.getClay().getProbPrimerSaque();
				ganarPuntoSacando += jugador.getClay().getProbGanarSegundoSaque() * jugador.getClay().getProbSegundoSaque();
				jugador.setProbGanarPuntoSacandoClay(ganarPuntoSacando);
				
				ganarPuntoSacando = 0.0;
				ganarPuntoSacando = jugador.getGrass().getProbGanarPrimerSaque() * jugador.getGrass().getProbPrimerSaque();
				ganarPuntoSacando += jugador.getGrass().getProbGanarSegundoSaque() * jugador.getGrass().getProbSegundoSaque();
				jugador.setProbGanarPuntoSacandoGrass(ganarPuntoSacando);
				
				ganarPuntoSacando = 0.0;
				ganarPuntoSacando = jugador.getHard().getProbGanarPrimerSaque() * jugador.getHard().getProbPrimerSaque();
				ganarPuntoSacando += jugador.getHard().getProbGanarSegundoSaque() * jugador.getHard().getProbSegundoSaque();
				jugador.setProbGanarPuntoSacandoHard(ganarPuntoSacando);
				
				ganarPuntoSacando = 0.0;
				ganarPuntoSacando = jugador.getOther().getProbGanarPrimerSaque() * jugador.getOther().getProbPrimerSaque();
				ganarPuntoSacando += jugador.getOther().getProbGanarSegundoSaque() * jugador.getOther().getProbSegundoSaque();
				jugador.setProbGanarPuntoSacandoOther(ganarPuntoSacando);

				
				mapper.writeValue(file, jugador);
			}
		}

	} // Cierre calcularProbGanarSacando

	private void calcularProbGanarRestando() throws JsonParseException, JsonMappingException, IOException {

		Integer tamPartidos = 0;
		double ganarPuntoRestando = 0.0;
		double ganarPuntoSacandoRivalClay = 0.0, ganarPuntoSacandoRivalGrass = 0.0, ganarPuntoSacandoRivalHard = 0.0, ganarPuntoSacandoRivalOther = 0.0;
		double ganarPuntoRestandoClay = 0.0, ganarPuntoRestandoGrass = 0.0, ganarPuntoRestandoHard = 0.0, ganarPuntoRestandoOther = 0.0;
		double ganarPuntoSacandoRival = 0.0;

		double ganarPrimerSaque = 0.0, primerSaque = 0.0;
		double ganarSegundoSaque = 0.0, segundoSaque = 0.0;
		double secondin = 0.0;

		ArrayList<Partido> partidos = null;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugadorFichero = mapper.readValue(file, Jugador.class);

				partidos = jugadorFichero.getPartidos();
				tamPartidos = partidos.size();

				for (Partido part : partidos) {

					// Si soy el ganador mi rival es el perdedor
					if (part.getGanador()) {

						secondin = part.getLoserServingPoints() - part.getLoserDobleFalta()
								- part.getLoserPrimerSaqueDentro();

						if (part.getLoserPrimerSaqueDentro() != 0 && secondin != 0
								&& part.getLoserServingPoints() != 0) {
							ganarPrimerSaque = (float) part.getLoserPrimerSaqueGanado()
									/ part.getLoserPrimerSaqueDentro();

							ganarSegundoSaque = (float) part.getLoserSegundoSaqueGanado() / secondin;

							primerSaque = (float) part.getLoserPrimerSaqueDentro() / part.getLoserServingPoints();
							segundoSaque = (float) secondin / part.getLoserServingPoints();
						} else {
							tamPartidos--;
							continue;
						}

					} else {

						secondin = part.getWinnerServingPoints() - part.getWinnerDobleFalta()
								- part.getWinnerPrimerSaqueDentro();

						if (part.getWinnerPrimerSaqueDentro() != 0 && secondin != 0
								&& part.getWinnerServingPoints() != 0) {
							ganarPrimerSaque = (float) part.getWinnerPrimerSaqueGanado()
									/ part.getWinnerPrimerSaqueDentro();

							ganarSegundoSaque = (float) part.getWinnerSegundoSaqueGanado() / secondin;

							primerSaque = (float) part.getWinnerPrimerSaqueDentro() / part.getWinnerServingPoints();
							segundoSaque = (float) secondin / part.getWinnerServingPoints();

						} else {
							tamPartidos--;
							continue;
						}
					} // Cierre if ganador

					ganarPuntoSacandoRival = ganarPrimerSaque * primerSaque;
					ganarPuntoSacandoRival += ganarSegundoSaque * segundoSaque;
					
					switch (part.getSuperficie()) {
					case("Clay"): {
						ganarPuntoSacandoRivalClay += ganarPrimerSaque * primerSaque + ganarSegundoSaque * segundoSaque;
						break;
					}
					
					case("Grass"): {
						ganarPuntoSacandoRivalGrass += ganarPrimerSaque * primerSaque + ganarSegundoSaque * segundoSaque;
						break;
					}
					
					case("Hard"): {
						ganarPuntoSacandoRivalHard += ganarPrimerSaque * primerSaque + ganarSegundoSaque * segundoSaque;
						break;
					}
					
					default: {
						ganarPuntoSacandoRivalOther += ganarPrimerSaque * primerSaque + ganarSegundoSaque * segundoSaque;
						break;
					}
					} // Cierre switch

					ganarPuntoRestando = ganarPuntoRestando + 1 - ganarPuntoSacandoRival;
					ganarPuntoRestandoClay = ganarPuntoRestandoClay + 1 - ganarPuntoSacandoRivalClay;
					ganarPuntoRestandoGrass = ganarPuntoRestandoGrass + 1 - ganarPuntoSacandoRivalGrass;
					ganarPuntoRestandoHard = ganarPuntoRestandoHard + 1 - ganarPuntoSacandoRivalHard;
					ganarPuntoRestandoOther = ganarPuntoRestandoOther + 1 - ganarPuntoSacandoRivalOther;

					ganarPuntoSacandoRival = 0.0;
					ganarPuntoSacandoRivalClay = 0.0;
					ganarPuntoSacandoRivalGrass = 0.0;
					ganarPuntoSacandoRivalHard = 0.0;
					ganarPuntoSacandoRivalOther = 0.0;
				} // Cierre for Partidos

				if (tamPartidos == 0) {
					tamPartidos++;
				}
				
				jugadorFichero.setGanarPuntoRestando(ganarPuntoRestando / tamPartidos);
				jugadorFichero.setProbGanarPuntoRestandoClay(ganarPuntoRestandoClay / tamPartidos);
				jugadorFichero.setProbGanarPuntoRestandoGrass(ganarPuntoRestandoGrass / tamPartidos);
				jugadorFichero.setProbGanarPuntoRestandoHard(ganarPuntoRestandoHard / tamPartidos);
				jugadorFichero.setProbGanarPuntoRestandoOther(ganarPuntoRestandoOther / tamPartidos);
				
				mapper.writeValue(file, jugadorFichero);

				ganarPuntoRestando = 0.0;
				ganarPuntoRestandoClay = 0.0;
				ganarPuntoRestandoGrass = 0.0;
				ganarPuntoRestandoHard = 0.0;
				ganarPuntoRestandoOther = 0.0;
			}
		}

	}

	private void calcularProbAce() throws JsonParseException, JsonMappingException, IOException {

		ArrayList<Partido> partidos = null;
		Integer numAce = 0, numServingPoints = 0;
		double probAce = 0.0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugadorFichero = mapper.readValue(file, Jugador.class);

				partidos = jugadorFichero.getPartidos();

				for (Partido part : partidos) {

					if (part.getGanador()) {
						numAce += part.getWinnerAces();
						numServingPoints += part.getWinnerServingPoints();
					} else {
						numAce += part.getLoserAces();
						numServingPoints += part.getLoserServingPoints();
					}

				}

				if (numServingPoints == 0) {
					probAce = 0.0;
				} else {
					probAce = (float) numAce / numServingPoints;
				}

				jugadorFichero.setProbAce(probAce);
				mapper.writeValue(file, jugadorFichero);

				probAce = 0.0;
				numAce = 0;
				numServingPoints = 0;
			}
		}

	} // Cierre calularProbAce

	public void calcularProbGanarSuperficie() throws JsonParseException, JsonMappingException, IOException {

		ArrayList<Partido> partidos = null;
		Integer numClay = 0, numGrass = 0, numHard = 0, numOther = 0;
		Integer numLosClay = 0, numLosGrass = 0, numLosHard = 0, numLosOther = 0;
		double probClay = 0.0, probGrass = 0.0, probHard = 0.0, probOther = 0.0;

		for (File file : (new File(path)).listFiles()) {
			if (file.isFile()) {

				Jugador jugador = mapper.readValue(file, Jugador.class);

				partidos = jugador.getPartidos();

				for (Partido part : partidos) {
					
					if(part.getGanador()) {
						switch (part.getSuperficie()) {
						case ("Clay"): {
							numClay++;
							break;
						}

						case ("Grass"): {
							numGrass++;
							break;

						}

						case ("Hard"): {
							numHard++;
							break;
						}

						default: {
							numOther++;
							break;
						}
						} // Cierre switch
					} else {
						switch (part.getSuperficie()) {
						case ("Clay"): {
							numLosClay++;
							break;
						}

						case ("Grass"): {
							numLosGrass++;
							break;

						}

						case ("Hard"): {
							numLosHard++;
							break;
						}

						default: {
							numLosOther++; 
							break;
						}
						} // Cierre switch
					}

				}
			

				if(numClay + numLosClay != 0){
					probClay = (float) numClay / (numClay + numLosClay);}
					
				if(numGrass + numLosGrass != 0)
					probGrass = (float) numGrass / (numGrass + numLosGrass);	

				
				if(numHard + numLosHard != 0) 
					probHard = (float) numHard / (numHard + numLosHard);	

				
				if(numOther + numLosOther != 0) 
					probOther = (float) numOther / (numOther + numLosOther);	

				jugador.setProbGanarClay(probClay);
				jugador.setProbGanarGrass(probGrass);
				jugador.setProbGanarHard(probHard);
				jugador.setProbGanarOther(probOther);
				
				mapper.writeValue(file, jugador);

				numClay = 0;
				numGrass = 0;
				numHard = 0;
				numOther = 0;
				
				numLosClay = 0;
				numLosGrass = 0;
				numLosHard = 0;
				numLosOther = 0;
				
				probClay = 0.0;
				probGrass = 0.0;
				probHard = 0.0;
				probOther = 0.0;
			}
		}

	} // Cierre calularProbSup

} // Cierre class
