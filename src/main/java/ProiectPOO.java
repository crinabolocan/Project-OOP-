import java.io.File;
import java.util.Scanner;
import java.util.*;

public class ProiectPOO {

    public static void main(String[] args) {
        if (args == null) { //verificare daca argumentele sunt null
            System.out.println("Nothing to read here");
            return;
        }
        //am declarat listele pentru streamers, streams si users
        List<Streamers> listaStreamers = new ArrayList<>();
        List<Streams> listaStreams = new ArrayList<>();
        List<Users> listaUsers = new ArrayList<>();
        //am declarat listele pentru tipurile de streamuri: music, podcast, audiobook
        List<Streams> listaStreamsMuzic;
        List<Streams> listaStreamsPodcast;
        List<Streams> listaStreamsAudio;
        Streamers streamers;
        Streams streams;
        Users users;
        try {
            //deschid fisierele pentru citire si cele cu comenzi
            File streamersfile = new File("./src/main/resources/" + args[0]);
            File streamsfile = new File("./src/main/resources/" + args[1]);
            File usersfile = new File("./src/main/resources/" + args[2]);
            File commandsfile = new File("./src/main/resources/" + args[3]);
            Scanner streamersReader = new Scanner(streamersfile);
            Scanner streamsReader = new Scanner(streamsfile);
            Scanner usersReader = new Scanner(usersfile);
            Scanner commandsReader = new Scanner(commandsfile);
            //am sarit prima linie din fiecare fisier pentru ca erau detalii despre coloane
            streamersReader.nextLine();
            streamsReader.nextLine();
            usersReader.nextLine();
            while (streamersReader.hasNextLine()) {
                //luand fiecare linie din fisierul streamers am creat un obiect de tip Streamers si am adaugat in lista de streamers
                //am folosit design pattern-ul builder pentru a crea obiectul si pentru a le salva mai usor in lista
                //am folosit si design pattern-ul iterator pentru a parcurge datele din fisier
                //Separa e clasa de iterator
                Separa buf = new Separa(streamersReader.nextLine());
                streamers = new StreamersBuilder().setStreamerType(Integer.parseInt(buf.next()))
                        .setId(Integer.parseInt(buf.next()))
                        .setName(buf.next())
                        .build();
                //adaugam in lista de streamers
                listaStreamers.add(streamers);
            }
            while (streamsReader.hasNextLine()) {
                //la fel si pentru streams
                Separa buf2 = new Separa(streamsReader.nextLine());
                streams = new StreamsBuilder().setStreamType(Integer.parseInt(buf2.next()))
                        .setId(Integer.parseInt(buf2.next()))
                        .setStreamGenre(Integer.parseInt(buf2.next()))
                        .setNoOfStreams(Long.parseLong(buf2.next()))
                        .setStreamerId(Integer.parseInt(buf2.next()))
                        .setLength(Long.parseLong(buf2.next()))
                        .setDateAdded(Long.parseLong(buf2.next()))
                        .setName(buf2.next())
                        .build();
                listaStreams.add(streams);
            }
            //aici am declarat listele pentru fiecare tip de stream in functie de Criteria
            //am declarat clasele, apeland getInstances() folosind design pattern-ul singleton
            CriteriaMuzic muzic = CriteriaMuzic.getInstance();
            CriteriaPodcast podcast = CriteriaPodcast.getInstance();
            CriteriaAudiobook audio = CriteriaAudiobook.getInstance();
            //am filtrat astfel listaStreams cu ajutorul design pattern-ului filter
            listaStreamsAudio = audio.meetCriteria(listaStreams);
            listaStreamsPodcast = podcast.meetCriteria(listaStreams);
            listaStreamsMuzic = muzic.meetCriteria(listaStreams);
            while (usersReader.hasNextLine()) {
                //la fel si pentru users
                Separa buf3 = new Separa(usersReader.nextLine());
                users = new UsersBuilder().setId(Integer.parseInt(buf3.next()))
                        .setName(buf3.next())
                        .setStreams(buf3.next())
                        .build();
                listaUsers.add(users);
            }
            Comenzi interactor;
            interactor = new Comenziexec();
            String comenzifisier = null;
            //am inceput citirea comenzilor din fisier
            //pentru fiecare comanda am apelat metoda respectiva
            while (commandsReader.hasNextLine()) {
                String[] comanda = commandsReader.nextLine().split(" ");
                switch (comanda[1]) {
                    case "ADD": {
                        comenzifisier = comenzifisier + ",ADD";
                        String nume = null;
                        ComenziStreamers comenzi = new ComenziStreamers();
                        if (comanda.length > 7) {
                            int i = 6;
                            nume = comanda[i];
                            while (i < comanda.length - 1) {
                                i++;
                                nume = nume + " " + comanda[i];
                            }
                        }
                        comenzifisier = comenzifisier + ":" + nume;
                        //am intors o noua lista modificata dupa ce am adaugat un nou streamer
                        listaStreams = comenzi.adaugastream(Integer.parseInt(comanda[0]), listaStreamers, listaStreams, Integer.parseInt(comanda[2]), Integer.parseInt(comanda[3]),
                                Integer.parseInt(comanda[4]), Long.parseLong(comanda[5]), nume);
                        break;
                    }
                    case "LIST": {
                        comenzifisier = comenzifisier + ",LIST";
                        ComenziStreamers comenzi = new ComenziStreamers();
                        System.out.print("[");
                        comenzi.listeazaStreams(Integer.parseInt(comanda[0]), listaStreams, listaStreamers, listaUsers);
                        System.out.println("]");
                        comenzifisier = comenzifisier + ":" + comanda[0];
                        break;
                    }
                    case "DELETE": {
                        comenzifisier = comenzifisier + ",DELETE";
                        ComenziStreamers comenzi = new ComenziStreamers();
                        listaStreams = comenzi.stergeStream(Integer.parseInt(comanda[0]), Integer.parseInt(comanda[2]), listaStreams, listaStreamers);
                        comenzifisier = comenzifisier + ":" + comanda[2];
                        break;
                    }
                    case "LISTEN": {
                        ComenziUser comenzi = new ComenziUser();
                        listaUsers = comenzi.listen(Integer.parseInt(comanda[0]), Integer.parseInt(comanda[2]), listaUsers, listaStreams);
                        break;
                    }
                    case "RECOMMEND": {
                        //atat aici cat si la surprise am folosit design pattern-ul filter pentru ca eu salvasem
                        // in listaStreamsAudio, listaStreamsPodcast si listaStreamsMuzic in functie de criteriu
                        comenzifisier = comenzifisier + ",RECOMMEND";
                        if (comanda[2].equals("SONG")) {
                            ComenziUser comenzi = new ComenziUser();
                            System.out.print("[");
                            comenzi.recommendedStreams(Integer.parseInt(comanda[0]), listaStreamsMuzic, listaUsers, listaStreamers);
                            System.out.println("]");
                        } else if (comanda[2].equals("PODCAST")) {
                            ComenziUser comenzi = new ComenziUser();
                            System.out.print("[");
                            comenzi.recommendedStreams(Integer.parseInt(comanda[0]), listaStreamsPodcast, listaUsers, listaStreamers);
                            System.out.println("]");
                        } else if (comanda[2].equals("AUDIOBOOK")) {
                            ComenziUser comenzi = new ComenziUser();
                            System.out.print("[");
                            comenzi.recommendedStreams(Integer.parseInt(comanda[0]), listaStreamsAudio, listaUsers, listaStreamers);
                            System.out.println("]");
                        }
                        comenzifisier = comenzifisier + ":" + comanda[2];
                        break;
                    }
                    case "SURPRISE":
                        if (comanda[2].equals("SONG")) {
                            ComenziUser comenzi = new ComenziUser();
                            System.out.print("[");
                            comenzi.surpriseStreams(Integer.parseInt(comanda[0]), listaStreamsMuzic, listaUsers, listaStreamers);
                            System.out.println("]");
                        } else if (comanda[2].equals("PODCAST")) {
                            ComenziUser comenzi = new ComenziUser();
                            System.out.print("[");
                            comenzi.surpriseStreams(Integer.parseInt(comanda[0]), listaStreamsPodcast, listaUsers, listaStreamers);
                            System.out.println("]");
                        } else if (comanda[2].equals("AUDIOBOOK")) {
                            ComenziUser comenzi = new ComenziUser();
                            System.out.print("[");
                            comenzi.surpriseStreams(Integer.parseInt(comanda[0]), listaStreamsAudio, listaUsers, listaStreamers);
                            System.out.println("]");
                        }
                        break;
                }
            }
            //la fiecare comanda am salvat intr-un string cateva date pentru a putea scrie intr-un fisier ceva interactiv
            //am folosit un design-ul template
            interactor.execute(comenzifisier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Separa implements Iterator //iterator
{
    String[] buffer;
    int index = 0;

    public Separa(String buf) {
        this.buffer = buf.split(",");
    }

    @Override
    public boolean hasNext() {
        return buffer.length > 8 && index == 7;
    }
    //hasNext() returneaza true dupa anumite conditii
    @Override
    public String next() {
        if (this.hasNext())
            return buffer[index++].substring(1, buffer[index - 1].length()) + "," + buffer[index++].substring(0, buffer[index - 1].length() - 1);
        else
            return buffer[index++].substring(1, buffer[index - 1].length() - 1);
    }
    //metoda next() returneaza un string dupa ce se verifica conditia din hasNext() si se face un substring din buffer ca sa scap de ghilimele
    //era un nume si cu virgula la un mom dat
}

