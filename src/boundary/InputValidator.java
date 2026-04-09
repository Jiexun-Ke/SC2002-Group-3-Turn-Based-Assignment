package boundary;

//utility class to read int and str, handling error input and loops to valid choice
// return clean data to controller
    
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    private Scanner scanner;

    public InputValidator(){
        // initialize
        this.scanner = new Scanner(System.in);
    }

    public int getInt(String prompt) {
        int input = 0;
        boolean isValid = false;

        while (!isValid){
            System.out.println(prompt);
            try{
                input = scanner.nextInt();
                isValid = true;
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input! Please enter a number.");
            }
            finally {
                scanner.nextLine();
            }
            
            }
        return input;
    }

    public int getIntInRange(String prompt, int min, int max){
        int input = 0;
        boolean isValid = false;

        while (!isValid){
            input = getInt(prompt);

            if (input >= min && input <= max) {
                isValid = true;
            }
            else {
                System.out.println("Out of bounds! Please enter a number between " + min + " and " + max + ".");
            }
        }
        return input;
    }

    public String getString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public void closeScanner(){
        scanner.close();
    }

    }


