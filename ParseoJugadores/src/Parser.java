import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;

import resources.Partido;

public class Parser {
	
	final private static String pathJSON = "/home/manu/Uni/PSI/json/jugadores/";
	final private static String pathCsvATP = "/home/manu/atp/tennis-prediction/Data/Non-Oncourt/atp_matches_";
	final private static String pathCsvQUAL = "/home/manu/atp/tennis_atp/atp_matches_qual_chall_";

	public static void main(String[] args) throws IOException {

		for (int i = 2004; i <= 2015; i++) {
			System.out.println(i);
			generarJugadores(pathCsvATP, Integer.toString(i));
			//System.exit(0);
			generarJugadores(pathCsvQUAL, Integer.toString(i));
			//generarJugadoresQuall(Integer.toString(i));

			Updater up = new Updater(Integer.toString(i));
			up.calcularProbPrimerSaque();
			up.calcularProbSegundoSaque();
			up.calcularProbGanarPrimerSaque();
			up.calcularProbGanarSegundoSaque();
			up.calcularProbGanarSacando();
			up.calcularProbGanarRestando();
			up.calcularProbAce();
			up.calcularProbGanarSuperficie();

			System.out.println("Fin del parseo de jugadores");

		}

	} // Cierre main

	private static void generarJugadores(String pathCsv, String ano) throws IOException {
		
		// Definimos el objeto que nos permite leer y escribir los ficheros JSON
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		String path = pathJSON + ano + "/";
		String fileGanador = "", filePerdedor = "";
		CSVReader reader = new CSVReader(
				new FileReader(pathCsv + ano + ".csv"));
		String[] nextLine;

		nextLine = reader.readNext(); // Para saltarnos la línea de cabecera

		ArrayList<Partido> partidos = new ArrayList<Partido>();

		Jugador jugador = null;;
		Partido partido = null;;

		while ((nextLine = reader.readNext()) != null) {

			// Datos del ganador
			jugador = new Jugador();
			partido = new Partido();
			partidos = new ArrayList<Partido>();

			fileGanador = toNombreFichero(nextLine[10]);

			jugador.setNombre(nextLine[10]);
			jugador.setMano(nextLine[11]);

			// Si el campo está vacío ponemos un 0
			if (!nextLine[14].equals("")) {
				jugador.setEdad(Double.parseDouble(nextLine[14]));
			} else {
				jugador.setEdad(0);
			}

			jugador.setNacionalidad(nextLine[13]);

			partido.setTorneo(nextLine[1]);
			partido.setFecha(nextLine[5]);
			partido.setSuperficie(nextLine[2]);
			partido.setResultado(nextLine[27]);
			partido.setContrincante(nextLine[20]);
			partido.setGanador(true);

			// Si este campo está vacío implica que no hay datos del partido, por lo que rellenamos con ceros
			if (!nextLine[31].equals("")) {
				partido.setWinnerAces(Integer.parseInt(nextLine[31]));
				partido.setWinnerDobleFalta(Integer.parseInt(nextLine[32]));
				partido.setWinnerServingPoints(Integer.parseInt(nextLine[33]));
				partido.setWinnerPrimerSaqueDentro(Integer.parseInt(nextLine[34]));
				partido.setWinnerPrimerSaqueGanado(Integer.parseInt(nextLine[35]));
				partido.setWinnerSegundoSaqueGanado(Integer.parseInt(nextLine[36]));
				partido.setWinnerServingGames(Integer.parseInt(nextLine[37]));

				partido.setLoserAces(Integer.parseInt(nextLine[40]));
				partido.setLoserDobleFalta(Integer.parseInt(nextLine[41]));
				partido.setLoserServingPoints(Integer.parseInt(nextLine[42]));
				partido.setLoserPrimerSaqueDentro(Integer.parseInt(nextLine[43]));
				partido.setLoserPrimerSaqueGanado(Integer.parseInt(nextLine[44]));
				partido.setLoserSegundoSaqueGanado(Integer.parseInt(nextLine[45]));
				partido.setLoserServingGames(Integer.parseInt(nextLine[46]));
			} else {
				partido.setWinnerAces(0);
				partido.setWinnerDobleFalta(0);
				partido.setWinnerServingPoints(0);
				partido.setWinnerPrimerSaqueDentro(0);
				partido.setWinnerPrimerSaqueGanado(0);
				partido.setWinnerSegundoSaqueGanado(0);
				partido.setWinnerServingGames(0);

				partido.setLoserAces(0);
				partido.setLoserDobleFalta(0);
				partido.setLoserServingPoints(0);
				partido.setLoserPrimerSaqueDentro(0);
				partido.setLoserPrimerSaqueGanado(0);
				partido.setLoserSegundoSaqueGanado(0);
				partido.setLoserServingGames(0);
			}

			File f = new File(path + fileGanador + ".json");
			
			// Si no existe el fichero lo creamos. Si existe lo leemos, y en caso de no tener el partido lo añadimos
			if (!f.exists()) {
				partidos.add(partido);
				jugador.setPartidos(partidos);
				mapper.writeValue(f, jugador);
			} else {

				Jugador jugadorFichero = mapper.readValue(f, Jugador.class);

				if (!jugadorFichero.getPartidos().contains(partido)) {
					System.out.println("No contiene el partido (Añadiendo...)");
					jugadorFichero.anadirPartido(partido);
					mapper.writeValue(f, jugadorFichero);
				}

			}

			// Datos del perdedor
			jugador = new Jugador();
			partido = new Partido();
			partidos = new ArrayList<Partido>();

			filePerdedor = toNombreFichero(nextLine[20]);

			jugador.setNombre(nextLine[20]);
			jugador.setMano(nextLine[21]);
			
			if (!nextLine[24].equals("")) {
				jugador.setEdad(Double.parseDouble(nextLine[24]));
			} else {
				jugador.setEdad(0);
			}
			
			jugador.setNacionalidad(nextLine[23]);

			partido.setTorneo(nextLine[1]);
			partido.setFecha(nextLine[5]);
			partido.setSuperficie(nextLine[2]);
			partido.setResultado(nextLine[27]);
			partido.setContrincante(nextLine[10]);
			partido.setGanador(false);

			if (!nextLine[31].equals("")) {
				partido.setWinnerAces(Integer.parseInt(nextLine[31]));
				partido.setWinnerDobleFalta(Integer.parseInt(nextLine[32]));
				partido.setWinnerServingPoints(Integer.parseInt(nextLine[33]));
				partido.setWinnerPrimerSaqueDentro(Integer.parseInt(nextLine[34]));
				partido.setWinnerPrimerSaqueGanado(Integer.parseInt(nextLine[35]));
				partido.setWinnerSegundoSaqueGanado(Integer.parseInt(nextLine[36]));
				partido.setWinnerServingGames(Integer.parseInt(nextLine[37]));

				partido.setLoserAces(Integer.parseInt(nextLine[40]));
				partido.setLoserDobleFalta(Integer.parseInt(nextLine[41]));
				partido.setLoserServingPoints(Integer.parseInt(nextLine[42]));
				partido.setLoserPrimerSaqueDentro(Integer.parseInt(nextLine[43]));
				partido.setLoserPrimerSaqueGanado(Integer.parseInt(nextLine[44]));
				partido.setLoserSegundoSaqueGanado(Integer.parseInt(nextLine[45]));
				partido.setLoserServingGames(Integer.parseInt(nextLine[46]));
			} else {
				partido.setWinnerAces(0);
				partido.setWinnerDobleFalta(0);
				partido.setWinnerServingPoints(0);
				partido.setWinnerPrimerSaqueDentro(0);
				partido.setWinnerPrimerSaqueGanado(0);
				partido.setWinnerSegundoSaqueGanado(0);
				partido.setWinnerServingGames(0);

				partido.setLoserAces(0);
				partido.setLoserDobleFalta(0);
				partido.setLoserServingPoints(0);
				partido.setLoserPrimerSaqueDentro(0);
				partido.setLoserPrimerSaqueGanado(0);
				partido.setLoserSegundoSaqueGanado(0);
				partido.setLoserServingGames(0);
			}

			f = new File(path + filePerdedor + ".json");

			if (!f.exists()) {
				partidos.add(partido);
				jugador.setPartidos(partidos);
				mapper.writeValue(f, jugador);
			} else {

				Jugador jugadorFichero = mapper.readValue(f, Jugador.class);

				if (!jugadorFichero.getPartidos().contains(partido)) {
					System.out.println("No contiene el partido (Añadiendo...)");
					jugadorFichero.anadirPartido(partido);
					mapper.writeValue(f, jugadorFichero);
				}
			}

		} // Cierre while

		System.out.println("End " + ano);

	} // Cierre generarJugadores

	private static void generarJugadoresQuall(String ano) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		String path = "/home/manu/Uni/PSI/json/jugadores/" + ano + "/";
		String fileGanador = "", filePerdedor = "";
		CSVReader reader = new CSVReader(
				new FileReader("/home/manu/atp/tennis_atp/atp_matches_qual_chall_" + ano + ".csv"));
		String[] nextLine;

		nextLine = reader.readNext();

		ArrayList<Partido> partidos = new ArrayList<Partido>();

		Jugador jugador = new Jugador();
		Partido partido = new Partido();

		while ((nextLine = reader.readNext()) != null) {

			// Datos del ganador
			jugador = new Jugador();
			partido = new Partido();
			partidos = new ArrayList<Partido>();

			fileGanador = toNombreFichero(nextLine[10]);

			jugador.setNombre(nextLine[10]);
			jugador.setMano(nextLine[11]);

			if (!nextLine[14].equals("")) {
				jugador.setEdad(Double.parseDouble(nextLine[14]));
			} else {
				jugador.setEdad(0);
			}

			jugador.setNacionalidad(nextLine[13]);

			partido.setTorneo(nextLine[1]);
			partido.setFecha(nextLine[5]);
			partido.setSuperficie(nextLine[2]);
			partido.setResultado(nextLine[27]);
			partido.setContrincante(nextLine[20]);
			partido.setGanador(true);

			if (!nextLine[31].equals("")) {
				partido.setWinnerAces(Integer.parseInt(nextLine[31]));
				partido.setWinnerDobleFalta(Integer.parseInt(nextLine[32]));
				partido.setWinnerServingPoints(Integer.parseInt(nextLine[33]));
				partido.setWinnerPrimerSaqueDentro(Integer.parseInt(nextLine[34]));
				partido.setWinnerPrimerSaqueGanado(Integer.parseInt(nextLine[35]));
				partido.setWinnerSegundoSaqueGanado(Integer.parseInt(nextLine[36]));
				partido.setWinnerServingGames(Integer.parseInt(nextLine[37]));

				partido.setLoserAces(Integer.parseInt(nextLine[40]));
				partido.setLoserDobleFalta(Integer.parseInt(nextLine[41]));
				partido.setLoserServingPoints(Integer.parseInt(nextLine[42]));
				partido.setLoserPrimerSaqueDentro(Integer.parseInt(nextLine[43]));
				partido.setLoserPrimerSaqueGanado(Integer.parseInt(nextLine[44]));
				partido.setLoserSegundoSaqueGanado(Integer.parseInt(nextLine[45]));
				partido.setLoserServingGames(Integer.parseInt(nextLine[46]));
			} else {
				partido.setWinnerAces(0);
				partido.setWinnerDobleFalta(0);
				partido.setWinnerServingPoints(0);
				partido.setWinnerPrimerSaqueDentro(0);
				partido.setWinnerPrimerSaqueGanado(0);
				partido.setWinnerSegundoSaqueGanado(0);
				partido.setWinnerServingGames(0);

				partido.setLoserAces(0);
				partido.setLoserDobleFalta(0);
				partido.setLoserServingPoints(0);
				partido.setLoserPrimerSaqueDentro(0);
				partido.setLoserPrimerSaqueGanado(0);
				partido.setLoserSegundoSaqueGanado(0);
				partido.setLoserServingGames(0);
			}

			File f = new File(path + fileGanador + ".json");

			if (!f.exists()) {
				partidos.add(partido);
				jugador.setPartidos(partidos);
				mapper.writeValue(f, jugador);
			} else {

				Jugador jugadorFichero = mapper.readValue(f, Jugador.class);

				if (!jugadorFichero.getPartidos().contains(partido)) {
					System.out.println("No contiene el partido (Añadiendo...)");
					jugadorFichero.anadirPartido(partido);
					mapper.writeValue(f, jugadorFichero);
				}

			}

			// Datos del perdedor
			jugador = new Jugador();
			partido = new Partido();
			partidos = new ArrayList<Partido>();

			filePerdedor = toNombreFichero(nextLine[20]);

			jugador.setNombre(nextLine[20]);
			jugador.setMano(nextLine[21]);
			if (!nextLine[24].equals("")) {
				jugador.setEdad(Double.parseDouble(nextLine[24]));
			} else {
				jugador.setEdad(0);
			}
			jugador.setNacionalidad(nextLine[23]);

			partido.setTorneo(nextLine[1]);
			partido.setFecha(nextLine[5]);
			partido.setSuperficie(nextLine[2]);
			partido.setResultado(nextLine[27]);
			partido.setContrincante(nextLine[10]);
			partido.setGanador(false);

			if (!nextLine[31].equals("")) {
				partido.setWinnerAces(Integer.parseInt(nextLine[31]));
				partido.setWinnerDobleFalta(Integer.parseInt(nextLine[32]));
				partido.setWinnerServingPoints(Integer.parseInt(nextLine[33]));
				partido.setWinnerPrimerSaqueDentro(Integer.parseInt(nextLine[34]));
				partido.setWinnerPrimerSaqueGanado(Integer.parseInt(nextLine[35]));
				partido.setWinnerSegundoSaqueGanado(Integer.parseInt(nextLine[36]));
				partido.setWinnerServingGames(Integer.parseInt(nextLine[37]));

				partido.setLoserAces(Integer.parseInt(nextLine[40]));
				partido.setLoserDobleFalta(Integer.parseInt(nextLine[41]));
				partido.setLoserServingPoints(Integer.parseInt(nextLine[42]));
				partido.setLoserPrimerSaqueDentro(Integer.parseInt(nextLine[43]));
				partido.setLoserPrimerSaqueGanado(Integer.parseInt(nextLine[44]));
				partido.setLoserSegundoSaqueGanado(Integer.parseInt(nextLine[45]));
				partido.setLoserServingGames(Integer.parseInt(nextLine[46]));
			} else {
				partido.setWinnerAces(0);
				partido.setWinnerDobleFalta(0);
				partido.setWinnerServingPoints(0);
				partido.setWinnerPrimerSaqueDentro(0);
				partido.setWinnerPrimerSaqueGanado(0);
				partido.setWinnerSegundoSaqueGanado(0);
				partido.setWinnerServingGames(0);

				partido.setLoserAces(0);
				partido.setLoserDobleFalta(0);
				partido.setLoserServingPoints(0);
				partido.setLoserPrimerSaqueDentro(0);
				partido.setLoserPrimerSaqueGanado(0);
				partido.setLoserSegundoSaqueGanado(0);
				partido.setLoserServingGames(0);
			}

			f = new File(path + filePerdedor + ".json");

			if (!f.exists()) {
				partidos.add(partido);
				jugador.setPartidos(partidos);
				mapper.writeValue(f, jugador);
			} else {

				Jugador jugadorFichero = mapper.readValue(f, Jugador.class);

				if (!jugadorFichero.getPartidos().contains(partido)) {
					System.out.println("No contiene el partido (Añadiendo...)");
					jugadorFichero.anadirPartido(partido);
					mapper.writeValue(f, jugadorFichero);
				}
			}

		} // Cierre while

		System.out.println("End " + ano);

	} // Cierre generarJugadores

	/**
	 *  Transforma el nombre del jugador a un String de la forma "nombre_apellido", y convirtiendo todas las letras en minúsculas
	 * @param nombre String con el nombre del jugador
	 * @return String con el nombre en el formato adecuado
	 */
	private static String toNombreFichero(String nombre) {
		return nombre.toLowerCase().replace(" ", "_");
	}

	/**
	 * Método auxiliar, usado para crear un fichero txt con los nombres de los
	 * jugadores.
	 * 
	 * @param text
	 * @param superfi
	 * @throws IOException
	 */
	private static void crearTxt(ArrayList<String> text, ArrayList<String> superfi) throws IOException {

		File fi = new File("/home/manu/jugadores.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fi));

		Collections.sort(text);
		System.out.println(text.size());

		for (String i : text) {
			writer.write(i + "\n");
		}

		writer.flush();
		writer.close();

		fi = new File("/home/manu/superficies.txt");
		writer = new BufferedWriter(new FileWriter(fi));

		Collections.sort(superfi);

		for (String i : superfi) {
			writer.write(i + "\n");
		}

		writer.flush();
		writer.close();
	}

}
