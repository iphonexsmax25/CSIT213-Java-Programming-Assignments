import java.time.*;
import java.util.*;
import java.io.*;
public class CityAirStat {
    
    private ArrayList<AirQualityReading> readings;
    private ArrayList<String> errors;

	public CityAirStat() {
            readings = new ArrayList<>();
            errors = new ArrayList<>();

	}

	public void load(String fileName) {
            readings = new ArrayList<>();
            errors = new ArrayList<>();
            
            int lineNumber = 0;
            
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                
                while ((line = reader.readLine()) != null){
                    lineNumber++;
                    String trimmedLine = line.trim();
                    
                    // Skip blank lines
                    if(trimmedLine.isEmpty()){
                        continue;
                    }
                    //Skip comment lines
                    if(trimmedLine.startsWith("#")){
                        continue;
                    }
                    
                    String[] parts = trimmedLine.split(",", -1);
                    
                    String dateStr = getField(parts, 0);
                    String sensorId = getField(parts, 1);
                    String district = getField(parts, 2);
                    String pm25Str =getField(parts, 3);
                    
                    
                    boolean lineIsValid = true;
                    
                    // Validate date: required format is yyyy-MM-dd, with a 
                    // fallback for d/M/yy (e.g. Excel-mangled dates like 5/2/26).
                    LocalDate reading = parseDate(dateStr);
                    if (reading == null){
                        errors.add ("Line" + lineNumber + ": Invalid Reading Date");
                        lineIsValid = false;
                    }
                    
                    // Validate sensor id: must not be empty
                    if (sensorId.isEmpty()){
                        errors.add("Line " + lineNumber + ": Invalid Sensor ID");
                        lineIsValid =false;
                    }
                    
                    // Validate  district: must mot be empty
                    if (district.isEmpty()){
                        errors.add("Line" + lineNumber + "Invalid District");
                        lineIsValid = false;
                    } 
                    
                    //Validate pm25:  must be numeric
                    double pm25 = 0.0;
                    try{
                        pm25 = Double.parseDouble(pm25Str);
                    } catch (NumberFormatException e){
                        errors.add("Line " + lineNumber + ": Invalid PM@.5");
                        lineIsValid =false;
                    }
                    
                    
                    // Only attempt to build the reading if the basics fields are ok
                    // TheAirQualityReading constructor performs the range check 
                    // (0.0 - 500.0) for pm25 and reports "InvalidPM2.5: <value>".
                     if (lineIsValid){
                         try{
                             AirQualityReading aqReading = 
                                     new AirQualityReading(sensorId, district, reading, pm25);
                             readings.add(aqReading);
                         } catch (AirQualityDataException e){
                             errors.add("Line " + lineNumber+ ": " + e.getMessage());
                         }
                         
                     }
                    
                    
                }
            } catch (IOException e){
                System.out.println("Unable to read File ' " + fileName + "': "+ e.getMessage());
            }
            writeErrorsFile();
	}
        
        private String getField(String[] parts, int index){
            if ( index < parts.length){
                return parts[index].trim();
            }
            else{
                return "";
            }
        }
        
        
        private LocalDate parseDate(String dateStr){
            try{
                return LocalDate.parse(dateStr);
            }catch(DateTimeException e) {
                
            }
            
            String[] dateParts =dateStr.split("/");
            if(dateParts.length == 3){
                try{
                    int day = Integer.parseInt(dateParts[0].trim());
                    int month = Integer.parseInt(dateParts[1].trim());
                    int year = Integer.parseInt(dateParts[2].trim());
                    if(year < 100){
                        year += 2000;
                    }
                    return LocalDate.of(year, month, day);
                } catch (NumberFormatException | DateTimeException e){
                    return null;
                }
            }
             return null;
        }
        private void writeErrorsFile(){
            try (PrintWriter writer = new PrintWriter(new FileWriter("errors.txt"))){
                for(String error : errors){
                    writer.println(error);
                }
                
            } catch (IOException e){
                System.out.println("Unable to write errors.txt" + e.getMessage());
            }
        }

	public int getSize() {
            return readings.size();
	}

	@Override
	public String toString() {
            return "CityAirStats{validReadings=" + readings.size() 
                    + ", errorCount=" + errors.size() + "}";
	}

	public void process(Analyser analyser) {
            HashMap<String, Double> results = analyser.process(readings);
            
            try(PrintWriter writer = new PrintWriter(new FileWriter("results.txt"))) {
                for (String key : results.keySet()){
                    writer.println(key + " : " + results.get(key));
                }
            } catch (IOException e){
                System.out.println("Unable to write results.txt " + e.getMessage());
            }
	}

	public static void main(String[] args) {
		CityAirStat app = new CityAirStat();
		app.load("data.csv");

		Analyser analyser = new DistrictAirQualityAnalyser();
		app.process(analyser);
	}

    

}
