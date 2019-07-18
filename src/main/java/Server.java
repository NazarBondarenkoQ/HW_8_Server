package main.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.java.exceptions.CustomNPE;
import main.java.exceptions.PalindromeException;
import main.java.exceptions.WrongNumberException;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
            httpServer.createContext("/homework", new MyHandler());
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    static class MyHandler implements HttpHandler {
        String palindrome = palindromeCheck();
        String number = numberCheck();
        String obj = nullChecker();


        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = (palindrome + "\n" + number + "\n" + obj);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            t.close();

        }
    }

    private static String palindromeCheck() {
        System.out.println("Please enter the word: ");
        String palindrome = scanner.next();
        StringBuilder stringBuilder = new StringBuilder(palindrome);
        String str;
        if (palindrome.toUpperCase().equals(stringBuilder.reverse().toString().toUpperCase())) {
            str = ("Word: \"" + palindrome + "\" is a palindrome.");
        } else {
            str = ("Word: \"" + palindrome + "\" is not a palindrome.");
            try {
                throw new PalindromeException(str);
            } catch (PalindromeException ex) {
                ex.printStackTrace();
            }
        }
        return str;
    }

    private static String numberCheck() {
        System.out.println("Please enter the number less than 100 and dividable by 2: ");
        int number;
        String str = null;
        try {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number % 2 != 0 || number > 100) {
                    str = ("Number: \"" + number + "\" is not a valid number");
                    throw new WrongNumberException(str);
                } else {
                    str = ("Number: \"" + number + "\" is a valid number");
                }
            } else {
                str = "You have entered wrong data. Please enter a number.";
                throw new InputMismatchException(str);
            }
        } catch (InputMismatchException | WrongNumberException e) {
            e.printStackTrace();
        }
        return str;
    }


    private static String nullChecker() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the object: ");
        Object object = null;
        try {
            object = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = null;
        try {
            if (object.equals("")) {
                str = "The object is null";
                throw new CustomNPE(str);
            } else {
                str = ("Object is not null. Object type: " + object.getClass());
            }
        } catch (CustomNPE customNPE) {
            customNPE.printStackTrace();
        } finally {
            address();
        }
        return str;
    }

    private static void address() {
        System.out.println("To check the results, please visit: http://localhost:8000/homework");
    }
}
