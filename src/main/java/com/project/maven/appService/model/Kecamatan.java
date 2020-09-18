package com.project.maven.appService.model;

public class Kecamatan {

    private Integer idKecamatan;
    private String namaKecamatan;
    private Integer kodeKabupaten;
    private Kabupaten kabupaten;

    public Integer getIdKecamatan() {
        return idKecamatan;
    }

    public void setIdKecamatan(Integer idKecamatan) {
        this.idKecamatan = idKecamatan;
    }

    public String getNamaKecamatan() {
        return namaKecamatan;
    }

    public void setNamaKecamatan(String namaKecamatan) {
        this.namaKecamatan = namaKecamatan;
    }

    public Integer getKodeKabupaten() {
        return kodeKabupaten;
    }

    public void setKodeKabupaten(Integer kodeKabupaten) {
        this.kodeKabupaten = kodeKabupaten;
    }

    public Kabupaten getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(Kabupaten kabupaten) {
        this.kabupaten = kabupaten;
    }
}
