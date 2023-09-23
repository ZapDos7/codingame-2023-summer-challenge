import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

class Player {

    /**
     * @param wishA The first wish.
     * @param wishB The second wish.
     * @return The hybrid wish you created.
     */
    public static String mixWishes(String wishA, String wishB) {
        // Write your code here
        System.err.println("Wish A: " + wishA + "\nWish B: " + wishB);
        return intertwineStrings(wishA, wishB);
    }

    public static String intertwineStrings(String str1, String str2) {
        String lcs = longestCommonSubsequence(str1, str2);
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0, k = 0;

        while (k < lcs.length()) {
            while (str1.charAt(i) != lcs.charAt(k)) {
                result.append(str1.charAt(i++));
            }

            while (str2.charAt(j) != lcs.charAt(k)) {
                result.append(str2.charAt(j++));
            }

            result.append(lcs.charAt(k++));
            i++;
            j++;
        }

        result.append(str1.substring(i)).append(str2.substring(j));
        var res = result.toString();

        System.err.println("Result: " + res);

        return res;
    }

    public static String longestCommonSubsequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param mixedAb The hybrid wish you created.
     */
    public static void trySolution(String mixedAb) {
        System.out.println("" + gson.toJson(mixedAb));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(mixWishes(
                gson.fromJson(in.nextLine(), new TypeToken<String>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<String>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


