public abstract class Comenzi {
    abstract void add(String nume);
    abstract void delete(String id);
    abstract void list(String idstreamer);
    abstract void recomandare(String tip);
    abstract void exit();
    //am declarat metodele abstracte pentru a putea fi suprascrise in clasa Comenziexec
    //metoda execute va executa comanda respectiva si se va intra cu ceea ce am salvat in comenzifisier in clasa ProiectPoo
    public final void execute(String comenzi) {
        String[] buf = comenzi.split(",");
        for (String s : buf) {
            if (s.split(":")[0].equals("ADD")) {
                add(s.split(":")[1]);
            }
            if (s.split(":")[0].equals("DELETE")) {
                delete(s.split(":")[1]);
            }
            if (s.split(":")[0].equals("LIST")) {
                list(s.split(":")[1]);
            }
            if (s.split(":")[0].equals("RECOMMEND")) {
                recomandare(s.split(":")[1]);
            }
        }
        exit();
    }
}
