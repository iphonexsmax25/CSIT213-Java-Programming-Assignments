import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

public class CityAirStat {
    

	public CityAirStat() {

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
