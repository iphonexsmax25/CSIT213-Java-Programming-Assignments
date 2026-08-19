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
            
            // Compute average per key, rounded to 2 decimal places
            HashMap<String, Double> result = new HashMap<>();
            
            for (String key :grouped.keySet()){
                ArrayList<Double> values = grouped.get(key);
                
                double sum = 0.0;
                for (double v : values){
                    sum += v;
                }
                double average =sum /values.size();
                
                double rounded = Math.round(average * 100) /100.0;
                result.put(key, rounded);
            }
            return result;
	}

}
