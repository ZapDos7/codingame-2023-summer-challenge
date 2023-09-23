import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

class Player {

    /**
     * @param instructions The list of instructions as memorized by the mutant.
     * @param target The coordinates (x, y) of the target.
     * @return A string respecting the given format to fix the mutant's path.
     */
    public static String findCorrectPath(List<String> instructions, List<Integer> target) {
        // Write your code here

        System.err.println(instructions);
        List<Movement> moves = new ArrayList<>(); 
        instructions.forEach(instr -> moves.add(Movement.getMove(instr)));

        PositionAndOrientation init = new PositionAndOrientation(Pair.of(0,0), Orientation.RIGHT); // Start = (0,0) looking right >
        PositionAndOrientation finalGoal = new PositionAndOrientation(Pair.of(target.get(0), target.get(1)), Orientation.RIGHT);

        int index = 0;
        String instruction = Movement.BACK.toString();

        for (Movement move : moves) {
            index++;
            int xDifference = init.getCoords().first - finalGoal.getCoords().first;
            int yDifference = init.getCoords().second - finalGoal.getCoords().second;
            System.err.println("X difference is: " + xDifference + ", Y difference: " + yDifference);
            if ((xDifference > 0 && init.getOrientation().equals(Orientation.RIGHT) && move.toString().equals("FORWARD"))
            || (xDifference < 0 && init.getOrientation().equals(Orientation.LEFT) && move.toString().equals("FORWARD"))
            || (yDifference > 0 && init.getOrientation().equals(Orientation.UP) && move.toString().equals("FORWARD"))
            || (yDifference < 0 && init.getOrientation().equals(Orientation.DOWN) && move.toString().equals("FORWARD"))) {
                // if they try to move towards the wrong way
                System.err.println("Erroneous move found!");
                instruction = move.getOpposite().toString();
                break;
                // but the rest of the execution remains wrong then?????
                // oh no!
            }
            init = move(init, move);
            System.err.println("Made it to: " + init.getCoords() + " facing " + init.getOrientation());

        }

        System.err.println("Made it to: " + init.getCoords() + " when I should have made it to: " + finalGoal.getCoords());
        String result = "Replace instruction " + index + " with " + instruction;
        System.err.println(result);
        return result;
    }

    public enum Movement {
    FORWARD("FORWARD"), BACK("BACK"), TURN_LEFT("TURN LEFT"), TURN_RIGHT("TURN RIGHT");

    private final String text;

    Movement(final String text) {
        this.text = text;
    }
    
    public static Movement getMove(String text) {
        if (text.equals("FORWARD")) return FORWARD;
        else if (text.equals("BACK")) return BACK;
        else if (text.equals("TURN LEFT")) return TURN_LEFT;
        else if (text.equals("TURN RIGHT")) return TURN_RIGHT;
        return null;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isStep() {
        return (this.text == "FORWARD" || this.text == "BACK");
    }

    public Movement getOpposite() {
        if (text.equals("FORWARD")) return BACK;
        else if (text.equals("BACK")) return FORWARD;
        else if (text.equals("TURN LEFT")) return TURN_RIGHT;
        else if (text.equals("TURN RIGHT")) return TURN_LEFT;
        return null;
    }
}
    enum Orientation {UP, DOWN, LEFT, RIGHT}

    private static class PositionAndOrientation {
        private Pair<Integer, Integer> coords;
        private Orientation orientation;
        public Pair<Integer, Integer> getCoords() {
            return this.coords;
        }
        public Orientation getOrientation() {
            return this.orientation;
        }
        public PositionAndOrientation(Pair<Integer, Integer> coords, Orientation or) {
            this.coords = coords;
            this.orientation = or;
        }
    }

    private static PositionAndOrientation move(PositionAndOrientation begin, Movement movement) {
        if (movement.equals(Movement.FORWARD)) {
            if (begin.getOrientation().equals(Orientation.DOWN)) {
                // (x,y) -> (x, y-1)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first, begin.getCoords().second-1);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            } else if (begin.getOrientation().equals(Orientation.RIGHT)) {
                // (x,y) -> (x+1, y)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first+1, begin.getCoords().second);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            } else if (begin.getOrientation().equals(Orientation.UP)) {
                // (x,y) -> (x, y+1)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first, begin.getCoords().second+1);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            } else if (begin.getOrientation().equals(Orientation.LEFT)) {
                // (x,y) -> (x-1, y)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first-1, begin.getCoords().second);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            }
        } else if (movement.equals(Movement.BACK)) {
            if (begin.getOrientation().equals(Orientation.DOWN)) {
                // (x,y) -> (x, y+1)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first, begin.getCoords().second+1);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            } else if (begin.getOrientation().equals(Orientation.RIGHT)) {
                // (x,y) -> (x-1, y)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first-1, begin.getCoords().second);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            } else if (begin.getOrientation().equals(Orientation.UP)) {
                // (x,y) -> (x, y-1)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first, begin.getCoords().second-1);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            } else if (begin.getOrientation().equals(Orientation.LEFT)) {
                // (x,y) -> (x+1, y)
                Pair<Integer, Integer> newCoords = Pair.of(begin.getCoords().first+1, begin.getCoords().second);
                return new PositionAndOrientation(newCoords, begin.getOrientation());
            }
        } else if (movement.equals(Movement.TURN_LEFT)) {
            if (begin.getOrientation().equals(Orientation.DOWN)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.RIGHT);
            } else if (begin.getOrientation().equals(Orientation.RIGHT)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.UP);
            } else if (begin.getOrientation().equals(Orientation.UP)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.LEFT);
            } else if (begin.getOrientation().equals(Orientation.LEFT)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.DOWN);
            }
        } else if (movement.equals(Movement.TURN_RIGHT)) {
            if (begin.getOrientation().equals(Orientation.DOWN)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.LEFT);
            } else if (begin.getOrientation().equals(Orientation.RIGHT)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.DOWN);
            } else if (begin.getOrientation().equals(Orientation.UP)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.RIGHT);
            } else if (begin.getOrientation().equals(Orientation.LEFT)) {
                return new PositionAndOrientation(begin.getCoords(), Orientation.UP);
            }
        }
        return begin;
    } 

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param recipe A string respecting the given format to fix the mutant's path.
     */
    public static void trySolution(String recipe) {
        System.out.println("" + gson.toJson(recipe));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(findCorrectPath(
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<Integer>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


