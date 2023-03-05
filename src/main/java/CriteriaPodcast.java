import java.util.*;
public class CriteriaPodcast implements Criteria {
    //design pattern: Filter
    //criteriul de cautare: podcast
    public List<Streams> meetCriteria(List<Streams> streams) {
        List<Streams> podcast = new ArrayList<Streams>();
        //iterez prin lista de streamuri si salvez in lista podcast doar streamurile de tip podcast, 2
        for (Streams s : streams) {
            if (s.getStreamType() == 2) {
                podcast.add(s);
            }
        }
        return podcast;
    }
    private static CriteriaPodcast instance;
    private CriteriaPodcast() {
    }
    public static CriteriaPodcast getInstance() {
        if (instance == null) {
            instance = new CriteriaPodcast();
        }
        return instance;
    }
}
