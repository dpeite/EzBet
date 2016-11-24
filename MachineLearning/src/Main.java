import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

/**
 * 
 */

/**
 * @author manu
 *
 */
public class Main {

	static ArrayList<Double[]> listBuffer = new ArrayList<Double[]>();

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		for (int i = 0; i < 2; i++) {
			listBuffer.add(new Double[10]);
		}

		logisticRegression(getTrainingData());

	}

	private static ArrayList<Partido> getTrainingData() throws IOException {

		CSVReader reader = new CSVReader(
				new FileReader("/home/manu/atp/tennis-prediction/Data/Non-Oncourt/atp_matches_2015.csv"));
		String[] nextLine;
		ArrayList<Partido> data = new ArrayList<Partido>();

		reader.readNext();
		while ((nextLine = reader.readNext()) != null) {

			data.add(rellenarPartido(nextLine));
		} // Cierre while

		return data;

	} // Cierre getTrainingData

	private static void logisticRegression(ArrayList<Partido> data) {

		Integer[] x = new Integer[2];
		Double[] theta = new Double[] { 1.0, 1.0 };

		double hipotesis = 0.0;

		ArrayList<Double> listHipotesis = new ArrayList<Double>();
		ArrayList<Integer[]> listX = new ArrayList<Integer[]>();

		// Vamos a testear primero usando como caracteristica el ganar el primer
		// saque
		for(int j = 0; j < 2000;j++) {
		for (Partido part : data) {

			x[0] = part.getLoserPrimerSaqueGanado() - part.getWinnerPrimerSaqueGanado();
			x[1] = part.getLoserPrimerSaqueDentro() - part.getWinnerPrimerSaqueDentro();

			for (int i = 0; i < x.length; i++) {
				hipotesis += x[i] * theta[i];
			}

			listX.add(new Integer[]{part.getWinnerPrimerSaqueGanado() - part.getLoserPrimerSaqueGanado(), part.getWinnerPrimerSaqueDentro() - part.getLoserPrimerSaqueDentro()});
			listHipotesis.add(sigmoid(hipotesis));
			hipotesis = 0.0;
			
			x[0] = part.getWinnerPrimerSaqueGanado() - part.getLoserPrimerSaqueGanado();
			x[1] = part.getWinnerPrimerSaqueDentro() - part.getLoserPrimerSaqueDentro();

			for (int i = 0; i < x.length; i++) {
				hipotesis += x[i] * theta[i];
			}

			listX.add(new Integer[]{part.getLoserPrimerSaqueGanado() - part.getWinnerPrimerSaqueGanado(), part.getLoserPrimerSaqueDentro() - part.getWinnerPrimerSaqueDentro()});
			listHipotesis.add(sigmoid(hipotesis));
			hipotesis = 0.0;
		}

		theta = derivadaFuncionCoste(listX, listHipotesis, theta);
		
		//System.out.println("--------------------------------------");
		
		if (j != 1999) {
		listHipotesis = new ArrayList<Double>();
		listX = new ArrayList<Integer[]>();	
		}
		}
		
	//	System.out.println(theta[0]);
		for (Double dou: listHipotesis) {
			
			//System.out.println(dou);
		}
		
		
		System.out.println(theta[0]);
		System.out.println(theta[1]);

	} // Cierre logisticRegression

	private static double sigmoid(double hipotesis) {
		return 1.0 / (1 + Math.pow(Math.E, (-hipotesis)));
	}

	private static Double[] derivadaFuncionCoste(ArrayList<Integer[]> listX, ArrayList<Double> listHipotesis,
			Double[] theta) {

		double alpha = 0.05;
		double aux0 = 0.0, aux1 = 0.0;
		boolean orden = true;

		if (listHipotesis.get(0) == 1.0 || listHipotesis.get(0) == 0.0) {
			return theta;
		}

		for (int i = 0; i < listX.size(); i++) {
			
			if (orden) {
				aux0 += (listHipotesis.get(i) - 1) * listX.get(i)[0];
				aux1 += (listHipotesis.get(i) - 1) * listX.get(i)[1];
				
				orden = false;
			}else {
				aux0 += (listHipotesis.get(i)) * listX.get(i)[0];
				aux1 += (listHipotesis.get(i)) * listX.get(i)[1];
				
				orden = true;
			}

		}

		aux0 = aux0 / listX.size();
		aux1 = aux1 / listX.size();
		
		theta[0] -= alpha * aux0;
		theta[1] -= alpha * aux0;
		
		System.out.println(theta[0]);

		aux0 = 0.0;
		aux1 = 0.0;

		return theta;
	}

	private static Partido rellenarPartido(String[] nextLine) {

		Partido partido = new Partido();

		partido.setTorneo(nextLine[1]);
		partido.setFecha(nextLine[5]);
		partido.setSuperficie(nextLine[2]);
		partido.setResultado(nextLine[27]);
		partido.setContrincante(nextLine[20]);
		partido.setGanador(true);

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

		return partido;
	}

}
