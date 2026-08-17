import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

public class CityAirStat {
    private ArrayList<AirQualityReading> readings;
    private ArrayList<String> errors;
    

	public CityAirStat() {
            readings = new ArrayList<>();
            errors = new ArrayList<>();

	}

	public void load(String fileName) {
	}

	public int getSize() {
	}

	@Override
	public String toString() {
	}

	public void process(Analyser analyser) {
	}

	public static void main(String[] args) {
		CityAirStat app = new CityAirStat();
		app.load("data.csv");

		Analyser analyser = new DistrictAirQualityAnalyser();
		app.process(analyser);
	}

}
