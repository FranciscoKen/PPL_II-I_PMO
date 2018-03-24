package ppl.pmotrainingapps.Pengumuman;

/**
 * Created by David on 3/21/2018.
 */

public class Pengumuman {
    private String title;
    private String details;
    private int id;



    public Pengumuman(int id, String title, String details) {
        this.title = title;
        this.details = details;
        this.id = id;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
