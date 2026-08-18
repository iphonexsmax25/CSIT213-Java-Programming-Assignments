import java.time.LocalDate;
import  java.util.*;

public class AirQualityReading {
    private String sensorId;
    private String district;
    private LocalDate readingDate;
    private double pm25;

    public AirQualityReading(String sensorId, String district, LocalDate readingDate,
                             double pm25) throws AirQualityDataException {
        if (readingDate == null){
            throw new AirQualityDataException("Invalid Reading Data");
        }
        
        if (pm25 < 0.0 ||pm25 >500.0){
            throw new AirQualityDataException("Invalid PM2.5: " + pm25);
        }
        this.sensorId = sensorId;
        this.district = district;
        this.readingDate= readingDate;
        this.pm25 = pm25;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getDistrict() {
        return district;
    }

    public int getYear() {
        return readingDate.getYear();
    }

    public int getMonth() {
        return readingDate.getMonthValue();
    }

    public double getPm25() {
        return pm25;
    }

    public LocalDate getReadingDateTime() {
        return readingDate;
    }

    /**
     * Equal if sensorId AND readingDate match.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AirQualityReading)) return false;

        AirQualityReading other = (AirQualityReading) obj;
        return sensorId.equals(other.sensorId)
                && readingDate.equals(other.readingDate);
    }
    
    @Override 
    public int hashCode(){
        return Objects.hash(sensorId, readingDate);
    }

    @Override
    public String toString() {
        return "AirQualityReading{" +
                "sensorId='" + sensorId + '\'' +
                ", district='" + district + '\'' +
                ", readingDate=" + readingDate +
                ", pm25=" + pm25 +
                "}";
    }
}
