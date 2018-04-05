package ppl.pmotrainingapps.calendar;

/**
 * Created by ayamberkakienam on 4/5/2018.
 */

public class CalendarDetailRow {
    private String title;
    private String target;
    private String lokasi;
    private String waktu;
    private String deskripsi;

    public CalendarDetailRow() {

    }

    public CalendarDetailRow(String title, String target, String lokasi, String waktu, String deskripsi) {
        this.title = title;
        this.target = target;
        this.lokasi = lokasi;
        this.waktu = waktu;
        this.deskripsi = deskripsi;
    }
}
