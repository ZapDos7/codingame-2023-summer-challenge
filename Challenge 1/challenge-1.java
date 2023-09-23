import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

class Solution {

    /**
     * @param mutantScores The score corresponding to each mutant
     * @param threshold The score threshold above which mutants should be ignored
     * @return 
     */
    public static String bestRemainingMutant(Map<String, Double> mutantScores, int threshold) {
        // Write your code here
        Map<String, Double> filtered = new HashMap<>();
        mutantScores.entrySet()
        .stream().filter(entry -> entry.getValue()<threshold)
        .collect(Collectors.toList())
        .forEach(entry -> filtered.put(entry.getKey(), entry.getValue()));
        
        return Collections.max(filtered.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param output 
     */
    public static void trySolution(String output) {
        System.out.println("" + gson.toJson(output));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(bestRemainingMutant(
                gson.fromJson(in.nextLine(), new TypeToken<Map<String, Double>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


