import java.util.HashMap;
import java.util.ArrayList;


public interface Analyser {
    HashMap <String, Double>  process(ArrayList<AirQualityReading> data);
	
}
