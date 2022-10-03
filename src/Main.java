import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";
        final String INFO_INPUT = """ 
                \nAnge ett matematiskt uttryck. 
                Uttrycket måste innehålla två operander (positiva eller negativa tal) och en operator (+, -, * eller /). 
                Ifall du vill avbryta programmet skriv teckenkombinationen Ctrl-D.""";
        Scanner scan = new Scanner(System.in);
        System.out.println(INFO_INPUT);
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            int tryAndFind;
            boolean tryAgain = true;
            while (tryAgain) {
                if (input.equals("")) {
                    System.out.println(ANSI_RED + "Error! Uttrycket får inte vara tomt!" + ANSI_RESET);
                    System.out.println(INFO_INPUT);
                    input = scan.nextLine();
                } else
                    tryAgain = false;
            }
            input = input.trim(); // ta bort alla eventuella tomma tecken på sidorna
            input = input.replace(',', '.'); // ersätta ',' med '.'
            String delEtt = "";
            String delTwo = "";
            String operator = "";
            tryAndFind = lookForWhatYouNeed(input, "+-*/", 1); //räkna från position 1 ifall det är "-" i början
            if (tryAndFind > 0) {
                delEtt = input.substring(0, tryAndFind);
                delTwo = input.substring(tryAndFind + 1);
                operator = input.substring(tryAndFind, tryAndFind + 1);
            } else if (tryAndFind == -1) {
                System.out.println(ANSI_RED + "Error! En giltig operator saknas! " +
                        "Uttrycket måste innehålla en av följande operatorer: +, -, * eller /." + ANSI_RESET);
                System.exit(-1);
            }
            if (delEtt.equals("") || delTwo.equals("")) {
                System.out.println(ANSI_RED + "Error! Uttrycket får inte innehålla bara en enda operand (ett enda tal)."
                        + ANSI_RESET);
                System.exit(-1);
            }
            tryAndFind = lookForWhatYouNeed(delTwo, "+*/", 0);
            if (tryAndFind > 0) {
                System.out.println(ANSI_RED + "Error! Uttrycket får inte innehålla flera operatorer." + ANSI_RESET);
                System.exit(-1);
            }
            double operandEtt = Double.parseDouble(delEtt);
            double operandTwo = Double.parseDouble(delTwo);
            double resultat = 0;
            if (operator.equals("+"))
                resultat = operandEtt + operandTwo;
            else if (operator.equals("-"))
                resultat = operandEtt - operandTwo;
            else if (operator.equals("*"))
                resultat = operandEtt * operandTwo;
            else if (operator.equals("/") && operandTwo == 0) {
                System.out.println(ANSI_RED + "Oops, man får inte dividera med 0!" + ANSI_RESET);
                System.exit(-1); // -1 generally indicates unsuccessful termination
            } else
                resultat = operandEtt / operandTwo;
            System.out.println(ANSI_BLUE + "Resultatet på din beräkning blir:\t" + resultat + ANSI_RESET);
        }
        System.out.println("Du har avbrutit programmet");
    }

    static int lookForWhatYouNeed(String origin, String whatToFind, int start) {
        for (int i = start; i < origin.length(); i++)
            if (whatToFind.indexOf(origin.charAt(i)) >= 0)
                return i;
        return -1;
    }
}