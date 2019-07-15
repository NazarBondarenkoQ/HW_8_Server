package main.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.java.exceptions.PalindromeException;
import main.java.exceptions.WrongNumberException;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Server {
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
        byte address = address();


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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the word: ");
        String palindrome = scanner.next();
        StringBuilder stringBuilder = new StringBuilder(palindrome);
        if (palindrome.toUpperCase().equals(stringBuilder.reverse().toString().toUpperCase())) {
            return ("Word: \"" + palindrome + "\" is a palindrome.");
        } else {
            String str = ("Word: \"" + palindrome + "\" is not a palindrome.");
            try {
                throw new PalindromeException(str);
            } catch (PalindromeException ex) {
                ex.printStackTrace();
            }
            return str;
        }
    }

    private static String numberCheck() {
        Scanner scanner = new Scanner(System.in);
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
                    return ("Number: \"" + number + "\" is a valid number");
                }
            } else {
                String s = "You have entered wrong data. Please enter a number.";
                throw new InputMismatchException(s);
            }
        } catch (InputMismatchException | WrongNumberException e) {
            e.printStackTrace();
        }
        return str;
    }


    private static String nullChecker() {
        Object object;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the object: ");
        object = scanner.next();
        if (Optional.ofNullable(object).isEmpty()) {
            throw new NullPointerException("The object is null");
        } else {
            return ("Object is not null. Object type: " + object.getClass());
        }
    }

    private static byte address(){
        System.out.println("To check the results, please visit: http://localhost:8000/homework");
        return 0;
    }
}
