import java.util.List;
import java.util.Map;

public class FileAnalyzer {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        //Reads the file
        FileReader fileReader = new FileReader("input.txt");
        List<String> words = fileReader.read();

        //Word Processor processes the given set of words , does normalize the words and forms the count map
        WordsProcessor wordsProcessor = new WordsProcessor(words);
        Map<String, Integer> result = wordsProcessor.getSortedMapByValues();

        // Tasks
        printExcludedWordCount(result);
        printTopFrequentWords(result, 5);
        printTopNKeys(result, 50);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Execution time: " + duration + " ms");
    }

    // Prints the total word count excluding filtered words.
    private static void printExcludedWordCount(Map<String, Integer> wordCountMap) {
        System.out.println("Excluded words count: " + wordCountMap.size());
    }


     //Prints the top N frequent words along with their count.
    private static void printTopFrequentWords(Map<String, Integer> wordCountMap, int limit) {
        System.out.println("Top " + limit + " Frequent Words:");
        wordCountMap.entrySet()
                .stream()
                .limit(limit)
                .forEach(entry -> System.out.println("Word: " + entry.getKey() + " -> Count: " + entry.getValue()));
    }

    // Prints the top N keys from the word count map.
    private static void printTopNKeys(Map<String, Integer> wordCountMap, int limit) {
        List<String> topKeys = wordCountMap.keySet()
                .stream()
                .limit(limit)
                .sorted()
                .toList();

        System.out.println("First " + limit + " elements:");
        topKeys.forEach(System.out::println);
    }
}
