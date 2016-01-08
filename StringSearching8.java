import java.util.ArrayList;
import java.util.List;

/**
 * @author Riley Auten
 * @version 1.0
 */
public class StringSearching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot have no length");
        }
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        List charList = new ArrayList<>(0);
        int textLen = text.length();
        int patternLen = pattern.length();
        int[] failTable = buildFailureTable(pattern);
        int j = 0;
        int i = 0;
        int next = -1;
        while (j < textLen - (patternLen - 1 - i)) {
            if (text.charAt(j) == pattern.charAt(i)) {
                if (i == (patternLen - 1)) {
                    charList.add(charList.size(), (j - (patternLen - 1)));
                    //j++;
                    if (i != 0) {
                        i = failTable[i - 1];
                    } else {
                        i = 0;
                        j++;
                    }
                } else {
                    j++;
                    i++;
                }
            } else if (i > 0) {
                i = failTable[i - 1];
            } else {
                j++;
            }
        }
        return charList;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building failure table for
     * @return integer array of size text.length that you are building a failure
     * table for
     */
    public static int[] buildFailureTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        int patternLen = pattern.length();
        int[] failTable = new int[patternLen];
        int j = 1;
        int i = 0;
        int count = 0;
        while (j < patternLen) {
            if (pattern.charAt(j) == pattern.charAt(i)) {
                failTable[j] = ++count;
                j++;
                i++;
            } else if (i != 0) {
                i = 0;
                count = 0;
            } else {
                count = 0;
                failTable[j] = count;
                j++;
            }
        }
        return failTable;
    }

    /**
     * Boyer Moore algorithm that relies on last table. Works better with large
     * alphabets.
     *
     * Make sure to implement the table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
            CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "Pattern cannot be null or empty");
        }
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        int[] charPos = buildLastTable(pattern);
        List holdList = new ArrayList<>(0);
        int i = pattern.length() - 1;
        int j = pattern.length() - 1;

        while (i < text.length()) {
            char textChar = text.charAt(i);
            if (textChar == pattern.charAt(j)) {
                if (j == 0) {
                    holdList.add(i);
                    i += pattern.length();
                    j = pattern.length() - 1;
                } else {
                    i--;
                    j--;
                }
            } else {
                i += pattern.length() - Math.min(j, 1 + charPos[textChar]);
                j = pattern.length() - 1;
            }
        }
        return holdList;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table[x].
     * Each entry should be -1 if x is not in the pattern or the last index of x
     * where x is a particular character in your pattern.
     *
     * Ex. octocat
     *
     * table[o] = 3
     * table[c] = 4
     * table[t] = 6
     * table[a] = 5
     * table[everything else] = -1
     *
     * HINT: Characters auto cast to their corresponding int in Unicode (UTF-16)
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return integer array of size {@code (Character.MAX_VALUE + 1)}
     * containing the mapping for all characters in Unicode
     */
    public static int[] buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        int[] table = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < table.length; i++) {
            table[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            table[pattern.charAt(i)] = i;
        }
        return table;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 433;

    /**
     * Runs Rabin-Karp algorithm. Generate initial hash, and compare it with
     * hash from substring of text same length as pattern. If the two
     * hashes match compare their individual characters, else update hash
     * and continue.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
            CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "Pattern cannot be null or empty");
        }
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        List rkList = new ArrayList<>();
        int patternHash = generateHash(pattern, pattern.length());
        int textHash = generateHash(text, pattern.length());
        int frontPointer = 0;
        int backPointer = pattern.length() - 1;
        while (backPointer < (text.length())) {
            if (patternHash == textHash) {
                for (int i = (pattern.length() - 1); i >= 0;) {
                    if (i == 0 && pattern.charAt(i)
                            == text.charAt(backPointer - (
                                    pattern.length() - 1 - i))) {
                        rkList.add(rkList.size(), frontPointer);
                        i--;
                    } else if (pattern.charAt(i)
                            == text.charAt(backPointer - (
                                    pattern.length() - 1 - i))) {
                        i--;
                    } else {
                        i = -1;
                    }
                }
                backPointer++;
                if (backPointer >= text.length()) {
                    return rkList;
                } else {
                    textHash = updateHash(textHash, pattern.length(),
                            text.charAt(frontPointer),
                            text.charAt(backPointer));
                }
                frontPointer++;
            } else {
                backPointer++;
                if (backPointer >= text.length()) {
                    return rkList;
                } else {
                    textHash = updateHash(textHash, pattern.length(),
                            text.charAt(frontPointer),
                            text.charAt(backPointer));
                }
                frontPointer++;
            }
        }
        return rkList;
    }

    /**
     * Hash function used for Rabin-Karp. The formula for hashing a string is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 433 hash
     * = b * 433 ^ 3 + u * 433 ^ 2 + n * 433 ^ 1 + n * 433 ^ 0 = 98 * 433 ^ 3 +
     * 117 * 433 ^ 2 + 110 * 433 ^ 1 + 110 * 433 ^ 0 = 7977892179
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if current is null
     * @throws IllegalArgumentException if length is negative or greater
     *     than the length of current
     * @param current substring you are generating hash function for
     * @param length the length of the string you want to generate the hash for,
     * starting from index 0. For example, if length is 4 but current's length
     * is 6, then you include indices 0-3 in your hash (and pretend the actual
     * length is 4)
     * @return hash of the substring
     */
    public static int generateHash(CharSequence current, int length) {
        if (current == null) {
            throw new IllegalArgumentException("Current cannot be null");
        }
        if (length < 0 || length > current.length()) {
            throw new IllegalArgumentException(
                    "Length cannot be negative "
                            + "/greater than the length of your CharSequence");
        }
        int hashNum = 0;
        int power = 0;
        for (int i = (length - 1); i >= 0; i--) {
            Character pointer = current.charAt(i);
            hashNum = hashNum + ((pointer.hashCode()) * pow(BASE, power));
            power++;
        }
        return hashNum;
    }

    /**
     *
     * @param base Number to be multiplied by itself exp times
     * @param exp The number of times base is multiplied by itself
     * @return int base ** exp
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Invalid exponent.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }


    /**
     * Updates a hash in constant time to avoid constantly recalculating
     * entire hash. To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 433
     * hash("unny") = (hash("bunn") - b * 433 ^ 3) * 433 + y * 433 ^ 0 =
     * (7977892179 - 98 * 433 ^ 3) * 433 + 121 * 433 ^ 0 = 9519051770
     *
     * The computation of BASE raised to length - 1 may require O(n) time,
     * but the method should otherwise run in O(1).
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if length is negative
     * @param oldHash hash generated by generateHash
     * @param length length of pattern/substring of text
     * @param oldChar character we want to remove from hashed substring
     * @param newChar character we want to add to hashed substring
     * @return updated hash of this substring
     */
    public static int updateHash(int oldHash, int length, char oldChar,
            char newChar) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        oldHash = oldHash - (oldChar * pow(BASE, (length - 1)));
        oldHash = oldHash * BASE;
        oldHash = oldHash + newChar;
        int newHash = oldHash;
        return newHash;
    }
}
