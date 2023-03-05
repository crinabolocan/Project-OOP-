import java.util.ArrayList;
import java.util.List;

public class CriteriaMuzic implements Criteria {
    //design pattern: Filter
    //criteriul de cautare: muzica
    public List<Streams> meetCriteria(List<Streams> streams) {
        List<Streams> muzic = new ArrayList<Streams>();
        //iterez prin lista de streamuri si salvez in lista muzic doar streamurile de tip muzica, 1
        for (Streams s : streams) {
            if (s.getStreamType() == 1) {
                muzic.add(s);
            }
        }
        return muzic;
    }
    //Șablonul Singleton asigură faptul că o clasă are o singură instanță și furnizează un punct
    //global de acces la ea
    private static CriteriaMuzic instance;
    private CriteriaMuzic() {
    }
    public static CriteriaMuzic getInstance() {
        if (instance == null) {
            instance = new CriteriaMuzic();
        }
        return instance;
    }
}
