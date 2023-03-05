public class Streamers extends ProiectPOO {
    private int streamerType;
    private int id;
    private String name;
    public Streamers(int streamerType, int id, String name) {
        this.streamerType = streamerType;
        this.id = id;
        this.name = name;
    }
    public Streamers() {
    }

    public int getStreamerType() {
        return streamerType;
    }

    public void setStreamerType(int streamerType) {
        this.streamerType = streamerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return streamerType + "," + id + "," + name;
    }
}

class StreamersBuilder {
    //design pattern: Builder
    //clasa care construieste streamerii
    private final Streamers streamers = new Streamers();
    public StreamersBuilder setStreamerType(int streamerType) {
        streamers.setStreamerType(streamerType);
        return this;
    }
    public StreamersBuilder setId(int id) {
        streamers.setId(id);
        return this;
    }
    public StreamersBuilder setName(String name) {
        streamers.setName(name);
        return this;
    }
    public Streamers build() {
        return streamers;
    }
}
