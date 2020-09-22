package com.project.maven.appService.config;

import com.project.maven.appService.model.Kabupaten;
import com.project.maven.appService.model.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class KabupatenConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource data;

    public List<Kabupaten> getName(){
        Connection connection = null;

        try{
            connection = data.getConnection();
//            connection = DriverManager.getConnection("jdbc:mysql://192.168.100.250/bmt_v1? " +
//                    "user=root&password=passwordnyaRoot&serverTimezone=UTC");

            String baseQuery = "select kodeBPS, namaKabupaten, namaProvinsi from kabupaten k join provinsi p on " +
                    "(k.Provinsi = p.kodeBps) where k.kodeProvinsi = p.kodeBps";
            PreparedStatement preparedStatement = connection.prepareStatement(baseQuery);

            ResultSet res = preparedStatement.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("kodeBPS")+ res.getString("namaKabupaten") + res.getString("namaProvinsi"));
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
        return null;
    }

    public List<Kabupaten> getNama(Optional<String> nama) {

        String baseQuery = "select k.kodeBps as idKabupaten, k.namaKabupaten, p.namaProvinsi, p.kodeBps as idProvinsi from kabupaten k join provinsi p on " +
                            "k.kodeProvinsi = p.kodeBps where 1 = 1 ";

        Object[] param = new Object[1];
        if(nama.isPresent()){
            baseQuery += "and namaKabupaten like CONCAT('%', ?, '%')";
            param[0] = nama.get();
        }
        return jdbcTemplate.query(baseQuery,param,(rs, rowNUm)-> {
            Kabupaten props = new Kabupaten();
            props.setKodeBps(rs.getInt("idKabupaten"));
            props.setNamaKabupaten(rs.getString("namaKabupaten"));
            Provinsi prov = new Provinsi();
            prov.setNamaProvinsi(rs.getString("namaProvinsi"));
            prov.setKodeBps(rs.getInt("idProvinsi"));
            props.setProvinsi(prov);
            return props;
        });

    }

    public List<Kabupaten> getNama() {

        String baseQuery = "select k.kodeBps as idKabupaten, k.namaKabupaten, p.namaProvinsi, p.kodeBps as idProvinsi from kabupaten k join provinsi p on " +
                "(k.kodeProvinsi = p.kodeBps) where k.kodeProvinsi = p.kodeBps";

        return jdbcTemplate.query(baseQuery, (rs, rowNUm) -> {
            Kabupaten props = new Kabupaten();
            props.setKodeBps(rs.getInt("idKabupaten"));
            props.setNamaKabupaten(rs.getString("namaKabupaten"));
            Provinsi prov = new Provinsi();
            prov.setNamaProvinsi(rs.getString("namaProvinsi"));
            prov.setKodeBps(rs.getInt("idProvinsi"));
            props.setProvinsi(prov);
            return props;
        });

    }

    public List<Kabupaten> getNama(int idProv) {

        String baseQuery = "select k.kodeBps as idKabupaten, k.namaKabupaten, p.namaProvinsi, k.kodeProvinsi as idProvinsi from kabupaten k inner join provinsi p on " +
                "k.kodeProvinsi = p.kodeBps where kodeProvinsi = ? ";

        Object[] param = {idProv};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Kabupaten props = new Kabupaten();
            props.setKodeBps(rs.getInt("idKabupaten"));
            props.setNamaKabupaten(rs.getString("namaKabupaten"));
            Provinsi prov = new Provinsi();
            prov.setNamaProvinsi(rs.getString("namaProvinsi"));
            props.setProvinsi(prov);
            return props;
        });

    }



        public Optional<Kabupaten> getProvinsiById(int id) {
        String baseQuery = "select kodeBPS, namaKabupaten, kodeProvinsi from kabupaten where kodeBps = ?";
        Object param[] = {id};

        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Kabupaten.class)));

        } catch (Exception e){
            return Optional.empty();
        }
    }

    public void insertKabupaten(Kabupaten kabupaten){
        String baseQuery = "insert into kabupaten (kodeBPS, namaKabupaten, kodeProvinsi) values (?, ? ,?)";
        Object parameters[] = {kabupaten.getKodeBps(),kabupaten.getNamaKabupaten(),kabupaten.getKodeProvinsi()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateKabupaten(Kabupaten kabupaten){
        String baseQuery = "update kabupaten set namaKabupaten = ? where kodeBPS = ?";
        Object parameters[] = {kabupaten.getNamaKabupaten(),kabupaten.getKodeBps()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateKabupaten(Kabupaten kabupaten){
        Optional<Kabupaten> kabs  = getProvinsiById(kabupaten.getKodeBps());
        if (kabs.isPresent()){
            updateKabupaten(kabupaten);
        } else {
            insertKabupaten(kabupaten);
        }
    }


    public void deleteKabupaten(Kabupaten kabupaten){
        String baseQuery = "delete from kabupaten where kodeBPS = ?";

        Object parameters[] = {kabupaten.getKodeProvinsi()};

        jdbcTemplate.update(baseQuery,parameters);
    }


}
