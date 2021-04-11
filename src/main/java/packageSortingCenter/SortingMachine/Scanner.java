package packageSortingCenter.SortingMachine;

import csv.Package;

import java.math.BigInteger;
import java.util.Random;

public class Scanner {
    public boolean scan(Package aPackage, SearchingAlgorithm searchingAlgorithm) {
        for (char[][] contents : aPackage.getContent()) {
            for (char[] content : contents) {
                switch (searchingAlgorithm) {
                    case BoyerMoore -> {
                        if (scanBoyerMoore(content)) {
                            return true;
                        }
                    }
                    case RabinKarp -> {
                        if (scanRabinKarp(new String(content))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean scanBoyerMoore(char[] text) {
        int R = 256;     // the radix
        int[] right;
        char[] pattern = "exp!os:ve".toCharArray();
        int m = pattern.length;
        int n = text.length;

        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pattern.length; j++)
            right[pattern[j]] = j;

        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (pattern[j] != text[i + j]) {
                    skip = Math.max(1, j - right[text[i + j]]);
                    break;
                }
            }
            if (skip == 0) return true;
        }
        return false;
    }

    private boolean scanRabinKarp(String txt) {
        String pat = "exp!os:ve";
        long patHash;    // pattern hash value
        int m = pat.length();           // pattern length
        long q = longRandomPrime();          // a large prime, small enough to avoid long overflow
        int R = 256;
        long RM;

        // precompute R^(m-1) % q for use in removing leading digit
        RM = 1;
        for (int i = 1; i <= m - 1; i++)
            RM = (R * RM) % q;
        patHash = hash(pat, m, R, q);

        int n = txt.length();
        if (n < m) return false;
        long txtHash = hash(txt, m, R, q);

        // check for match at offset 0
        if ((patHash == txtHash) && check(txt, 0, m, pat))
            return true;

        // check for hash match; if hash match, check for exact match
        for (int i = m; i < n; i++) {
            // Remove leading digit, add trailing digit, check for match.
            txtHash = (txtHash + q - RM * txt.charAt(i - m) % q) % q;
            txtHash = (txtHash * R + txt.charAt(i)) % q;

            // match
            int offset = i - m + 1;
            if ((patHash == txtHash) && check(txt, offset, m, pat))
                return true;
        }

        // no match
        return false;
    }

    private long hash(String key, int m, int R, long q) {
        long h = 0;
        for (int j = 0; j < m; j++)
            h = (R * h + key.charAt(j)) % q;
        return h;
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private boolean check(String txt, int i, int m, String pat) {
        for (int j = 0; j < m; j++)
            if (pat.charAt(j) != txt.charAt(i + j))
                return false;
        return true;
    }
}
