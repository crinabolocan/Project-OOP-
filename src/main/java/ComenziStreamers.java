import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
//import org.json.simple.JSONObject;

public class ComenziStreamers extends Streams {
    //comanda adaugastream - adauga un stream nou
    List<Streams> adaugastream(int idstreamers, List<Streamers> listaStreamers, List<Streams> listaStreams, int streamType, int id, int streamGenre, Long length, String name) {
        for (Streamers listaStreamer : listaStreamers) {
            if (listaStreamer.getId() == idstreamers) {
                //cu ajutorul design pattern-ului Builder, se construieste un stream nou
                listaStreams.add(new StreamsBuilder().setStreamType(streamType)
                        .setId(id)
                        .setStreamGenre(streamGenre)
                        .setNoOfStreams(0L)
                        .setStreamerId(idstreamers)
                        .setLength(length)
                        .setDateAdded(System.currentTimeMillis() / 1000L)
                        .setName(name).build());
            }
        }
        return listaStreams;
    }

    void listeazaStreams(int idstreamer, List<Streams> listaStreams, List<Streamers> listaStreamers, List<Users> listaUsers) {
        int contor= 0;
        //iterez pentru a verifica in lista de streameri daca exista streamerul cu id-ul dat, asta daca id ul e al unui streamer
        for (int i = 0; i < listaStreams.size(); i++) {
            if (listaStreams.get(i).getStreamerId() == idstreamer) {
                for (int j = 0; j < listaStreamers.size(); j++) {
                    if (listaStreamers.get(j).getId() == idstreamer) {
                        String time = getTime(listaStreams, i);
                        String formattedDate = getDate(listaStreams, i);
                        contor = afis(listaStreams, listaStreamers, contor, i, j, time, formattedDate);
                    }
                }
            }
        }
        contor = 0;
        //aici in cazul in care id-ul dat e al unui user
        for (Users listaUser : listaUsers) {
            if (listaUser.getId() == idstreamer) {
                for (int j = 0; j < listaUser.getStreams().size(); j++) {
                    for (int k = 0; k < listaStreams.size(); k++) {
                        if (listaUser.getStreams().get(j) == listaStreams.get(k).getId()) {
                            for (int l = 0; l < listaStreamers.size(); l++) {
                                if (listaStreamers.get(l).getId() == listaStreams.get(k).getStreamerId()) {
                                    String time = getTime(listaStreams, k);
                                    String formattedDate1 = getDate(listaStreams, k);
                                    contor = afis(listaStreams, listaStreamers, contor, k, l, time, formattedDate1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static int afis(List<Streams> listaStreams, List<Streamers> listaStreamers, int contor, int i, int j, String time, String formattedDate) {
        //fac afisarea in format json
        //contorul e pentru virgula
        if(contor == 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", "" + listaStreams.get(i).getId());
            jsonObject.addProperty("name", listaStreams.get(i).getName());
            jsonObject.addProperty("streamerName", listaStreamers.get(j).getName());
            jsonObject.addProperty("noOfListenings", "" + listaStreams.get(i).getNoOfStreams());
            jsonObject.addProperty("length", time);
            jsonObject.addProperty("dateAdded", formattedDate);
            System.out.print(jsonObject);
            //System.out.print("{" + '"' + "id" + '"' + ":" + '"' + listaStreams.get(i).getId() + '"' + "," + '"' + "name" + '"' + ":" + '"' + listaStreams.get(i).getName() + '"' + "," + '"' + "streamerName" + '"' + ":" + '"' + listaStreamers.get(j).getName() + '"' + "," + '"' + "noOfListenings" + '"' + ":" + '"' + listaStreams.get(i).getNoOfStreams() + '"' + "," + '"' + "length" + '"' + ":" + '"' + time + '"' + "," + '"' + "dateAdded" + '"' + ":" + '"' + formattedDate + '"' + "}");
            contor++;
        }
        else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", "" + listaStreams.get(i).getId());
            jsonObject.addProperty("name", listaStreams.get(i).getName());
            jsonObject.addProperty("streamerName", listaStreamers.get(j).getName());
            jsonObject.addProperty("noOfListenings", "" + listaStreams.get(i).getNoOfStreams());
            jsonObject.addProperty("length", time);
            jsonObject.addProperty("dateAdded", formattedDate);
            System.out.print("," + jsonObject);
            //System.out.print("," + "{" + '"' + "id" + '"' + ":" + '"' + listaStreams.get(i).getId() + '"' + "," + '"' + "name" + '"' + ":" + '"' + listaStreams.get(i).getName() + '"' + "," + '"' + "streamerName" + '"' + ":" + '"' + listaStreamers.get(j).getName() + '"' + "," + '"' + "noOfListenings" + '"' + ":" + '"' + listaStreams.get(i).getNoOfStreams() + '"' + "," + '"' + "length" + '"' + ":" + '"' + time + '"' + "," + '"' + "dateAdded" + '"' + ":" + '"' + formattedDate + '"' + "}");
        }
        return contor;
    }

    private static String getDate(List<Streams> listaStreams, int i) {
        //fac conversia datei din formatul dat in formatul dorit
        Date data1 = new Date(listaStreams.get(i).getDateAdded() * 1000L);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf1.format(data1);
    }

    private static String getTime(List<Streams> listaStreams, int i) {
        //fac conversia timpului din formatul dat in formatul dorit
        SimpleDateFormat sdf;
        if (listaStreams.get(i).getLength() >= 3600) {
            sdf = new SimpleDateFormat("kk:mm:ss");
        } else {
            sdf = new SimpleDateFormat("mm:ss");
        }
        Date date = new Date(listaStreams.get(i).getLength() * 1000L);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    List<Streams> stergeStream(int idstreamer, int idstream, List<Streams> listaStreams, List<Streamers> listaStreamers) {
        //iterez prin lista de streameri si verific daca exista streamerul cu id-ul dat pentru a sterge stream-ul
        for (int i = 0; i < listaStreams.size(); i++) {
            if (listaStreams.get(i).getId() == idstream) {
                for (Streamers listaStreamer : listaStreamers) {
                    if (listaStreamer.getId() == idstreamer) {
                        listaStreams.remove(i);
                    }
                }
            }
        }
        return listaStreams;
    }
}
