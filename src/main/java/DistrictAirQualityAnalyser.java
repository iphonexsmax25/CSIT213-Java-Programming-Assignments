import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
public class DistrictAirQualityAnalyser implements Analyser {

	@Override
	public HashMap<String, Double> process(ArrayList<AirQualityReading> data) {
            // Group all m25 values by date_district" key
            HashMap<String, ArrayList<Double>> grouped = new HashMap<>();
            
            for (AirQualityReading reading :data){
                //LocalDate.toString() already gives ISO format: yyyy-MM-dd 
                String key = reading.getReadingDateTime().toString() + "_" + reading.getDistrict();
                
                grouped.putIfAbsent(key, new ArrayList<>());
                grouped.get(key).add(reading.getPm25());
            }
            
            // 
                        
	}

}
