package auxiliar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Rui Malemba
 */
public class Ficheiro {

    public static String getFileName() {
        return "GESTOR_de_CAIXA.txt";
    }

    public static boolean isEmpty() {
        boolean result = true;

        final String fileP = "C:\\Users\\Rui Malemba\\Documents\\NetBeansProjects\\ProjectoFinal\\GESTOR_de_CAIXA.txt";
        File file = new File(fileP);
        try {
            if (file.exists() && !file.isDirectory()) {
                BufferedReader br = new BufferedReader(new FileReader(getFileName()));
                result = br.readLine() == null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
