package ppl.pmotrainingapps.calendar;

import java.util.Date;

/**
 * Created by ayamberkakienam on 4/5/2018.
 */

public class Kegiatan {
    private int id;
    private String nama;
    private String target;
    private String deskripsi;
    private Date tanggal;
    private String lokasi;

    public Kegiatan() {
    }

    public Kegiatan(int id, String nama, String target, String deskripsi, Date tanggal, String lokasi) {
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

    public String getNama() {
        return nama;
    }

    public String getTarget() {
        return target;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public long getTanggal() {
        return new Date().getTime();
    }

    public String getLokasi() {
        return lokasi;
    }
}
