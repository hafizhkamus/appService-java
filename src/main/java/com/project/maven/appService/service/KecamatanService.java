package com.project.maven.appService.service;

import com.project.maven.appService.config.KabupatenConfig;
import com.project.maven.appService.config.KecamatanConfig;
import com.project.maven.appService.datatables.DataTableRequest;
import com.project.maven.appService.datatables.DataTableResponse;
import com.project.maven.appService.model.Kabupaten;
import com.project.maven.appService.model.Kecamatan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KecamatanService {

    @Autowired
    private KecamatanConfig kecamatanConfig;

    public DataTableResponse<Kecamatan> datatables(DataTableRequest res){
        DataTableResponse<Kecamatan> tables = new DataTableResponse<>();
        tables.setData(kecamatanConfig.getAllKecamatan( res));
        Integer total = kecamatanConfig.getBanyakKecamatan();
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }
}
