package main.java.Errors;

public class StackOverflowTest {
    public static void main(String[] args) {
        overflow("lol");
    }
    private static String overflow(String str){
        return overflow(str);
    }
}
