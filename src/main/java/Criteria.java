import java.util.*;
public interface Criteria {
    //design pattern: Filter
    public List<Streams> meetCriteria(List<Streams> streams);
}
