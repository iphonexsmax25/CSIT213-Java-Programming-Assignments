import java.util.HashMap;
import java.util.ArrayList;

public interface Analyser {
	/** 
         * Process a list of valid  readings and return computed results
         * as a map  of key -> value (e.g. "2026-02-05_North -> 37.97)
         */
    HashMap<String, Double> process (ArrayList<AirQualityReading> data);
}