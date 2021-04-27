import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class DrawSimulation {
	private static String key;
	private static Integer value;
	private static LinkedHashMap<String, Integer> firstRound, secondRound, thirdRound, fourthRound, fifthRound,
			sixthRound, seventhRound, winners;
	private static LinkedHashMap<String, Integer> stats;
	private static Random random;
	private static int randomNumber;

	private static float perc;
	private static String[] players;
	private static int[] rankings;
	private static int i;
	private static float offset;
	private static int j = 0;
	private static float average;
	private static double stdDev;
	private static int averageRanking;
	
	//Number to set Monte Carlo simulations
	private static int simulations = 1000000;

	public static void main(String[] args) throws IOException {
		firstRound = new LinkedHashMap<String, Integer>();
		secondRound = new LinkedHashMap<String, Integer>();
		thirdRound = new LinkedHashMap<String, Integer>();
		fourthRound = new LinkedHashMap<String, Integer>();
		fifthRound = new LinkedHashMap<String, Integer>();
		sixthRound = new LinkedHashMap<String, Integer>();
		seventhRound = new LinkedHashMap<String, Integer>();
		winners = new LinkedHashMap<String, Integer>();
		stats = new LinkedHashMap<String, Integer>();

		File name = new File("PlayersAO20019.txt");
		long startTime = System.currentTimeMillis();

		// File reader
		if (name.isFile()) {
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			String[] arrayStrings;
			text = input.readLine();
			while (text != null) {
				arrayStrings = text.split(",");
				key = arrayStrings[0];
				value = Integer.parseInt(arrayStrings[1]);
				firstRound.put(key, value);
				averageRanking += value;
				stats.put(key, 0);
				text = input.readLine();
			}

			input.close();
		}

		averageRanking = averageRanking / 128;
		System.out.println("Average ranking: " + averageRanking);
		players = new String[2];
		rankings = new int[2];

		random = new Random();

		for (j = 0; j < simulations; j++) {
			// System.out.println("\nSimulation #:" + j);

			// System.out.println("First round");
			simulateRound(firstRound, 1, secondRound);

			// System.out.println("Second round");
			simulateRound(secondRound, 2, thirdRound);

			// System.out.println("Third round");
			simulateRound(thirdRound, 3, fourthRound);

			// System.out.println("Fourth round");
			simulateRound(fourthRound, 4, fifthRound);

			// System.out.println("Fifth round");
			simulateRound(fifthRound, 5, sixthRound);

			// System.out.println("Sixth round");
			simulateRound(sixthRound, 6, seventhRound);

			// System.out.println("Seventh round");
			
			simulateRound(seventhRound, 7, winners);
			calculateStats();

			secondRound.clear();
			thirdRound.clear();
			fourthRound.clear();
			fifthRound.clear();
			sixthRound.clear();
			seventhRound.clear();

		}

		System.out.println("\nStats");
		for (Map.Entry<String, Integer> entry : stats.entrySet()) {

			perc = ((float) entry.getValue() / simulations) * 100;
			offset += Math.abs((perc - 0.78125));
			average += perc;
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println(entry.getKey() +": " + df.format(perc) + "%");
		}

		// Standard deviation
		average = average / 128;
		for (Map.Entry<String, Integer> entry : stats.entrySet()) {
			perc = ((float) entry.getValue() / simulations) * 100;
			stdDev += Math.pow(perc - average, 2) / 128;
		}
		stdDev = Math.sqrt(stdDev);

		offset = offset / 128;
		System.out.println("\nAverage offset: " + offset);
		System.out.println("Standard deviation: " + stdDev);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("\nExecution time: " + elapsedTime);
		// writer.close();
	}

	private static void simulateRound(LinkedHashMap<String, Integer> round, int numberRound,
			LinkedHashMap<String, Integer> nextRound) {

		for (Map.Entry<String, Integer> entry : round.entrySet()) {
			players[i] = entry.getKey();
			rankings[i] = entry.getValue();
			i++;
			if (i % 2 == 0) {
				playMatch(players, rankings, nextRound);
				i = 0;
			}
		}

	}

	private static void playMatch(String[] players, int[] rankings, LinkedHashMap<String, Integer> round) {
		// System.out.println("Play match!");

		randomNumber = generateRandom(0, rankings[0] + rankings[1]);
		// System.out.println("Random number = " + randomNumber);

		if (randomNumber < rankings[0]) {
			round.put(players[0], rankings[0]);
			// System.out.println("1st player\n");
		} else {
			round.put(players[1], rankings[1]);
			// System.out.println("2nd player \n");
		}

	}

	private static void calculateStats() {
		if (randomNumber < rankings[0]) {
			stats.put(players[0], stats.get(players[0]) + 1);
		} else {
			stats.put(players[1], stats.get(players[1]) + 1);
		}

	}

	private static int generateRandom(int min, int max) {
		return random.nextInt(((max - min) + 1)) + min;

	}
}
