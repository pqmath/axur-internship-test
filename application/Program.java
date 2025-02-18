package application;

import application.entities.DeepTraveler;
import java.io.IOException;
import java.net.URL;
public class Program {

    public static void main(String[] args) throws IOException {

        DeepTraveler traveler = new DeepTraveler();

        URL url = new URL("http://hiring.axreng.com/internship/example1.html ");
        traveler.setUrl(url);

         System.out.println(traveler.deepestText());

    }
}