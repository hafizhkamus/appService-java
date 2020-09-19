package com.project.maven.appService.model;

public class Kecamatan {

    private Integer idKecamatan;
    private String namaKecamatan;
    private Integer kodeKabupaten;
    private Kabupaten kabupaten;
    private Integer idKabupaten;
    private Provinsi provinsi;
    private Integer kodeProvinsi;


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

    public Provinsi getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(Provinsi provinsi) {
        this.provinsi = provinsi;
    }

    public Integer getKodeProvinsi() {
        return kodeProvinsi;
    }

    public void setKodeProvinsi(Integer kodeProvinsi) {
        this.kodeProvinsi = kodeProvinsi;
    }

    public Integer getIdKabupaten() {
        return idKabupaten;
    }

    public void setIdKabupaten(Integer idKabupaten) {
        this.idKabupaten = idKabupaten;
    }
}
