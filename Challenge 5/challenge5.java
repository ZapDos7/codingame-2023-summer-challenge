import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.concurrent.atomic.AtomicBoolean;

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


class Solution {

    /**
     * @param nGears An integer representing the number of gears in the system (numbered from 0 to N-1).
     * @param connections An array representing all pairs of gears connected together.
     * @return An array of two integers representing the number of gears rotating clockwise and counterclockwise respectively, or [-1, -1] if the configuration is invalid.
     */
    public static List<Integer> gearBalance(int nGears /* how many gears */,
        List<List<Integer>> connections /* pairs of gears connected */) {

        System.err.println("Connections: " + connections);

        // Write your code here
        List<Integer> result = new ArrayList<>(); // how many clockwise, how many counterclockwise
        // -1, -1 if impossible
        Map<Integer, List<Integer>> neighbours = new HashMap<>(); 
        // key: my gear ID
        // value: list of my neighbours gear IDs

        Map<Integer, Boolean> rotations = new HashMap<>(); 
        // key: my gear ID
        // value: true if ClockWise & false if CC

        
        connections.forEach(connection -> {
            int key = connection.get(0);
            int neighbour = connection.get(1);
            var existingNeighbours = neighbours.get(key);
            if (existingNeighbours == null) {
                existingNeighbours = new ArrayList<>();
            }
            existingNeighbours.add(neighbour);
            neighbours.put(key, existingNeighbours);
        });
        System.err.println("Neightbours: " + neighbours);
        AtomicBoolean error = new AtomicBoolean(false);
        // store neightbours & rotation
        neighbours.forEach((k,v) -> {

            Boolean isClockwise = Boolean.FALSE; // ERROR LIES HERE
            // if n neighbours between k and 0
            // if n%2==0, false
            // else, true
            if (findNeighbouringDistance(k, neighbours)%2==0) {
                isClockwise = Boolean.TRUE;
            }
            System.err.println("Adding " + k + "'s rotation: " + isClockwise);
            rotations.put(k, isClockwise);
            for (Integer v1: v) {
                System.err.println("Adding neighbour's (" + v1 + ") rotation which must be "+ !isClockwise);
                if (rotations.get(v1) == null) {
                    rotations.put(v1, !isClockwise);
                } else {
                    if (rotations.get(v1) != !isClockwise) {
                        error.set(true);
                    } else {
                        continue;
                    }
                }
            }
             System.err.println("New world state: " + rotations);
        });
        if (error.get() == true) {
            return List.of(-1,-1);
        } else {
            int c = 0;
            int cc = 0;
            for (Boolean rotation : rotations.values()) {
                if (rotation.booleanValue() == true) {
                    c +=1;
                } else {
                    cc += 1;
                }
            }
            result.add(c);
            result.add(cc);
        }
        return result;
    }

    /* Todo: ameliorate method for stack overflow */
    public static int findNeighbouringDistance(int gear, Map<Integer, List<Integer>> neighbours) {
        // find how many neighbours between gear & 0
        if (gear != 0 && neighbours.get(gear) != null) {
            return findNeighbouringDistance(neighbours.get(gear).get(0), neighbours) +1;
        } else {
            return 0;
        }
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param output An array of two integers representing the number of gears rotating clockwise and counterclockwise respectively, or [-1, -1] if the configuration is invalid.
     */
    public static void trySolution(List<Integer> output) {
        System.out.println("" + gson.toJson(output));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(gearBalance(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<List<Integer>>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


