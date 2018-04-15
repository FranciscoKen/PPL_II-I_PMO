package ppl.pmotrainingapps.calendar;

/**
 * Created by ayamberkakienam on 4/5/2018.
 */

public class Kegiatan {
    private int id;
    private String nama;
    private String target;
    private String deskripsi;
    private String tanggal;
    private String lokasi;

    public Kegiatan() {
    }

    public Kegiatan(int id, String nama, String target, String deskripsi, String tanggal, String lokasi) {
        this.id = id;
        this.nama = nama;
        this.target = target;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    String getNama() {
        return nama;
    }

    String getTarget() {
        return target;
    }

    String getDeskripsi() {
        return deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    String getLokasi() {
        return lokasi;
    }
}
