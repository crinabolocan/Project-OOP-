import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class ComenziUser {
    List<Users> listen(int userid, int streamid, List<Users> listaUsers, List<Streams> listaStreams) {
        //iterez prin lista de users pentru a i schimba datele
        for (Users listaUser : listaUsers) {
            if (listaUser.getId() == userid) {
                for (Streams listaStream : listaStreams) {
                    if (listaStream.getId() == streamid) {
                        //adun la nr de vizualizari
                        listaStream.setNoOfStreams(listaStream.getNoOfStreams() + 1);
                        //adaug in lista de streams a userului
                        listaUser.getStreams().add(streamid);
                    }
                }
            }
        }
        return listaUsers;
    }

    void recommendedStreams(int userid, List<Streams> listaStreams, List<Users> listaUsers, List<Streamers> listaStreamers) {
        //am declarat o coada de prioritati cu metoda de comparare pentru a salva streamurile in ordine in functie de cerinta
        PriorityQueue<Streams> pq = new PriorityQueue<>(getComparator());
        for (Users listaUser : listaUsers) {//users
            if (listaUser.getId() == userid) {
                for (int j = 0; j < listaUser.getStreams().size(); j++) { //lista de streams a userului
                    for (int k = 0; k < listaStreams.size(); k++) { //lista de streams
                        if (listaStreams.get(k).getId() == listaUser.getStreams().get(j)) {
                            for (Streams listaStream : listaStreams) { //lista de streams
                                if (listaStream.getStreamerId() == listaStreams.get(k).getStreamerId()) {
                                    if (listaStream.getId() != listaStreams.get(k).getId()) {
                                        pq.add(listaStream);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int contor = 0;
        int size = pq.size();
        int sizelista;
        int nr = 0;
        for (int i = 0; i < size; i++) {
            String time = getTime(pq);
            String formattedDate = getDate(pq);
            sizelista = listaStreamers.size();
            for (int j = 0; j < sizelista; j++) {
                if (pq.isEmpty())
                    break;
                //verific daca id ul stremerului este in coada ca sa afisez
                if (listaStreamers.get(j).getId() == pq.peek().getStreamerId()) {
                    if (nr > 4)
                        break;
                    contor = afis(listaStreamers, pq, contor, time, formattedDate, j);
                    nr++;
                    pq.poll();
                }
            }
        }
    }

    private static int afis(List<Streamers> listaStreamers, PriorityQueue<Streams> pq, int contor, String time, String formattedDate, int j) {
        if (contor == 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", "" + pq.peek().getId());
            jsonObject.addProperty("name", pq.peek().getName());
            jsonObject.addProperty("streamerName", listaStreamers.get(j).getName());
            jsonObject.addProperty("noOfListenings", "" + pq.peek().getNoOfStreams());
            jsonObject.addProperty("length", time);
            jsonObject.addProperty("dateAdded", formattedDate);
            System.out.print(jsonObject);
            //System.out.print("{" + '"' + "id" + '"' + ":" + '"' + pq.peek().getId() + '"' + "," + '"' + "name" + '"' + ":" + '"' + pq.peek().getName() + '"' + "," + '"' + "streamerName" + '"' + ":" + '"' + listaStreamers.get(j).getName() + '"' + "," + '"' + "noOfListenings" + '"' + ":" + '"' + pq.peek().getNoOfStreams() + '"' + "," + '"' + "length" + '"' + ":" + '"' + time + '"' + "," + '"' + "dateAdded" + '"' + ":" + '"' + formattedDate + '"' + "}");
            contor++;
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", "" + pq.peek().getId());
            jsonObject.addProperty("name", pq.peek().getName());
            jsonObject.addProperty("streamerName", listaStreamers.get(j).getName());
            jsonObject.addProperty("noOfListenings", "" + pq.peek().getNoOfStreams());
            jsonObject.addProperty("length", time);
            jsonObject.addProperty("dateAdded", formattedDate);
            System.out.print("," + jsonObject);
        }
        return contor;
    }

    private static String getDate(PriorityQueue<Streams> pq) {
        Date data1 = new Date(pq.peek().getDateAdded() * 1000L);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf1.format(data1);
    }

    private static String getTime(PriorityQueue<Streams> pq) {
        SimpleDateFormat sdf;
        if (pq.peek().getLength() >= 3600) {
            sdf = new SimpleDateFormat("kk:mm:ss");
        } else {
            sdf = new SimpleDateFormat("mm:ss");
        }
        Date date = new Date(pq.peek().getLength() * 1000L);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    private static Comparator<Streams> getComparator() {
        //comparatorul pentru coada de prioritati in functie de noOfStreams
        // -> este un comparator lambda care compara noOfStreams si intoarce valoarea
        return (o1, o2) -> o2.getNoOfStreams().compareTo(o1.getNoOfStreams());
    }

    void surpriseStreams(int userid, List<Streams> listaStreams, List<Users> listaUsers, List<Streamers> listaStreamers) {
        //la recommend am cautat sa fie acelasi id, aici daca are acelasi id incerc sa nu il bag in coada
        PriorityQueue<Streams> pq = new PriorityQueue<>(getStreamsComparator());
        List<Integer> listaretineri = new ArrayList<>();
        //retin ce id uri are deja ca sa stiu sa le sterg dupa
        pq.addAll(listaStreams);
        for (Users listaUser : listaUsers) {
            if (listaUser.getId() == userid) {
                for (int j = 0; j < listaUser.getStreams().size(); j++) {
                    for (Streams listaStream : listaStreams) {
                        if (listaStream.getId() == listaUser.getStreams().get(j)) {
                            listaretineri.add(listaStream.getStreamerId());
                            break;
                        }
                    }
                }
            }
        }
        int sizepq = pq.size();
        PriorityQueue<Streams> aux = new PriorityQueue<>(getStreamsComparator());
        int check;
        //in coada am salvat toata lista si daca imi gaseste id ul in lista de retinere il sterge
        for (int i = 0; i < sizepq; i++) {
            check = 0;
            for (Integer integer : listaretineri) {
                if (pq.peek().getStreamerId() == integer) {
                    pq.poll();
                    check = 1;
                }
            }
            if(check == 0)
            {
                aux.add(pq.poll());
            }
        }
        pq = aux;
        //apoi vine afisarea
        int contor = 0;
        int size = pq.size();
        int sizelista;
        int nr = 0;
        for (int i = 0; i < size; i++) {
            sizelista = listaStreamers.size();
            for (int j = 0; j < sizelista; j++) {
                if (pq.isEmpty())
                    break;
                String time = getTime(pq);
                String formattedDate = getDate(pq);
                if (listaStreamers.get(j).getId() == pq.peek().getStreamerId()) {
                    if (nr > 2)
                        break;
                    contor = afis(listaStreamers, pq, contor, time, formattedDate, j);
                    nr++;
                    pq.poll();
                }
            }
        }
    }
    private static Comparator<Streams> getStreamsComparator() {
        //comparatorul pentru coada de prioritati in functie de dateAdded si noOfStreams
        return (o1, o2) -> {
            if (o1.getDateAdded() > o2.getDateAdded())
                return -1;
            else if (o1.getDateAdded() < o2.getDateAdded())
                return 1;
            else {
                return o2.getNoOfStreams().compareTo(o1.getNoOfStreams());
            }
        };
    }
}
