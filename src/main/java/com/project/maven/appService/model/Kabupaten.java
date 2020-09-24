package com.project.maven.appService.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Kabupaten {

    @NotNull(message = "kok kosong?")
    @Min(value = 1000, message = "kurang cok!!")
    private Integer kodeBps;

    @NotEmpty(message = "kok kosong?")
    @Size(min = 4, message = "pendek kali nama kau!")
    private String namaKabupaten;

    private Integer kodeProvinsi;

    private String namaProvinsi;

    private Provinsi provinsi;

    public Integer getKodeBps() {
        return kodeBps;
    }

    public void setKodeBps(Integer kodeBps) {
        this.kodeBps = kodeBps;
    }

    public String getNamaKabupaten() {
        return namaKabupaten;
    }

    public void setNamaKabupaten(String namaKabupaten) {
        this.namaKabupaten = namaKabupaten;
    }

    public Integer getKodeProvinsi() {
        return kodeProvinsi;
    }

    public void setKodeProvinsi(Integer kodeProvinsi) {
        this.kodeProvinsi = kodeProvinsi;
    }

    public Provinsi getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(Provinsi provinsi) {
        this.provinsi = provinsi;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }
}
