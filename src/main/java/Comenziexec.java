import java.io.FileWriter;
import java.io.IOException;

public class Comenziexec extends Comenzi {
    //design pattern Template Method face posibila suprascrierea metodelor unei clase abstracte si executa metodele
    void add(String nume) {
        try {
            //afisez in fisierul Aplicatie.csv pentru o afisare interactiva datele
            FileWriter myWriter = new FileWriter("./src/main/java/Aplicatie.csv", true);
            myWriter.write("S-a adaugat un stream : ");
            myWriter.write(nume); // scriu numele streamului
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void delete(String id) {
        try {
            FileWriter myWriter = new FileWriter("./src/main/java/Aplicatie.csv", true);
            myWriter.write("S-a sters un stream cu id-ul : ");
            myWriter.write(id); // scriu id-ul streamului
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void list(String idstreamer) {
        try {
            FileWriter myWriter = new FileWriter("./src/main/java/Aplicatie.csv", true);
            myWriter.write("S-a listat un stream de la streamer-ul cu id-ul : ");
            myWriter.write(idstreamer); // scriu id-ul streamerului
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void recomandare(String tip) {
        try {
            FileWriter myWriter = new FileWriter("./src/main/java/Aplicatie.csv", true);
            myWriter.write("S-a recomandat un stream de tipul : ");
            myWriter.write(tip); // scriu tipul streamului
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void exit() {
        try {
            FileWriter myWriter = new FileWriter("./src/main/java/Aplicatie.csv", true);
            myWriter.write("S-a inchis aplicatia.");
            myWriter.write("\n");
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
