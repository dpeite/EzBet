import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 */

/**
 * @author manu
 *
 */
public class Main {
	
	static ArrayList<HashMap<String,Jugador>> jugadores = new ArrayList<HashMap<String,Jugador>>();
	static ArrayList<ArrayList<Jugador>> jugadoresList = new ArrayList<ArrayList<Jugador>>();
	private static Integer size = 5;

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Double[] beta = new Double[size];

		for (int i = 0; i < size; i++) {
			beta[i] = 1.0;	
		} 
		
		System.out.println(beta.length);
		
		for (int i = 2004; i < 2015; i++) {
			System.out.println(i);
			jugadores.add(getJugadores(Integer.toString(i)));
			beta = logisticRegression(jugadoresList.get(i - 2004), Integer.toString(i));
		}

		System.out.println("Todo va como dios manda");

		double por = (float) testeoSistema(beta) * 100.0;

		System.out.println("El porcentaje de acierto del sistema es del " + por + "%.");

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("Jug1: ");
			String jug1 = bf.readLine();

			System.out.println("Jug2: ");
			String jug2 = bf.readLine();
			
			System.out.println("Superficie: ");
			String superficie = bf.readLine();

			estimarResultado(jug1, jug2, superficie,beta);
		}

	}

	private static Double[] logisticRegression(ArrayList<Jugador> data, String year)
			throws JsonParseException, JsonMappingException, IOException {
		String path = "/home/manu/Uni/PSI/json/jugadores/" + year + "/";
		Jugador jug2 = null;

		Double[] beta = new Double[size];
		Double[] x = new Double[size];
		
		// Generamos el vector de pesos
		for (int i = 0; i < size; i++) {
			beta[i] = 1.0;	
		}

		double hipotesis = 0.0;
		ArrayList<Partido> partidos = null;
		ArrayList<Double> listaHipo = new ArrayList<Double>();
		ArrayList<Double[]> listaX = new ArrayList<Double[]>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		for (Jugador jug1 : data) {
			partidos = jug1.getPartidos();

			for (Partido part : partidos) {
				jug2 = mapper.readValue(new File(path + toNombreFichero(part.getContrincante()) + ".json"),
						Jugador.class);

				x = calcularCaracteristicas(jug1, jug2, part.getSuperficie());

				for (int j = 0; j < x.length; j++) {
					hipotesis += x[j] * beta[j];
				}

				listaHipo.add(sigmoid(hipotesis));
				listaX.add(x);
				hipotesis = 0.0;
				x = new Double[size];

			} // Cierre for Partidos
		} // Cierre for Jugadores

		beta = derivadaFuncionCoste(listaX, listaHipo, beta);

		listaHipo = new ArrayList<Double>();
		listaX = new ArrayList<Double[]>();

		return beta;
	}

	private static void estimarResultado(String jugador1, String jugador2, String superficie, Double[] beta)
			throws JsonParseException, JsonMappingException, IOException {

		Double[] x = new Double[size];
		double hipotesis = 0.0;

		Jugador jug1 = leerJSONJugador(jugador1);
		Jugador jug2 = leerJSONJugador(jugador2);

		x = calcularCaracteristicas(jug1, jug2, superficie);

		for (int j = 0; j < x.length; j++) {
			hipotesis += x[j] * beta[j];
		}

		System.out.println(
				"Nuestra estimación es que " + jugador1 + " tiene " + sigmoid(hipotesis) + " probabilidades de ganar.");

	} // Cierre estimarResultado

	private static double estimarResultado(Jugador jug1, Jugador jug2, String superficie, Double[] beta)
			throws JsonParseException, JsonMappingException, IOException {

		Double[] x = new Double[size];
		double hipotesis = 0.0;

		x = calcularCaracteristicas(jug1, jug2, superficie);

		for (int j = 0; j < x.length; j++) {
			hipotesis += x[j] * beta[j];
		}

		return sigmoid(hipotesis);
	} // Cierre estimarResultado

	/**
	 *  Devuelve el objeto tipo Jugador que está almacenado en formato JSON en el fichero correspondiente.
	 * @param jugador
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static Jugador leerJSONJugador(String jugador)
			throws JsonParseException, JsonMappingException, IOException {
		String path = "/home/manu/Uni/PSI/json/jugadores/2015/";
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		return mapper.readValue(new File(path + toNombreFichero(jugador) + ".json"), Jugador.class);
	} // Cierre leerJSONJugador

	private static Double[] derivadaFuncionCoste(ArrayList<Double[]> listX, ArrayList<Double> listHipotesis,
			Double[] theta) {

		double alpha = 0.05, aux0 = 0.0, aux1 = 0.0, aux2 = 0.0, aux3 = 0.0, aux4 = 0.0;

		for (int i = 0; i < listX.size(); i++) {
			aux0 += (listHipotesis.get(i) - 1) * listX.get(i)[0];
			aux1 += (listHipotesis.get(i) - 1) * listX.get(i)[1];
			aux2 += (listHipotesis.get(i) - 1) * listX.get(i)[2];
			aux3 += (listHipotesis.get(i) - 1) * listX.get(i)[3];
			aux4 += (listHipotesis.get(i) - 1) * listX.get(i)[4];
		}

		aux0 = aux0 / listX.size();
		aux1 = aux1 / listX.size();
		aux2 = aux2 / listX.size();
		aux3 = aux3 / listX.size();
		aux4 = aux4 / listX.size();

		theta[0] -= alpha * aux0;
		theta[1] -= alpha * aux1;
		theta[2] -= alpha * aux2;
		theta[3] -= alpha * aux3;
		theta[4] -= alpha * aux4;

		return theta;
	}

	private static HashMap<String,Jugador> getJugadores(String year)
			throws JsonParseException, JsonMappingException, IOException {

		String path = "/home/manu/Uni/PSI/json/jugadores/" + year;
		HashMap<String,Jugador> jugadores = new HashMap<String,Jugador>();
		ArrayList<Jugador> jugadorList = new ArrayList<Jugador>();
		ObjectMapper mapper = null;
		Jugador jugAux = null;

		mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				jugAux = mapper.readValue(file, Jugador.class);
				jugadores.put(toNombreFichero(jugAux.getNombre()), jugAux);
				jugadorList.add(jugAux);
			}
		}
		
		jugadoresList.add(jugadorList);

		return jugadores;
	} // Cierre getJugadores
	
	private static ArrayList<Jugador> getJugadoresArray(String year)
			throws JsonParseException, JsonMappingException, IOException {

		String path = "/home/manu/Uni/PSI/json/jugadores/" + year;
		ArrayList<Jugador> jugadorList = new ArrayList<Jugador>();
		ObjectMapper mapper = null;
		Jugador jugAux = null;

		mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				jugAux = mapper.readValue(file, Jugador.class);
				jugadorList.add(jugAux);
			}
		}

		return jugadorList;
	} // Cierre getJugadores

	private static double testeoSistema(Double[] beta) throws JsonParseException, JsonMappingException, IOException {
		String path = "/home/manu/Uni/PSI/json/jugadores/2015/";
		Jugador jug2 = null;
		Integer aciertos = 0, fallos = 0;
		double rendimiento = 0.0, desviacion = 0.0;

		double hipotesis = 0.0;
		ArrayList<Partido> partidos = null;

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		ArrayList<Jugador> data = getJugadoresArray("2015");

		for (Jugador jug1 : data) {
			partidos = jug1.getPartidos();

			for (Partido part : partidos) {

				// Obtenemos al contrincante
				jug2 = mapper.readValue(new File(path + toNombreFichero(part.getContrincante()) + ".json"),
						Jugador.class);

				hipotesis = estimarResultado(jug1, jug2, part.getSuperficie(), beta);

				if (part.getGanador()) {
					if (hipotesis > 0.5) {
						aciertos++;
					} else {
						desviacion += (0.5 - hipotesis);
						fallos++;
					}
				} else {
					if (hipotesis > 0.5) {
						desviacion += (hipotesis - 0.5);
						fallos++;
					} else {
						aciertos++;
					}
				} // Cierre if ganador
			} // Cierre for Partidos
		} // Cierre for Jugadores

		desviacion /= fallos;
		System.out.println("Aciertos: " + aciertos);
		System.out.println("Fallos: " + fallos);
		System.out.println("Desviación: " + desviacion);
		rendimiento = (float) aciertos / (aciertos + fallos);

		return rendimiento;

	} // Cierre testeoSistema
	
	/**
	 *  Calcula el valor de la función sigmoid para el parámetro dado
	 * @param hipotesis
	 * @return
	 */
	private static double sigmoid(double hipotesis) {
		return 1.0 / (1 + Math.pow(Math.E, (-hipotesis)));
	}

	/**
	 *  Devuelve el nombre del tenista en formato nombre_apellido (en minúsculas)
	 * @param nombre Nombre del tenista
	 * @return Nombre del tenista en el formato descrito
	 */
	private static String toNombreFichero(String nombre) {
		return nombre.toLowerCase().replace(" ", "_");
	}
	
	private static Double[] calcularCaracteristicas(Jugador jug1, Jugador jug2, String superficie) throws JsonParseException, JsonMappingException, IOException {
		
		Double[] x = new Double[size];
		
		Double wsp1 = jug1.getGanarPuntoSacando();
		Double wsp2 = jug2.getGanarPuntoSacando();
		Double wrp1 = jug1.getGanarPuntoRestando();
		Double wrp2 = jug2.getGanarPuntoRestando();

		Double direct1 = wsp1 - wrp2;
		Double direct2 = wsp2 - wrp1;

		Double serveadv = direct1 - direct2;

		Double complet1 = wsp1 * wrp1;
		Double complet2 = wsp2 * wrp2;

		Double complet = complet1 - complet2;
		Double probSuperficie = 0.0;
		
		switch(superficie) {
		
		case("Clay"): {
			probSuperficie = jug1.getProbClay() - jug2.getProbClay();
			break;
		}
		
		case("Grass"): {
			probSuperficie = jug1.getProbGrass() - jug2.getProbGrass();
			break;
			
		}
		
		case("Hard"): {
			probSuperficie = jug1.getProbHard() - jug2.getProbHard();
			break;
		}
		default: {
			probSuperficie = jug1.getProbOther() - jug2.getProbOther();
			
		}
		}

		x[0] = serveadv;
		x[1] = complet;
		x[2] = calcularDirect(jug1, jug2);
		x[3] = calcularAces(jug1, jug2);
		x[4] = probSuperficie;
		
		return x;
	} // Cierre calcularCaracteristicas

	private static double calcularDirect(Jugador jug1, Jugador jug2) throws JsonParseException, JsonMappingException, IOException {
		String nombre2 = jug2.getNombre();
		Integer victorias1 = 0, victorias2 = 0, totalPartidos = 0;
		double por1 = 0.0, por2 = 0.0;
		
		//for (int i = 0; i < jugadores.size(); i++) {
			
			Jugador jugador1 = jugadores.get(jugadores.size() - 1).get(toNombreFichero(jug1.getNombre()));
			
			if (jugador1 != null) {
				for (Partido part : jugador1.getPartidos()) {
					if (part.getContrincante().equals(nombre2)) {
						if (part.getGanador()) {
							victorias1++;
						} else {
							victorias2++;
						}
					}
				} // Cierre for Partido
			}

			
			
	//	} // Cierre for años
		
		/*File f = null;
		String path = "/home/manu/Uni/PSI/json/jugadores/";		
		Jugador jugadorAux = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT); 
		
		for (int i = 2004; i < 2015; i++) {	
			f = new File(path + Integer.toString(i) + "/" + toNombreFichero(jug1.getNombre()) + ".json");
			
			if (f.exists()) {
				jugadorAux = mapper.readValue(f, Jugador.class);
				
				for (Partido part : jugadorAux.getPartidos()) {
					if (part.getContrincante().equals(nombre2)) {
						if (part.getGanador()) {
							victorias1++;
						} else {
							victorias2++;
						}
					}
				} // Cierre for Partido
			} // Cierre if exists		
		} // Cierre for años
		*/
		
		
		totalPartidos = victorias1 + victorias2;

		if (totalPartidos == 0) {
			totalPartidos++;
		}

		por1 = (float) victorias1 / totalPartidos;
		por2 = (float) victorias2 / totalPartidos;

		return por1 - por2;

	} // Cierre calcularDirect
	
	private static double calcularAces(Jugador jug1, Jugador jug2) {
		return jug1.getProbAce() - jug2.getProbAce();
	}

} // Cierre class
