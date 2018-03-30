package ppl.pmotrainingapps.Pengumuman;

/**
 * Created by David on 3/21/2018.
 */

public class Pengumuman {

    private int id_pengumuman;
    private int id_kegiatan;
    private String judul;
    private String tanggal;
    private String konten_teks;
    private String konten_gambar;

    public Pengumuman(int id_pengumuman, int id_kegiatan, String judul, String tanggal, String konten_teks, String konten_gambar) {
        this.judul = judul;
        this.konten_teks = konten_teks;
        this.konten_gambar = konten_gambar;
        this.tanggal = tanggal;
        this.id_pengumuman = id_pengumuman;
        this.id_kegiatan = id_kegiatan;
    }

    public int getId_pengumuman() {
        return id_pengumuman;
    }

    public void setId_pengumuman(int id_pengumuman) {
        this.id_pengumuman = id_pengumuman;
    }

    public int getId_kegiatan() {
        return id_kegiatan;
    }

    public void setId_kegiatan(int id_kegiatan) {
        this.id_kegiatan = id_kegiatan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKonten_teks() {
        return konten_teks;
    }

    public void setKonten_teks(String konten_teks) {
        this.konten_teks = konten_teks;
    }

    public String getKonten_gambar() {
        return konten_gambar;
    }

    public void setKonten_gambar(String konten_gambar) {
        this.konten_gambar = konten_gambar;
    }



}
