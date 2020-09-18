package com.project.maven.appService.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Provinsi {

//    @NotEmpty(message = "kok kosong?")
//    @Size(min = 4, message = "pendek kali nama kau!")
    private String namaProvinsi;

//    @NotNull(message = "kok kosong?")
//    @Min(value = 1000, message = "kurang cok!!")
    private Integer kodeBps;

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public Integer getKodeBps() {
        return kodeBps;
    }

    public void setKodeBps(Integer kodeBps) {
        this.kodeBps = kodeBps;
    }
}
