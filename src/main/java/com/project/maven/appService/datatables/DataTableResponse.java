package com.project.maven.appService.datatables;

import java.util.List;
import lombok.Data;

@Data
public class DataTableResponse<T> {

    private List<T> data;
    private Integer draw;
    private Integer recordFiltered, recordsTotal;
}
