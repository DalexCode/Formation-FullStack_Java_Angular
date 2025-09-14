import java.util.Scanner;

public class HelloWorldPro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Entrez votre nom: ");
        String nom = scanner.nextLine();
        
        if (nom.trim().isEmpty()) {
            System.out.println("Erreur: Le nom ne peut pas être vide!");
        } else {
            System.out.println("Hello " + nom + "!");
        }
        
        scanner.close();
    }
}
