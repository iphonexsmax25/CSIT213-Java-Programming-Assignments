import java.time.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class StudentAssertionTest {

	public static void main(String[] args) {
		// Make sure assertions are enabled
		boolean enabled = false;
		assert enabled = true; // if assertions enabled, this runs and sets enabled=true
		if (!enabled) {
			throw new RuntimeException("Assertions are NOT enabled! Run with: java -ea StudentAssertionTest");
		}

		try {
			testAirQualityReading();
			testInterfaceAnalyser();
			testDistrictAirQualityAnalyser();
			testCityAirStat();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void testCityAirStat() throws Exception {
		// Ensure assertions enabled
		boolean enabled = false;
		assert enabled = true;
		if (!enabled) {
			throw new RuntimeException("Assertions are NOT enabled! Run with: java -ea StudentAssertionTest");
		}

		// 1) Ensure data.csv exists in current folder
		Path dataCsv = Paths.get("data.csv");
		assert Files.exists(dataCsv) : "data.csv not found in current folder: " + dataCsv.toAbsolutePath();

		// 2) Run CityAirStat.load()
		CityAirStat app = new CityAirStat();
		app.load("data.csv");

		// Valid data lines = 12 (the first 12 lines are valid readings)
		// Invalid lines are commented + 4 invalid data lines => they should not be loaded.
		assert app.getSize() == 12 : "Expected 12 valid readings, but got " + app.getSize();

		// 3) Verify errors.txt content (order not important)
		Path errorsFile = Paths.get("errors.txt");
		assert Files.exists(errorsFile) : "errors.txt was not created in current folder.";

		Set<Integer> actualErrorLines = errorLineNumbers(errorsFile);

		// Line numbers include comment lines because CityAirStat increments line count for every line read
		// (including lines starting with '#').
		// Only the reported line numbers are checked here; the exact wording of each
		// error message is not compared.
		// Line 20 appears two times because that line must produce two separate error messages. 
		Set<Integer> expectedErrorLines = new HashSet<>(Arrays.asList(14, 16, 18, 20, 20));

		assert actualErrorLines.equals(expectedErrorLines)
				: "errors.txt line numbers mismatch.\nExpected lines: " + expectedErrorLines
				+ "\nActual lines:   " + actualErrorLines;

		// 4) Process with DistrictAirQualityAnalyser -> results.txt
		app.process(new DistrictAirQualityAnalyser());

		// 5) Verify results.txt content (order not important)
		Path resultsFile = Paths.get("results.txt");
		assert Files.exists(resultsFile) : "results.txt was not created in current folder.";

		Map<String, Double> actualResults = parseResultsFile(resultsFile);

		// Expected computed averages (rounded to 2 decimals by DistrictAirQualityAnalyser)
		Map<String, Double> expectedResults = new HashMap<>();
		expectedResults.put("2026-02-05_North", 37.97); // (35.2+40.6+38.1)/3 = 37.9666 -> 37.97
		expectedResults.put("2026-02-05_South", 51.90); // (55.0+48.3+52.4)/3 = 51.9000 -> 51.90
		expectedResults.put("2026-02-06_North", 30.90); // (30.4+28.8+33.5)/3 = 30.9000 -> 30.90
		expectedResults.put("2026-02-06_South", 45.57); // (45.1+47.6+44.0)/3 = 45.5666 -> 45.57

		assert actualResults.size() == expectedResults.size()
				: "Expected " + expectedResults.size() + " result entries, but got " + actualResults.size()
				+ "\nActual keys: " + actualResults.keySet();

		// Compare each expected key/value with tolerance
		double eps = 1e-9;
		for (Map.Entry<String, Double> exp : expectedResults.entrySet()) {
			String key = exp.getKey();
			assert actualResults.containsKey(key)
					: "Missing key in results.txt: " + key + "\nActual keys: " + actualResults.keySet();

			double actual = actualResults.get(key);
			double expected = exp.getValue();

			assert Math.abs(actual - expected) < eps
					: "Value mismatch for key '" + key + "'. Expected " + expected + " but got " + actual;
		}

		System.out.println("All CityAirStat assertion tests PASSED.");
		System.out.println("Checked files in: " + Paths.get(".").toAbsolutePath().normalize());
	}

	// ---------- Helpers ----------

	/**
	 * Reads errors.txt and extracts only the line number from each error message.
	 * Each non-empty line must be in the form: "Line <number>: <message>".
	 * The message text after the colon is not checked.
	 * Returns a Set of line numbers (order not important).
	 */
	private static Set<Integer> errorLineNumbers(Path file) throws IOException {
		List<String> lines = Files.readAllLines(file);
		Set<Integer> set = new HashSet<>();
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("^Line\\s+(\\d+)\\s*:");
		for (String line : lines) {
			String t = line.trim();
			if (t.isEmpty()) continue;
			java.util.regex.Matcher m = p.matcher(t);
			assert m.find() : "Error line does not start with 'Line <number>:' -> '" + t + "'";
			set.add(Integer.parseInt(m.group(1)));
		}
		return set;
	}

	/**
	 * Parse results.txt format:
	 *   <key> : <value>
	 * Example:
	 *   2026-02-05_North : 37.97
	 *
	 * Returns map of key->double.
	 */
	private static Map<String, Double> parseResultsFile(Path file) throws IOException {
		List<String> lines = Files.readAllLines(file);
		Map<String, Double> map = new HashMap<>();

		for (String line : lines) {
			String trimmed = line.trim();
			if (trimmed.isEmpty()) continue;

			String[] parts = trimmed.split("\\s*:\\s*");
			assert parts.length == 2 : "Invalid results line format: '" + trimmed + "'";

			String key = parts[0].trim();

			double value;
			try {
				value = Double.parseDouble(parts[1].trim());
			} catch (NumberFormatException e) {
				throw new AssertionError("Invalid numeric value in results line: '" + trimmed + "'");
			}

			map.put(key, value);
		}

		return map;
	}

	// ##########################################################################################
	private static void testDistrictAirQualityAnalyser() {
		testImplementsAnalyserInDistrictAirQualityAnalyser();
		testHasCorrectProcessMethodInDistrictAirQualityAnalyser();
		System.out.println("All DistrictAirQualityAnalyser assertion tests PASSED.");
	}

	/** Test that DistrictAirQualityAnalyser implements the Analyser interface */
	private static void testImplementsAnalyserInDistrictAirQualityAnalyser() {
		// Option 1: Check via "instanceof" (simple & clear)
		DistrictAirQualityAnalyser daa = new DistrictAirQualityAnalyser();
		assert (daa instanceof Analyser)
				: "DistrictAirQualityAnalyser should implement Analyser (instanceof check failed).";

		// Option 2: Check via reflection (robust)
		Class<?>[] interfaces = DistrictAirQualityAnalyser.class.getInterfaces();
		boolean found = false;
		for (Class<?> i : interfaces) {
			if (i.equals(Analyser.class)) {
				found = true;
				break;
			}
		}
		assert found : "DistrictAirQualityAnalyser should declare 'implements Analyser'.";
	}

	/**
	 * Test that DistrictAirQualityAnalyser has a process method with correct signature
	 */
	private static void testHasCorrectProcessMethodInDistrictAirQualityAnalyser() {
		try {
			// getMethod checks PUBLIC methods (including those from interface)
			Method m = DistrictAirQualityAnalyser.class.getMethod("process", ArrayList.class);

			// Return type should be HashMap
			assert m.getReturnType().equals(HashMap.class)
					: "process must return HashMap<String, Double> (raw type check failed). Found: "
							+ m.getReturnType().getName();

			// Must be public
			assert Modifier.isPublic(m.getModifiers()) : "process method must be public.";

			// Not static
			assert !Modifier.isStatic(m.getModifiers()) : "process method must NOT be static.";

		} catch (NoSuchMethodException e) {
			assert false : "Missing method: public HashMap<String, Double> process(ArrayList<AirQualityReading> data)";
		}
	}

	// ##########################################################################################
	private static void testInterfaceAnalyser() {
		testIsInterface();
		testHasCorrectProcessMethodInInterfaceAnalyser();
		System.out.println("All Analyser assertion tests PASSED.");

	}

	private static void testIsInterface() {
		assert Analyser.class.isInterface() : "Analyser should be an interface, but is not.";
	}

	private static void testHasCorrectProcessMethodInInterfaceAnalyser() {
		Method[] methods = Analyser.class.getDeclaredMethods();
		boolean found = false;

		for (Method m : methods) {
			if (m.getName().equals("process")) {
				// Check parameter types
				Class<?>[] params = m.getParameterTypes();
				assert params.length == 1 : "process should take exactly 1 argument";

				assert params[0].equals(ArrayList.class) : "process should take ArrayList<AirQualityReading> as parameter";

				// Check return type
				assert m.getReturnType().equals(HashMap.class) : "process should return HashMap<String, Double>";

				// Check public & abstract (interface methods are implicitly public abstract)
				assert Modifier.isPublic(m.getModifiers()) : "process should be public";

				found = true;
			}
		}

		assert found
				: "Analyser interface must declare method: HashMap<String, Double> process(ArrayList<AirQualityReading> data)";
	}

	// ##########################################################################################
	private static void testAirQualityReading() {
		testValidConstructionAndGetters();
		testNullDateThrows();
		testPm25BelowMinThrows();
		testPm25AboveMaxThrows();
		testBoundaryPm25Allowed();
		testEqualsSameSensorIdSameDateTrue();
		testEqualsSameSensorIdDifferentDateFalse();
		testEqualsDifferentSensorIdSameDateFalse();
		testEqualsNullAndDifferentTypeFalse();
		testToStringContainsFields();

		System.out.println("All AirQualityReading assertion tests PASSED.");
	}

	private static void testValidConstructionAndGetters() {
		try {
			LocalDate date = LocalDate.of(2026, 2, 5);
			AirQualityReading reading = new AirQualityReading("S001", "North", date, 35.2);

			assert "S001".equals(reading.getSensorId()) : "sensorId getter failed";
			assert "North".equals(reading.getDistrict()) : "district getter failed";
			assert date.equals(reading.getReadingDateTime()) : "readingDate getter failed";
			assert reading.getYear() == 2026 : "getYear failed";
			assert reading.getMonth() == 2 : "getMonth failed";
			assert Math.abs(reading.getPm25() - 35.2) < 1e-9 : "pm25 getter failed";

		} catch (AirQualityDataException e) {
			assert false : "Valid construction should not throw, but threw: " + e.getMessage();
		}
	}

	private static void testNullDateThrows() {
		try {
			new AirQualityReading("S001", "North", null, 35.0);
			assert false : "Expected AirQualityDataException for null date, but none was thrown";
		} catch (AirQualityDataException e) {
			assert e.getMessage() != null : "Exception message should not be null";
			assert e.getMessage().toLowerCase().contains("invalid reading date")
					: "Message should mention invalid reading date. Actual: " + e.getMessage();
		}
	}

	private static void testPm25BelowMinThrows() {
		try {
			new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 1), -0.01);
			assert false : "Expected AirQualityDataException for PM2.5 below 0.0";
		} catch (AirQualityDataException e) {
			assert e.getMessage().contains("Invalid PM2.5")
					: "Message should mention Invalid PM2.5. Actual: " + e.getMessage();
		}
	}

	private static void testPm25AboveMaxThrows() {
		try {
			new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 1), 500.01);
			assert false : "Expected AirQualityDataException for PM2.5 above 500.0";
		} catch (AirQualityDataException e) {
			assert e.getMessage().contains("Invalid PM2.5")
					: "Message should mention Invalid PM2.5. Actual: " + e.getMessage();
		}
	}

	private static void testBoundaryPm25Allowed() {
		try {
			AirQualityReading low = new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 1), 0.0);
			AirQualityReading high = new AirQualityReading("S002", "South", LocalDate.of(2026, 2, 1), 500.0);

			assert Math.abs(low.getPm25() - 0.0) < 1e-9 : "Boundary 0.0 should be allowed";
			assert Math.abs(high.getPm25() - 500.0) < 1e-9 : "Boundary 500.0 should be allowed";
		} catch (AirQualityDataException e) {
			assert false : "Boundary PM2.5 values should not throw: " + e.getMessage();
		}
	}

	private static void testEqualsSameSensorIdSameDateTrue() {
		try {
			LocalDate date = LocalDate.of(2026, 2, 5);
			AirQualityReading a = new AirQualityReading("S001", "North", date, 20.0);
			AirQualityReading b = new AirQualityReading("S001", "South", date, 30.0); // district/pm25 differs

			assert a.equals(b) : "equals should be true when sensorId and date match";
			assert b.equals(a) : "equals should be symmetric";
		} catch (AirQualityDataException e) {
			assert false : "Should not throw for valid readings: " + e.getMessage();
		}
	}

	private static void testEqualsSameSensorIdDifferentDateFalse() {
		try {
			AirQualityReading a = new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 5), 20.0);
			AirQualityReading b = new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 6), 20.0);

			assert !a.equals(b) : "equals should be false when dates differ";
		} catch (AirQualityDataException e) {
			assert false : "Should not throw for valid readings: " + e.getMessage();
		}
	}

	private static void testEqualsDifferentSensorIdSameDateFalse() {
		try {
			LocalDate date = LocalDate.of(2026, 2, 5);
			AirQualityReading a = new AirQualityReading("S001", "North", date, 20.0);
			AirQualityReading b = new AirQualityReading("S002", "North", date, 20.0);

			assert !a.equals(b) : "equals should be false when sensorIds differ";
		} catch (AirQualityDataException e) {
			assert false : "Should not throw for valid readings: " + e.getMessage();
		}
	}

	private static void testEqualsNullAndDifferentTypeFalse() {
		try {
			AirQualityReading reading = new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 5), 20.0);

			assert !reading.equals(null) : "equals should be false for null";
			assert !reading.equals("not an AirQualityReading") : "equals should be false for different type";
		} catch (AirQualityDataException e) {
			assert false : "Should not throw for valid readings: " + e.getMessage();
		}
	}

	private static void testToStringContainsFields() {
		try {
			AirQualityReading reading = new AirQualityReading("S001", "North", LocalDate.of(2026, 2, 5), 35.2);
			String s = reading.toString();

			assert s.contains("S001") : "toString should contain sensorId";
			assert s.contains("North") : "toString should contain district";
			assert s.contains("2026-02-05") : "toString should contain date";
			assert s.contains("35.2") : "toString should contain pm25";
		} catch (AirQualityDataException e) {
			assert false : "Should not throw for valid readings: " + e.getMessage();
		}
	}
}
