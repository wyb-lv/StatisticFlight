import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightFile {
   private final String fileFlight = "Flight.csv";

    protected void checkExistFile(String fileName){
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist. Creating a new file.");
            try {
                if (file.createNewFile()) {
                    System.out.println("File created.");
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }

    protected void loadFlightfromFile(List<Flight>flightList) {
        checkExistFile(fileFlight);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileFlight))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    try {
                        String code = data[0].trim();
                        String airlineName = data[1].trim();
                        String departLocation = data[2].trim();
                        String arriveLocation = data[3].trim();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime departTime = LocalDateTime.parse(data[4].trim(), formatter);
                        LocalDateTime arriveTime = LocalDateTime.parse(data[5].trim(), formatter);

                        int seat = Integer.parseInt(data[6].trim());
                        Flight flight = new Flight(code, airlineName, departLocation, arriveLocation, departTime, arriveTime, seat);
                        flightList.add(flight);
                    } catch (Exception e) {
                        System.out.println("Error parsing flight data: " + e.getMessage() + " in line: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
