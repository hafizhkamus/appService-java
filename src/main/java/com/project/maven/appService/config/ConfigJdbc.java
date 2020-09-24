package com.project.maven.appService.config;


import com.project.maven.appService.datatables.DataTableRequest;
import com.project.maven.appService.model.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Component
public class ConfigJdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource data;

    public void getName(){
        Connection connection = null;

        try{
            connection = data.getConnection();
//            connection = DriverManager.getConnection("jdbc:mysql://192.168.100.250/bmt_v1? " +
//                    "user=root&password=passwordnyaRoot&serverTimezone=UTC");

            String baseQuery = "select namaProvinsi, kodeBPS from provinsi";
            PreparedStatement preparedStatement = connection.prepareStatement(baseQuery);

            ResultSet res = preparedStatement.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("kodeBPS")+ " + " + res.getString("namaProvinsi"));
            }

        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("\n\n\n");

//            System.out.println("SQLExeption" + " " + e.getMessage());
//            System.out.println("SQLState" + " " + e.getSQLState());
//            System.out.println("VendorError"+ " " + e.getErrorCode());
        }
//        finally {
//            try {
//                if (connection != null && !connection.isClosed()){
//                    connection.close();
//                }
//            } catch (SQLException exception){
//                exception.printStackTrace();
//            }
//        }
    }

    public List<Provinsi> getNama(){

        String baseQuery = "select namaProvinsi, kodeBPS from provinsi";
        List<Provinsi> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Provinsi.class));
        return prop;
        /*(rs, rowNUm)-> {
            Provinsi props = new Provinsi();
            props.setKodeBps(rs.getInt("kodeBps"));
            props.setNamaProvinsi(rs.getString("namaProvinsi"));
            return props;
        });*/
    }

    public List<Provinsi> getNamaProvinsi(){

        String baseQuery = "select namaProvinsi from provinsi";
        List<Provinsi> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Provinsi.class));
        return prop;
        /*(rs, rowNUm)-> {
            Provinsi props = new Provinsi();
            props.setKodeBps(rs.getInt("kodeBps"));
            props.setNamaProvinsi(rs.getString("namaProvinsi"));
            return props;
        });*/
    }

    public Optional<Provinsi> getProvinsiById(int id) {
        String baseQuery = "select namaProvinsi, kodeBPS from provinsi where kodeBps = ?";
        Object param[] = {id};

//        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        parameterSource.addValue("kodeBps", id);
        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Provinsi.class)));

        } catch (Exception e){
            return Optional.empty();
        }
    }

    public void insertProvinsi(Provinsi provinsi){
        String baseQuery = "insert into provinsi (kodeBps, namaProvinsi) values (?, ?)";
        Object parameters[] = {provinsi.getKodeBps(),provinsi.getNamaProvinsi()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateProvinsi(Provinsi provinsi){
        String baseQuery = "update provinsi set namaProvinsi=? where kodeBps =?";
        Object parameters[] = {provinsi.getNamaProvinsi(),provinsi.getKodeBps()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void deleteProvinsi(Provinsi provinsi){
        String baseQuery = "delete from provinsi where kodeBps =?";
        Object parameters[] = {provinsi.getKodeBps()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public Provinsi getDeleteProvinsi(Provinsi provinsi) throws DataAccessException {
        String baseQuery = "delete from provinsi where kodeBps =?";
        Object parameters[] = {provinsi.getKodeBps()};

        jdbcTemplate.update(baseQuery,parameters);

        return provinsi;
    }

    public void deleteProv(Provinsi provinsi){
        Optional<Provinsi> prop  = getProvinsiById(provinsi.getKodeBps());
        if (prop.isPresent()){
            deleteProvinsi(provinsi);
        } else {
            insertProvinsi(provinsi);
        }
    }

    public void insertOrUpdateProvinsi(Provinsi provinsi){
        Optional<Provinsi> prop  = getProvinsiById(provinsi.getKodeBps());
        if (prop.isPresent()){
            updateProvinsi(provinsi);
        } else {
            insertProvinsi(provinsi);
        }
    }

    public Integer getBanyakProvinsi(DataTableRequest req){
        String baseQuery = "select count(kodeBPS) as banyak from provinsi";
        if(!req.getExtraParam().isEmpty()){
            String namaProvinsi = (String) req.getExtraParam().get("namaProvinsi");
            baseQuery = "select count(kodeBPS) as banyak from provinsi where namaProvinsi like concat('%', ?, '%')";
            return jdbcTemplate.queryForObject(baseQuery, Integer.class, namaProvinsi);
        } else {
            return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
        }
    }

    public List<Provinsi> getAllProvinsi(DataTableRequest request){
        String baseQuery = "SELECT kodeBPS, namaProvinsi FROM provinsi "
                + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        if(!request.getExtraParam().isEmpty()){
            String namaProvinsi = (String) request.getExtraParam().get("namaProvinsi");
             baseQuery = "SELECT kodeBPS, namaProvinsi FROM provinsi where namaProvinsi like concat('%', ?, '%') "
                    + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Provinsi.class),namaProvinsi,
                request.getLength(), request.getStart());
        } else {
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Provinsi.class),
                    request.getLength(), request.getStart());
        }

    }






}
