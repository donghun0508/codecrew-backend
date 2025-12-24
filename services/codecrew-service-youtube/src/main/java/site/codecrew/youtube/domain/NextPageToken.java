package site.codecrew.youtube.domain;

public class NextPageToken {

    private Status status;
    private String value;

    public NextPageToken(Status status, String value) {
        this.status = status;
        this.value = value;
    }

    public static NextPageToken init() {
        return new NextPageToken(Status.INIT, null);
    }

    public boolean hasNext() {
        if(isInit()) {
            return true;
        }
        if(isProcessing() && value != null) {
            return true;
        }
        return false;
    }

    private boolean isInit() {
        return status == Status.INIT;
    }

    private boolean isProcessing() {
        return status == Status.PROCESSING;
    }

    public void update(NextPageToken nextPageToken) {

    }

    public boolean isNotEnd() {
        return false;
    }

    enum Status {
        INIT,
        PROCESSING
    }
}
