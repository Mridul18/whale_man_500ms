import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileReader {

    //Regex helps in filtering anything which is not between a-z , A-Z and apostrophe(') symbol
    private final String REGEX_FOR_WORD = "[^a-zA-Z']+";

    String fileLocation;

    public FileReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    //Reads the file and splits it based on white spaces , punctuations , special characters and numbers
    public List<String> read() {
        try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
            return stream
                    .flatMap(line -> Arrays.stream(line.split(REGEX_FOR_WORD))) // Split by anything that isn't a letter, number, or apostrophe
                    .filter(word -> !word.isEmpty()) // Remove empty words
                    .toList();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage()); // Log the error
        }
        return Collections.emptyList(); // Return an empty list if there's an error
    }
}
