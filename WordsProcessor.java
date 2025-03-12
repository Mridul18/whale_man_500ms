import java.util.*;
import java.util.stream.Collectors;

public class WordsProcessor {
    private static final List<String> PREPOSITIONS = List.of(
            "about", "above", "across", "after", "against", "along", "among", "around", "as", "at",
            "before", "behind", "below", "beneath", "beside", "between", "beyond", "but", "by", "concerning",
            "despite", "down", "during", "except", "for", "from", "in", "inside", "into", "like", "near", "of",
            "off", "on", "onto", "out", "outside", "over", "past", "regarding", "since", "through", "throughout",
            "till", "to", "toward", "under", "underneath", "until", "up", "upon", "with", "within", "without"
    );

    private static final List<String> CONJUNCTIONS = List.of(
            "and", "but", "or", "nor", "for", "yet", "so", "although", "because", "since", "unless",
            "until", "while", "whereas", "whether", "however", "moreover", "nevertheless", "therefore"
    );

    private static final List<String> PRONOUNS = List.of(
            "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them", "my", "your",
            "his", "her", "its", "our", "their", "mine", "yours", "hers", "ours", "theirs", "myself", "yourself",
            "himself", "herself", "itself", "ourselves", "yourselves", "themselves", "who", "whom", "whose", "which", "that"
    );

    private static final List<String> ARTICLES = List.of("a", "an", "the");

    private static final List<String> MODAL_VERBS = List.of(
            "can", "could", "may", "might", "shall", "should", "will", "would", "must", "ought"
    );

    private static final List<String> STOP_WORDS = List.of(
            "is", "was", "are", "were", "be", "been", "being", "am", "do", "does", "did", "doing", "has", "have",
            "had", "having", "shall", "will", "should", "would", "can", "could", "may", "might", "must", "ought", "dare",
            "need", "not", "no", "nor", "only", "own", "same", "so", "than", "too", "very", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "this", "that", "these", "those", "what", "which", "who", "whom",
            "whose", "when", "where", "why", "how", "again", "then", "once", "here", "there"
    );

    private final List<String> words;

    public WordsProcessor(List<String> words) {
        this.words = words;
    }


    public Map<String, Integer> getSortedMapByValues() {
        Map<String, Integer> wordCounter = new HashMap<>();

        words.stream()
                .filter(word -> !isExcluded(word.toLowerCase())) // Exclude certain words
                .map(word -> normalizeWord(word.toLowerCase())) // Normalize words (e.g., singularize and handle apostrophes)
                .forEach(word -> wordCounter.put(word.toLowerCase(), wordCounter.getOrDefault(word.toLowerCase(), 0) + 1)); //Put's the word into the map if it's already there then increase the count by 1

        return wordCounter.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private String normalizeWord(String word) {
        word = removeApostrophes(word); // Handle apostrophes (ex father's-> father)
        word = removePluralization(word); // Handles singular plural difference (ex Dogs -> Dog)
        return word;
    }

    //Handles removing apostrophe if it's present in the word
    private String removeApostrophes(String word) {
        if (word.endsWith("'s")) {
            word = word.substring(0, word.length() - 2);
        }
        return word;
    }

    // Handle common pluralization rules (e.g., "dogs" -> "dog")
    private String removePluralization(String word) {
        if (word.endsWith("s")) {
            return word.substring(0, word.length() - 1);
        }
        return word;
    }

    //Handles checking of common words like Preposition , Conjunctions , Articles , Pronouns , Auxiliary and Modal verbs
    private boolean isExcluded(String word) {
        return PREPOSITIONS.contains(word) ||
                CONJUNCTIONS.contains(word) ||
                PRONOUNS.contains(word) ||
                ARTICLES.contains(word) ||
                MODAL_VERBS.contains(word) ||
                STOP_WORDS.contains(word);
    }
}
