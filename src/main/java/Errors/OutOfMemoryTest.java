package main.java.Errors;

public class OutOfMemoryTest {
    public static void main(String[] args) {
        byte[][] test = new byte[8][(int)(1024 * Math.pow(1024,10))];
    }
}
