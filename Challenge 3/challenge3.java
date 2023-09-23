import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

class Solution {

    /**
     * @param fileContents A list of strings, where each string represents the contents of a file.
     * @return The contents of the merged file.
     */
    public static String mergeFiles(List<String> fileContents) {
        // Write your code here

        // System.err.println("Start: " + fileContents);

        Map<String, String> info = new HashMap<>();
        fileContents.forEach(fileContent -> {
            // different line per \n
            String[] lines = fileContent.split("\n");

            List<String> linesList = List.of(lines);

            linesList.forEach(line -> {
                // split line by ";"
                String[] lineParts = line.split(";");
                List<String> linePartsList = List.of(lineParts);

                String name = extractName(linePartsList);
                // sort data alphabetically before storing in map
                if (info.containsKey(name)) {
                    List<String> previousData = extractInfo(List.of(info.get(name).split(";")));
                    List<String> allData = extractInfo(linePartsList);
                    previousData.forEach(pd -> {
                        if (!allData.contains(pd)) {
                            allData.add(pd);
                        }
                    });
                    String data = sortData(allData);
                    info.put(name, data);
                } else {
                    String data = sortData(extractInfo(linePartsList));
                    info.put(name, data);
                }
            });
        });
        AtomicReference<String> result = new AtomicReference<String>("");
        TreeMap<String, String> sorted = new TreeMap<>();
        sorted.putAll(info);
    
System.err.println(sorted);

        sorted.forEach((k,v) -> {
            boolean valuesNotExist = v.length()==1;
            String values = "";
            if (valuesNotExist == false) {
                values = ";" + v ;
                values = values.substring(0, values.length()-1);
            }
            result.set(result.get() + "Name" + k + values + "\n");
        });
        String res = "";
        if (result.get().endsWith(";\n")) {
            res = result.get().substring(0, result.get().length()-2);
        } else if (result.get().endsWith("\n")) {
            res = result.get().substring(0, result.get().length()-1);
        } else if (result.get().endsWith(";")) {
            res = result.get().substring(0, result.get().length()-1);
        } else {
            res = result.get();
        }
        return res;
    }

    private static String extractName(List<String> parts) {
        for (String linePart: parts) {
            if (linePart.contains("Name")) {
                return linePart.substring(4, linePart.length());
            } else {
                continue;
            }
        }
        return "error";
    }

    private static List<String> extractInfo(List<String> parts) {
        List<String> sorted = new ArrayList<>();
        for (String linePart: parts) {
            if (linePart.contains("Name")) {
                continue;
            } else {
                sorted.add(linePart);
            }
        }
        return sorted;
    }

    private static String sortData(List<String> sortedParts) {
        String result = "";
        Collections.sort(sortedParts);
        for (String part : sortedParts) {
            result += part + ";";
        }
        return result;
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param mergedFile The contents of the merged file.
     */
    public static void trySolution(String mergedFile) {
        System.out.println("" + gson.toJson(mergedFile));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(mergeFiles(
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


