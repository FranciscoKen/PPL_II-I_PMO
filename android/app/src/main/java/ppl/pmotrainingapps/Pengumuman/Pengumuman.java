package ppl.pmotrainingapps.Pengumuman;

/**
 * Created by David on 3/21/2018.
 */

public class Pengumuman {
    private String title;
    private String details;

    public Pengumuman(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}