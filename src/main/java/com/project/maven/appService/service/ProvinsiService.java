package com.project.maven.appService.service;


import com.project.maven.appService.config.ConfigJdbc;
import com.project.maven.appService.datatables.DataTableRequest;
import com.project.maven.appService.datatables.DataTableResponse;
import com.project.maven.appService.model.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProvinsiService {

    @Autowired
    private ConfigJdbc jdbcTemplate;

    public DataTableResponse<Provinsi> datatables(DataTableRequest res){
        DataTableResponse<Provinsi> tables = new DataTableResponse<>();
        tables.setData(jdbcTemplate.getAllProvinsi( res));
        Integer total = jdbcTemplate.getBanyakProvinsi( res);
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }
}
