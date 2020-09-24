package com.project.maven.appService.service;


import com.project.maven.appService.config.KabupatenConfig;
import com.project.maven.appService.datatables.DataTableRequest;
import com.project.maven.appService.datatables.DataTableResponse;
import com.project.maven.appService.model.Kabupaten;
import com.project.maven.appService.model.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KabupatenService {

    @Autowired
    private KabupatenConfig kabupatenConfig;

    public DataTableResponse<Kabupaten> datatables(DataTableRequest res){
        DataTableResponse<Kabupaten> tables = new DataTableResponse<>();
        tables.setData(kabupatenConfig.getAllKabupaten( res));
        Integer total = kabupatenConfig.getBanyakKabupaten();
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }
}
