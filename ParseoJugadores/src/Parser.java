import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;

import resources.Jugador;
import resources.Partido;

public class Parser {

	final private static String pathJSON = "/home/manu/Uni/PSI/json/jugadores/";
	final private static String pathCsvATP = "/home/manu/atp/tennis-prediction/Data/Non-Oncourt/atp_matches_";
	final private static String pathCsvQUAL = "/home/manu/atp/tennis_atp/atp_matches_qual_chall_";

	// Definimos el objeto que nos permite leer y escribir los ficheros JSON
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws IOException {

		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		for (int i = 2004; i <= 2015; i++) {
			System.out.println("Año: " + i);
			generarJugadores(pathCsvATP, Integer.toString(i));
			generarJugadores(pathCsvQUAL, Integer.toString(i));

			Updater up = new Updater(Integer.toString(i));
			up.update();
		}

		System.out.println("Fin del parseo de jugadores");
	} // Cierre main

	@SuppressWarnings("resource")
	private static void generarJugadores(String pathCsv, String ano) throws IOException {

		String path = pathJSON + ano + "/";
		CSVReader reader = new CSVReader(new FileReader(pathCsv + ano + ".csv"));
		String[] nextLine = null;

		nextLine = reader.readNext(); // Para saltarnos la línea de cabecera

		ArrayList<Partido> partidos = new ArrayList<Partido>();

		Jugador jugador = null;
		Partido partido = null;

		while ((nextLine = reader.readNext()) != null) {

			jugador = new Jugador();
			partido = new Partido();
			partidos = new ArrayList<Partido>();
			
			// Almacenamos los datos del partido
			partido.setTorneo(nextLine[1]);
			partido.setFecha(nextLine[5]);
			partido.setSuperficie(nextLine[2]);
			partido.setResultado(nextLine[27]);
			partido.setContrincante(nextLine[20]);
			partido.setGanador(true);

			// Si este campo está vacío implica que no hay datos del partido,
			// por lo que rellenamos con ceros
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
			}

			// Datos del ganador
			jugador.setNombre(nextLine[10]);
			jugador.setMano(nextLine[11]);
			jugador.setNacionalidad(nextLine[13]);

			// Si el campo está vacío ponemos un 0
			if (!nextLine[14].equals("")) {
				jugador.setEdad(Double.parseDouble(nextLine[14]));
			} else {
				jugador.setEdad(0);
			}

			escribirFichero(new File(path + toNombreFichero(nextLine[10]) + ".json"), jugador, partido, partidos);

			// Datos del perdedor
			jugador = new Jugador();
			partidos = new ArrayList<Partido>();

			jugador.setNombre(nextLine[20]);
			jugador.setMano(nextLine[21]);
			jugador.setNacionalidad(nextLine[23]);

			if (!nextLine[24].equals("")) {
				jugador.setEdad(Double.parseDouble(nextLine[24]));
			} else {
				jugador.setEdad(0);
			}

			// Los datos del partido son los mismos, sólo hay que modificar el
			// ganador y el contrincante
			partido.setGanador(false);
			partido.setContrincante(nextLine[10]);

			escribirFichero(new File(path + toNombreFichero(nextLine[20]) + ".json"), jugador, partido, partidos);
		} // Cierre while

		System.out.println("Final del parseo del año " + ano);
	} // Cierre generarJugadores
	
	private static void escribirFichero(File f, Jugador jugador, Partido partido, ArrayList<Partido> partidos) throws JsonGenerationException, JsonMappingException, IOException {
		if (!f.exists()) {
			partidos.add(partido);
			jugador.setPartidos(partidos);
			mapper.writeValue(f, jugador);
		} else {
			Jugador jugadorFichero = mapper.readValue(f, Jugador.class);

			if (!jugadorFichero.getPartidos().contains(partido)) {
				jugadorFichero.anadirPartido(partido);
				mapper.writeValue(f, jugadorFichero);
			}
		} // Cierre if File exists
	} // Cierre escribirFichero

	/**
	 * Transforma el nombre del jugador a un String de la forma
	 * "nombre_apellido", y convirtiendo todas las letras en minúsculas
	 * 
	 * @param nombre
	 *            String con el nombre del jugador
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
	@SuppressWarnings("unused")
	private static void crearTxt(ArrayList<String> text, ArrayList<String> superfi) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/manu/jugadores.txt")));
		Collections.sort(text);

		for (String i : text)
			writer.write(i + "\n");

		writer.flush();
		writer.close();

		writer = new BufferedWriter(new FileWriter(new File("/home/manu/superficies.txt")));
		Collections.sort(superfi);

		for (String i : superfi)
			writer.write(i + "\n");

		writer.flush();
		writer.close();
	} // Cierre crearTxt

} // Cierre class
