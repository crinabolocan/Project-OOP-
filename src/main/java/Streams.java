public class Streams {
    private int streamType;
    private int id;
    private int streamGenre;
    private Long noOfStreams;
    private int streamerId;
    private Long length;
    private Long dateAdded;
    private String name;

    public Streams() {
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStreamGenre(int streamGenre) {
        this.streamGenre = streamGenre;
    }

    public void setNoOfStreams(Long noOfStreams) {
        this.noOfStreams = noOfStreams;
    }

    public void setStreamerId(int streamerId) {
        this.streamerId = streamerId;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public void setDateAdded(Long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getStreamType() {
        return streamType;
    }

    public int getId() {
        return id;
    }

    public Long getNoOfStreams() {
        return noOfStreams;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public Long getLength() {
        return length;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    public int getStreamerId() {
        return streamerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return streamType + "," + id + "," + streamGenre + "," + noOfStreams + "," + streamerId + "," + length + "," + dateAdded + "," + name;
    }
}

class StreamsBuilder {
    private final Streams streams = new Streams();

    public StreamsBuilder setStreamType(int streamType) {
        streams.setStreamType(streamType);
        return this;
    }

    public StreamsBuilder setId(int id) {
        streams.setId(id);
        return this;
    }

    public StreamsBuilder setStreamGenre(int streamGenre) {
        streams.setStreamGenre(streamGenre);
        return this;
    }

    public StreamsBuilder setNoOfStreams(Long noOfStreams) {
        streams.setNoOfStreams(noOfStreams);
        return this;
    }

    public StreamsBuilder setStreamerId(int streamerId) {
        streams.setStreamerId(streamerId);
        return this;
    }

    public StreamsBuilder setLength(Long length) {
        streams.setLength(length);
        return this;
    }

    public StreamsBuilder setDateAdded(Long dateAdded) {
        streams.setDateAdded(dateAdded);
        return this;
    }

    public StreamsBuilder setName(String name) {
        streams.setName(name);
        return this;
    }

    public Streams build() {
        return streams;
    }
}
