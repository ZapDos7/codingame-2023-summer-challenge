import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;

class Solution {

    private static final String Y_MEN = "Y-Men";
    private static final String Z_MEN = "Z-Men";

    /**
     * @param nMutants The number of graduating mutants
     * @param wishlistY The priority of mutants selected by Y-Men
     * @param wishlistZ The priority of mutants selected by Z-Men
     * @param wishlistMutants The priorities of squads for each mutant
     * @param friendships The list of friendships between mutants
     * @return The best possible score across all configurations
     */
    public static int maxScore(int nMutants, Map<Integer, Double> wishlistY, Map<Integer, Double> wishlistZ, List<Unknown> wishlistMutants, List<Unknown2> friendships) {
        // Write your code here
        System.err.println("Number of graduating mutants: " + nMutants);
        System.err.println("Y-men wishlist: " + wishlistY);
        System.err.println("Z-men wishlist: " + wishlistZ);
        System.err.println("Priorities of squads per mutant student: " + wishlistMutants);
        System.err.println("Friendships between mutants: " + friendships);

        Map<Integer, Pair<Double, String>> maxScorePerStudent = new HashMap<>();
        for (int i = 0; i < nMutants; i++) {
            maxScorePerStudent.put(i, Pair.of(0.0, ""));
        }

        int tempScore = 0; //score is added each time a priority or friendship is used?
        for (Map.Entry<Integer, Double> entry : wishlistY.entrySet()) {
            maxScorePerStudent.put(entry.getKey(), Pair.of(entry.getValue(),Y_MEN));
            tempScore += entry.getValue();
        }

        for (Map.Entry<Integer, Double> entry : wishlistZ.entrySet()) {
            if (maxScorePerStudent.get(entry.getKey()).first == 0.0) {
                maxScorePerStudent.put(entry.getKey(), Pair.of(entry.getValue(), Z_MEN));
                tempScore += entry.getValue();
            } else if (maxScorePerStudent.get(entry.getKey()).first < entry.getValue()) {
                maxScorePerStudent.remove(entry.getKey(), entry.getValue());
                maxScorePerStudent.put(entry.getKey(), Pair.of(entry.getValue(), Z_MEN));
                tempScore -= maxScorePerStudent.get(entry.getKey()).first;
                tempScore += entry.getValue();
            } //else, keep this value as Y score > Z score for this student
        }

        for (Unknown u : wishlistMutants) {
            int mutant = Integer.parseInt(u.mutant);
            if (u.squad.equals(Y_MEN)) {
                if (!maxScorePerStudent.get(mutant).second.equals(Y_MEN)) {
                    //mutant wants Y squad but is placed in Z squad
                    if (u.priority > maxScorePerStudent.get(mutant).first) {
                        //mutant swaps to Z-squad
                        tempScore -= maxScorePerStudent.get(mutant).first;
                        maxScorePerStudent.remove(mutant);
                        maxScorePerStudent.put(mutant, Pair.of(Double.valueOf(u.priority), Z_MEN));
                        tempScore += u.priority;
                    }
                } else {
                    // mutant is in desired squad
                    tempScore += u.priority;
                }
            } else { // Z-Men
                // same
                if (!maxScorePerStudent.get(mutant).second.equals(Z_MEN)) {
                    //mutant wants Z squad but is placed in Y squad
                    if (u.priority > maxScorePerStudent.get(mutant).first) {
                        //mutant swaps to Y-squad
                        tempScore -= maxScorePerStudent.get(mutant).first;
                        maxScorePerStudent.remove(mutant);
                        maxScorePerStudent.put(mutant, Pair.of(Double.valueOf(u.priority), Y_MEN));
                        tempScore += u.priority;
                    }
                } else {
                    // mutant is in desired squad
                    tempScore += u.priority;
                }
            }
        }

        List<Integer> yGroup = new ArrayList<>();
        List<Integer> zGroup = new ArrayList<>();
        
        for (Map.Entry<Integer, Pair<Double, String>> entry : maxScorePerStudent.entrySet()) {
            if (entry.getValue().second.equals(Y_MEN)) {
                yGroup.add(entry.getKey());
            } else {
                zGroup.add(entry.getKey());
            }
        }

        for (Unknown2 u : friendships) {
            int m1 = Integer.parseInt(u.friend1);
            int m2 = Integer.parseInt(u.friend2);
            if ((yGroup.contains(m1) && yGroup.contains(m2)) || (zGroup.contains(m1) && zGroup.contains(m2)))
            tempScore += u.strength;
        }
        
        return tempScore;
    }
    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param maxPossibleScore The best possible score across all configurations
     */
    public static void trySolution(int maxPossibleScore) {
        System.out.println("" + gson.toJson(maxPossibleScore));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(maxScore(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Map<Integer, Double>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Map<Integer, Double>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<Unknown>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<Unknown2>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


// Pair class
class Pair<U, V>
{
    public final U first;       // the first field of a pair
    public final V second;      // the second field of a pair
 
    // Constructs a new pair with specified values
    private Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }
 
    @Override
    // Checks specified object is "equal to" the current object or not
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
 
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
 
        Pair<?, ?> pair = (Pair<?, ?>) o;
 
        // call `equals()` method of the underlying objects
        if (!first.equals(pair.first)) {
            return false;
        }
        return second.equals(pair.second);
    }
 
    @Override
    // Computes hash code for an object to support hash tables
    public int hashCode()
    {
        // use hash codes of the underlying objects
        return 31 * first.hashCode() + second.hashCode();
    }
 
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
 
    // Factory method for creating a typed Pair immutable instance
    public static <U, V> Pair <U, V> of(U a, V b)
    {
        // calls private constructor
        return new Pair<>(a, b);
    }
}

class Unknown {
    @SerializedName("mutant")
    public String mutant;
    @SerializedName("squad")
    public String squad;
    @SerializedName("priority")
    public int priority;

    @Override
    public String toString() {
        return "mutant: " + mutant
        + ", squad: " + squad
        + ", priority: " + priority;
    }
}

class Unknown2 {
    @SerializedName("friend1")
    public String friend1;
    @SerializedName("friend2")
    public String friend2;
    @SerializedName("strength")
    public int strength;

    @Override
    public String toString() {
        return "friend1: " + friend1
        + ", friend2: " + friend2
        + ", strength: " + strength;
    }
}
