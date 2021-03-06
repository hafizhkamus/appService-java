package com.project.maven.appService.config;


import com.project.maven.appService.datatables.DataTableRequest;
import com.project.maven.appService.model.Kabupaten;
import com.project.maven.appService.model.Kecamatan;
import com.project.maven.appService.model.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class KecamatanConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource data;

    public List<Kecamatan> getNama(Optional<String> nama) {

        String baseQuery = "select k.kodeBps as idKecamatan, k.namaKecamatan, p.namaKabupaten, p.kodeBps as idKabupaten, e.namaProvinsi, e.kodeBps as idProvinsi from kecamatan k join kabupaten p  on " +
                "k.kodeKabupaten = p.kodeBps join provinsi e on p.kodeProvinsi = e.kodeBps   where 1 = 1 ";

        Object[] param = new Object[1];
        if(nama.isPresent()){
            baseQuery += "and namaKecamatan like CONCAT('%', ?, '%')";
            param[0] = nama.get();
        }
        return jdbcTemplate.query(baseQuery,param,(rs, rowNUm)-> {
            Kecamatan kecamatan = new Kecamatan();
            kecamatan.setIdKecamatan(rs.getInt("idKecamatan"));
            kecamatan.setNamaKecamatan(rs.getString("namaKecamatan"));
            Kabupaten prov = new Kabupaten();
            prov.setNamaKabupaten(rs.getString("namaKabupaten"));
            prov.setKodeBps(rs.getInt("idKabupaten"));
            kecamatan.setKabupaten(prov);
            Provinsi props = new Provinsi();
            props.setNamaProvinsi(rs.getString("namaProvinsi"));
            props.setKodeBps(rs.getInt("idProvinsi"));
            prov.setProvinsi(props);
            return kecamatan;
        });

    }

    public List<Kecamatan> getNama() {

        String baseQuery = "select k.kodeBps as idKecamatan, k.namaKecamatan, p.namaKabupaten, p.kodeBps as idKabupaten, e.namaProvinsi, e.kodeBps as idProvinsi from kecamatan k join kabupaten p on " +
                "(k.kodeKabupaten = p.kodeBps) join provinsi e on (p.kodeProvinsi = e.kodeBps) where k.kodeKabupaten = p.kodeBps";


        return jdbcTemplate.query(baseQuery,(rs, rowNUm)-> {
            Kecamatan kecamatan = new Kecamatan();
            kecamatan.setIdKecamatan(rs.getInt("idKecamatan"));
            kecamatan.setNamaKecamatan(rs.getString("namaKecamatan"));
            Kabupaten prov = new Kabupaten();
            prov.setNamaKabupaten(rs.getString("namaKabupaten"));
            prov.setKodeBps(rs.getInt("idKabupaten"));
            kecamatan.setKabupaten(prov);
            Provinsi props = new Provinsi();
            props.setNamaProvinsi(rs.getString("namaProvinsi"));
            props.setKodeBps(rs.getInt("idProvinsi"));
            prov.setProvinsi(props);
            return kecamatan;
        });
    }

    public Optional<Kecamatan> getKecamatanById(int id) {
        String baseQuery = "select k.kodeBPS as idKecamatan, k.namaKecamatan, k.kodeKabupaten, kb.kodeProvinsi as idProvinsi from kecamatan k join kabupaten kb on k.kodeKabupaten = kb.kodeBps  where k.kodeBps = ?";
        Object param[] = {id};

        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Kecamatan.class)));

        } catch (Exception e){
            return Optional.empty();
        }
    }

    public void insertKabupaten(Kecamatan kecamatan){
        String baseQuery = "insert into kecamatan (kodeBPS, namaKecamatan, kodeKabupaten) values (?, ? ,?)";
        Object parameters[] = {kecamatan.getIdKecamatan(),kecamatan.getNamaKecamatan(), kecamatan.getKodeKabupaten()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateKabupaten(Kecamatan kecamatan){
        String baseQuery = "update kabupaten set namaKabupaten = ? where kodeBPS = ?";
        Object parameters[] = {kecamatan.getNamaKecamatan(),kecamatan.getIdKecamatan()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateKabupaten(Kecamatan kecamatan){
        Optional<Kecamatan> kabs  = getKecamatanById(kecamatan.getIdKecamatan());
        if (kabs.isPresent()){
            updateKabupaten(kecamatan);
        } else {
            insertKabupaten(kecamatan);
        }
    }

    public Integer getBanyakKecamatan(){
        String baseQuery = "select count(kodeBPS) as banyak from kecamatan";
        return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
    }

    public List<Kecamatan> getAllKecamatan(DataTableRequest request){
        String baseQuery = "SELECT k.kodeBPS as idKecamatan, k.namaKecamatan, e.namaKabupaten, p.namaProvinsi  FROM kecamatan k join kabupaten e on k.kodeKabupaten = e.kodeBps join provinsi p on e.kodeProvinsi = p.kodeBps "
                + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Kecamatan.class),
                request.getLength(), request.getStart());

    }
}
