package com.project.maven.appService.datatables;


import com.sun.javafx.collections.MappingChange;
import java.util.Map;
import lombok.Data;

@Data
public class DataTableRequest {

    private Integer draw, start, length, sortCol;
    private Map<String, Object> extraParam;
    private String sortDir;


}
