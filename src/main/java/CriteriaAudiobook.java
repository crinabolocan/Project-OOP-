import java.util.*;
public class CriteriaAudiobook implements Criteria {
    //design pattern: Filter
    //criteriul de cautare: audiobook
    public List<Streams> meetCriteria(List<Streams> streams) {
        List<Streams> audiobook = new ArrayList<Streams>();
        //iterez prin lista de streamuri si salvez in lista audiobook doar streamurile de tip audiobook, 3
        for (Streams s : streams) {
            if (s.getStreamType() == 3) {
                audiobook.add(s);
            }
        }
        return audiobook;
    }
    //instantele pentru Singleton, care creaza doar o singura instanta a clasei
    //Șablonul Singleton asigură faptul că o clasă are o singură instanță și furnizează un punct
    //global de acces la ea
    private static CriteriaAudiobook instance;
    private CriteriaAudiobook() {
    }
    public static CriteriaAudiobook getInstance() {
        if (instance == null) {
            instance = new CriteriaAudiobook();
        }
        return instance;
    }
}
