package ppl.pmotrainingapps.calendar;

/**
 * Created by ayamberkakienam on 4/5/2018.
 */

public class Kegiatan {
    private String title;
    private String target;
    private String lokasi;
    private String waktu;
    private String deskripsi;

    public Kegiatan() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Kegiatan(String title, String target, String lokasi, String waktu, String deskripsi) {
        this.title = title;
        this.target = target;
        this.lokasi = lokasi;
        this.waktu = waktu;
        this.deskripsi = deskripsi;
    }
}
