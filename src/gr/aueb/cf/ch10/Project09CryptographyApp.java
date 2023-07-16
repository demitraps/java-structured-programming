package gr.aueb.cf.ch10;

import java.util.ArrayList;

/**
 * The {@link Project09CryptographyApp} class provides methods for
 * encrypting and decrypting a phrase
 * based on a given key.
 * It uses a simple encryption algorithm based on modular
 * arithmetic.
 *
 * @author demitra
 */
public class Project09CryptographyApp {
    public static void main(String[] args) {
        final int KEY = 300; // αn int bigger than 128 = ASCII numbers
        String phrase = "Code is like humor. When you have to explain it, it’phrase bad.#";

        String encrypted = encrypt(phrase, KEY).toString();
        System.out.println(encrypted);

        String decrypted = decrypt(encrypt(phrase, KEY), KEY).toString();
        System.out.println(decrypted);
    }

    /**
     * Encrypts a given phrase using the provided key.
     * @param phrase        the phrase to be encrypted
     * @param key           the encryption key
     * @return              an ArrayList of integers,
     *                      representing the encrypted phrase.
     */
    public static ArrayList<Integer> encrypt(String phrase, int key) {
        ArrayList<Integer> encryptedPhrase = new ArrayList<>();
        char ch;
        int i;

        int previous = cipher(phrase.charAt(0), -1, key);
        encryptedPhrase.add(previous);

        i = 1;
        while ((ch = phrase.charAt(i)) != '#') {
            encryptedPhrase.add(cipher(ch, previous, key));
            previous = cipher(ch, previous, key);
            i++;
        }
        encryptedPhrase.add(-1);
        return encryptedPhrase;
    }

    /**
     * Decrypts an encrypted phrase using the provided key.
     *
     * @param encryptedPhrase   an ArrayList of integers representing the encrypted phrase
     * @param key               the encryption key
     * @return                  an ArrayList of characters,
     *                          representing the decrypted phrase.
     */
    public static ArrayList<Character> decrypt (ArrayList<Integer> encryptedPhrase, int key) {
        ArrayList<Character> decrypted = new ArrayList<>();
        int token;
        int i;
        int prevToken;

        prevToken = decipher(encryptedPhrase.get(0), -1, key);
        decrypted.add((char) prevToken);

        i = 1;
        while ((token = encryptedPhrase.get(i)) != -1) {
            decrypted.add(decipher(token, prevToken, key));
            prevToken = token;
            i++;
        }
        return decrypted;
    }

    /**
     * Encrypts a character based on the previous encrypted character and the key.
     *
     * @param ch        the character to be encrypted
     * @param prev      the previous encrypted character (or -1 for the first character)
     * @param key       the encryption key
     * @return          the encrypted value of the character
     */
    public static int cipher(char ch, int prev, int key) {
        if (prev == -1) return ch;
        return (ch + prev) % key; //ensure the value remains within the range of valid ASCII characters
    }

    /**
     * Decrypts a token based on the previous decrypted token and the key.
     *
     * @param cipher        the token to be decrypted
     * @param prev          the previous decrypted token (or -1 for the first token)
     * @param key           the encryption key
     * @return              the decrypted character
     */
    public static char decipher (int cipher, int prev, int key) {
        int decipher;
        if (prev == -1) return (char) cipher;

        decipher = (cipher - prev + key) % key;
        return (char) decipher;
    }
}
