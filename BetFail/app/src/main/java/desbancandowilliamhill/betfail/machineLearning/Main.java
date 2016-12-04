package desbancandowilliamhill.betfail.machineLearning;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import desbancandowilliamhill.betfail.R;


public class Main {

	static ArrayList<HashMap<String,Jugador>> jugadores = new ArrayList<>();
	static ArrayList<ArrayList<Jugador>> jugadoresList = new ArrayList<>();
	private static Integer size = 5;

	Context context;
    View view;
    StringWriter sw;
    ObjectMapper mapper = new ObjectMapper();

	public Main(Context context, View view, StringWriter sw) {
		this.context = context;
        this.view = view;
        this.sw = sw;

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
	} // Cierre Main

    public void obtenerBeta() {

        Double[] beta = new Double[size];
        int aux = 0;

        for (int i = 0; i < size; i++) {
            beta[i] = 1.0;
        }

        try {
            for (int i = 2004; i < 2015; i++) {
                Log.d("Iteracion", Integer.toString(i));
                jugadores.add(getJugadores(Integer.toString(i)));
                beta = logisticRegression(jugadoresList.get(i - 2004), Integer.toString(i));
                aux++;
            }

            Log.d("DEBUG", "Todo va como dios manda");
            Log.d("DEBUG", Double.toString(beta[0]));
            Log.d("DEBUG", Double.toString(beta[1]));
            Log.d("DEBUG", Double.toString(beta[2]));
            Log.d("DEBUG", Double.toString(beta[3]));
            Log.d("DEBUG", Double.toString(beta[4]));

            //	double por = (float) testeoSistema(beta) * 100.0;

            //	System.out.println("El porcentaje de acierto del sistema es del " + por + "%.");
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

	private Double[] logisticRegression(ArrayList<Jugador> data, String year) throws IOException {
		Jugador jug2;

		Double[] x, beta = new Double[size];
		
		// Generamos el vector de pesos
		for (int i = 0; i < size; i++) {
			beta[i] = 1.0;	
		}

		double hipotesis = 0.0;
		ArrayList<Partido> partidos;
		ArrayList<Double> listaHipo = new ArrayList<>();
		ArrayList<Double[]> listaX = new ArrayList<>();

		for (Jugador jug1 : data) {
			partidos = jug1.getPartidos();

			for (Partido part : partidos) {
				jug2 = this.leerJSONJugador(year, part.getContrincante());

				x = calcularCaracteristicas(jug1, jug2, part.getSuperficie(), year);

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

	private double estimarResultado(Jugador jug1, Jugador jug2, String superficie, String year, Double[] beta)
			throws IOException {

		Double[] x;
		double hipotesis = 0.0;

		x = this.calcularCaracteristicas(jug1, jug2, superficie, year);

		for (int j = 0; j < x.length; j++) {
			hipotesis += x[j] * beta[j];
		}

		return sigmoid(hipotesis);
	} // Cierre estimarResultado

	private static Double[] derivadaFuncionCoste(ArrayList<Double[]> listX, ArrayList<Double> listHipotesis,
			Double[] theta) {

		double alpha = 0.05;
        Double[] aux = new Double[size];
        int tam = listX.size();

        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < size; j++) {
                aux[j] += (listHipotesis.get(i) - 1) * listX.get(i)[j];
                aux[j] /= tam;
                theta[j] -= alpha * aux[j];
            }
        }

        /*
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
		theta[4] -= alpha * aux4; */

		return theta;
	}

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

	private Double[] calcularCaracteristicas(Jugador jug1, Jugador jug2, String superficie, String year) throws IOException {

		Double[] x = new Double[size];
		Superficie superf1, superf2;

		Double probSuperficie;

		switch(superficie) {

		case("Clay"): {
			superf1 = jug1.getClay();
			superf2 = jug2.getClay();
			break;
		}

		case("Grass"): {
			superf1 = jug1.getGrass();
			superf2 = jug2.getGrass();
			break;
		}

		case("Hard"): {
			superf1 = jug1.getHard();
			superf2 = jug2.getHard();
			break;
		}
		default: {
			superf1 = jug1.getOther();
			superf2 = jug2.getOther();
		}
		} // Cierre switch

        probSuperficie = superf1.getProbVictoria() - superf2.getProbVictoria();

		Double wsp1 = superf1.getProbGanarPuntoSacando();
		Double wsp2 = superf2.getProbGanarPuntoSacando();
		Double wrp1 = superf1.getProbGanarPuntoRestando();
		Double wrp2 = superf2.getProbGanarPuntoRestando();

        ((TextView) view.findViewById(R.id.textWspJug1)).setText(Double.toString(Math.round(wsp1 * 10000d) / 10000d));
        ((TextView) view.findViewById(R.id.textWspJug2)).setText(Double.toString(Math.round(wsp2 * 10000d) / 10000d));
        ((TextView) view.findViewById(R.id.textWrpJug1)).setText(Double.toString(Math.round(wrp1 * 10000d) / 10000d));
        ((TextView) view.findViewById(R.id.textWrpJug2)).setText(Double.toString(Math.round(wrp2 * 10000d) / 10000d));

		Double direct1, direct2;

		if (wsp1 == 0.0 || wsp2 == 0.0 || wrp1 == 0.0 || wrp2 == 0.0) {
			wsp1 = jug1.getGanarPuntoRestando();
			wsp2 = jug2.getGanarPuntoSacando();
			wrp1 = jug1.getGanarPuntoRestando();
			wrp2 = jug2.getGanarPuntoRestando();
		}

		direct1 = wsp1 - wrp2;
		direct2 = wsp2 - wrp1;

		Double serveadv = direct1 - direct2;

        ((TextView) view.findViewById(R.id.textServeAdvJug1)).setText(Double.toString(Math.round(direct1 * 10000d) / 10000d));
        ((TextView) view.findViewById(R.id.textServeAdvJug2)).setText(Double.toString(Math.round(direct2 * 10000d) / 10000d));

		Double complet1 = wsp1 * wrp1;
		Double complet2 = wsp2 * wrp2;

		Double complet = complet1 - complet2;

        Log.e("calcularCaracteristicas", "Antes de darle valores a x");

		//TODO Revisar resultados con distintas combinaciones
		x[0] = serveadv;
		//x[1] = complet;
		x[1] = 0.0;
		x[2] = this.calcularDirect(jug1, jug2, superficie);
		//x[3] = calcularAces(superf1, superf2);
		x[3] = 0.0;
		x[4] = probSuperficie;

		return x;
	} // Cierre calcularCaracteristicas
	
	private double calcularDirect(Jugador jug1, Jugador jug2, String superficie) throws IOException {
		String nombre2 = jug2.getNombre();
		Integer victorias1 = 0, victorias2 = 0, totalPartidos = 0;
		double por1, por2;

			if (jug1 != null) {
				for (Partido part : jug1.getPartidos()) {
					if (part.getContrincante().equals(nombre2)) {
						if (part.getGanador()) {
							if(part.getSuperficie().equals(superficie)) {
								victorias1 += 1;
							}
						} else {
							if(part.getSuperficie().equals(superficie)) {
								victorias2++;
							}
						}
					}
				} // Cierre for Partido
			}

		totalPartidos = victorias1 + victorias2;

		if (totalPartidos == 0) {
			totalPartidos++;
		}

		por1 = (float) victorias1 / totalPartidos;
		por2 = (float) victorias2 / totalPartidos;

        ((TextView) view.findViewById(R.id.textDirectJug1)).setText(Double.toString(Math.round(por1 * 10000d) / 10000d));
        ((TextView) view.findViewById(R.id.textDirectJug2)).setText(Double.toString(Math.round(por2 * 10000d) / 10000d));

		return por1 - por2;

	} // Cierre calcularDirect

	private static double calcularAces(Superficie sup1, Superficie sup2) {
		return sup1.getProbAce() - sup2.getProbAce();
	}
	
	public StringWriter estimarResultado(String jugador1, String jugador2, String superficie, String year, Double[] beta)
			throws IOException {

		Double[] x = new Double[size];
		double hipotesis = 0.0;
		Jugador jug1 = this.leerJSONJugador("2015", jugador1);
		Jugador jug2 = this.leerJSONJugador("2015", jugador2);

		x = calcularCaracteristicas(jug1, jug2, superficie, year);
		for (int j = 0; j < x.length; j++) {
			hipotesis += x[j] * beta[j];
		}

        Log.d("Resultado", "Nuestra estimación es que " + jugador1 + " tiene " + sigmoid(hipotesis) + " probabilidades de ganar.");

        sw.write(jugador1 + "\nvs\n" + jugador2 + "\n" + sigmoid(hipotesis) + "\n\n");

        Double prob1 = Math.round(sigmoid(hipotesis) * 10000d) / 10000d;
        Double prob2 = 1 - prob1;
        prob2 = Math.round(prob2 * 10000d) / 10000d;

        ((TextView) view.findViewById(R.id.textProbGanarJug1)).setText(Double.toString(prob1));
        ((TextView) view.findViewById(R.id.textProbGanarJug2)).setText(Double.toString(prob2));

        return this.sw;
	} // Cierre estimarResultado

	private Jugador leerJSONJugador(String year, String nombreJugador) throws IOException {
        InputStream is = this.context.getAssets().open("jugadores/" + year + "/" + toNombreFichero(nombreJugador) + ".json");

        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

		return this.mapper.readValue(new String(buffer), Jugador.class);
	} // Cierre leerJSONJugador
	
	private HashMap<String,Jugador> getJugadores(String year) throws IOException {

        HashMap<String,Jugador> jugadores = new HashMap<>();
        ArrayList<Jugador> jugadorList = new ArrayList<>();
        String[] f = context.getAssets().list("jugadores/" + year);

        for(String f1 : f){
            InputStream is = context.getAssets().open("jugadores/" + year + "/" + f1);

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            Jugador jugador = this.mapper.readValue(new String(buffer), Jugador.class);

            jugadores.put(toNombreFichero(jugador.getNombre()), jugador);
            jugadorList.add(jugador);
        }

        jugadoresList.add(jugadorList);

		return jugadores;
	} // Cierre getJugadores

	private ArrayList<Jugador> getJugadoresArray(String year) throws IOException {

		ArrayList<Jugador> jugadorList = new ArrayList<>();

        String[] f = this.context.getAssets().list("jugadores/" + year);

        for(String f1 : f){
            InputStream is = this.context.getAssets().open("jugadores/" + year + "/" + f1);

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            Jugador jugador = this.mapper.readValue(new String(buffer), Jugador.class);
            jugadorList.add(jugador);
        }

		return jugadorList;
	} // Cierre getJugadores
	
	private double testeoSistema(Double[] beta) throws IOException {
		Jugador jug2 = null;
		Integer aciertos = 0, fallos = 0;
		double rendimiento, hipotesis, desviacion = 0.0;
        String year = "2015";
		ArrayList<Partido> partidos;

		ArrayList<Jugador> data = this.getJugadoresArray("2015");

		for (Jugador jug1 : data) {
			partidos = jug1.getPartidos();

			for (Partido part : partidos) {

				// Obtenemos al contrincante
				jug2 = this.leerJSONJugador("2015", part.getContrincante());

				hipotesis = this.estimarResultado(jug1, jug2, part.getSuperficie(), year,  beta);

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
} // Cierre class
