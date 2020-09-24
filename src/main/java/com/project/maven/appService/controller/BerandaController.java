package com.project.maven.appService.controller;

import com.project.maven.appService.config.ConfigJdbc;
import com.project.maven.appService.config.KabupatenConfig;
import com.project.maven.appService.config.KecamatanConfig;
import com.project.maven.appService.datatables.DataTableRequest;
import com.project.maven.appService.datatables.DataTableResponse;
import com.project.maven.appService.model.Kabupaten;
import com.project.maven.appService.model.Kecamatan;
import com.project.maven.appService.model.Provinsi;
import com.project.maven.appService.service.KabupatenService;
import com.project.maven.appService.service.KecamatanService;
import com.project.maven.appService.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BerandaController {

    @Autowired
    private ConfigJdbc config;

    @Autowired
    private KabupatenConfig conf;

    @Autowired
    private KecamatanConfig configure;

    @Autowired
    private ProvinsiService service;

    @Autowired
    private KabupatenService _service;

    @Autowired
    private KecamatanService services;


    @GetMapping(path = "/")
    public String getName(ModelMap model){
        model.put("nama", "welcome");
        return "home";
    }

    @GetMapping(path = "/about")
    public ModelAndView getAbout(ModelAndView model){
        Map<String, String> map = new HashMap();
        new ModelAndView("about", map);
        map.put("about", "ini about");
        return new ModelAndView("about", map);
    }

    @GetMapping(path = "/listprovinsi")
    public ModelAndView getAllProvinsi(DataTableResponse<Provinsi> datatables){
        Map<String, List<Provinsi>> map = new HashMap<>();
        map.put("provinsi", config.getNama());
        return new ModelAndView("provinsi", map);
    }

    @GetMapping(path = "/api/list-provinsi")
    public ResponseEntity<List<Provinsi>> getProvinsi(){
        return ResponseEntity.ok().body(config.getNama());
    }

    @GetMapping(path = "/api/list-nama-provinsi")
    public ResponseEntity<List<Provinsi>> getNamaProvinsi(){
        return ResponseEntity.ok().body(config.getNamaProvinsi());
    }

    @GetMapping(path = "/provinsidetails/{id}")
    public String getProvDetails(@PathVariable("id") Integer id, Model model){
        model.addAttribute("provinsi", config.getProvinsiById(id).get());
        return "insert";
    }

    @GetMapping(path = "/addprovinsi")
    public String saveProvinsi(Model model){
        model.addAttribute("provinsi", new Provinsi());
        return "insert";
    }

    @PostMapping(path = "/saveprov")
    public String insertProvinsi(@Valid @ModelAttribute Provinsi provinsi, BindingResult res){
        if(res.hasErrors()){
            return "insert";
        }
        config.insertOrUpdateProvinsi(provinsi);
        return "redirect:list-provinsi";
    }

    @GetMapping(path = "/deleteprovinsi")
    public String cleanProvinsi(Model model){
        model.addAttribute("provinsi", new Provinsi());
        return "delete";
    }

//    @DeleteMapping(path = "/deleteprov")
//    public String deleteProvinsi(@ModelAttribute Provinsi provinsi){
//        config.deleteProvinsi(provinsi);
//        return "redirect:list-provinsi";
//    }

    @PostMapping(path = "/api/provinsi/save")
    public ResponseEntity<Map<String, Object>> saveProvinsiJson(@RequestBody Provinsi provinsi){
        Map<String,Object> status = new HashMap<>();
        config.insertOrUpdateProvinsi(provinsi);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @GetMapping(path = "/api/provinsidetails/{id}")
    public ResponseEntity<Provinsi> getProvinsiDetails(@PathVariable("id") Integer id){
        Optional<Provinsi> prov = config.getProvinsiById(id);
        if(prov.isPresent()){
            return ResponseEntity.ok().body(prov.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/api/provinsi-deleted/{id}")
    public ResponseEntity<?> deleteProvinsiById(@PathVariable("id") Provinsi provinsi){
        Map<String,Object> status = new HashMap<>();
        config.deleteProv(provinsi);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }




    /// kabupaten.........




    @GetMapping(path = "/list-kabupaten")
    public ModelAndView getKabupaten(){
        Map<String, List<Kabupaten>> map = new HashMap<>();
        map.put("kabupaten", conf.getNama());
        return new ModelAndView("kabupaten", map);
    }

    @GetMapping(path = "/kabupatendetails/{id}")
    public String getKabDetails(@PathVariable("id") Integer id, Model model){
        model.addAttribute("kabupaten", conf.getProvinsiById(id).get());
        model.addAttribute("provinsi", config.getNama());
        return "insertkabupaten";
    }

    @GetMapping(path = "/carikabupaten")
    public ModelAndView getOneKabupaten(@RequestParam(name= "nama")Optional<String> namaParam){
        Map<String, List<Kabupaten>> map = new HashMap<>();
        map.put("kabupaten", conf.getNama(namaParam));
        return new ModelAndView("kabupaten", map);
    }

    @GetMapping(path = "/addkabupaten")
    public String saveKabupaten(Model model){
        model.addAttribute("kabupaten", new Kabupaten());
        model.addAttribute("provinsi", config.getNama());
        return "insertkabupaten";
    }

    @PostMapping(path = "/savekab")
    public String insertKabupaten(@Valid @ModelAttribute Kabupaten kabupaten, BindingResult res){
        if(res.hasErrors()){
            return "insertkabupaten";
        }
        conf.insertOrUpdateKabupaten(kabupaten);
        return "redirect:list-kabupaten";
    }

    @GetMapping(path = "/api/listkabupatenjson")
    public ResponseEntity<List<Kabupaten>> getAllKabupaten(){
        return ResponseEntity.ok().body(conf.getNama());
    }

    @PostMapping(path = "/api/kabupaten/save")
    public ResponseEntity<Map<String, Object>> saveKabupatenJson(@RequestBody Kabupaten kabupaten){
        Map<String,Object> status = new HashMap<>();
        conf.insertKabupaten(kabupaten);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @GetMapping(path = "/api/kabupatendetails/{id}")
    public ResponseEntity<Kabupaten> getKabupatenDetails(@PathVariable("id") Integer id){
        Optional<Kabupaten> prov = conf.getProvinsiById(id);
        if(prov.isPresent()){
            return ResponseEntity.ok().body(prov.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

//    @GetMapping(path = "/listkabupaten/{id}")
//    public ResponseEntity<List<Kabupaten>> getKecamatan(@PathVariable("id") Integer id){
//        return ResponseEntity.ok().body(conf.getNama(id));
//    }

    @GetMapping(path = "/kabupaten-deleted/{id}")
    public String deleteKabupaten(@PathVariable("id") Integer id, Model model){
        Kabupaten kabupaten = conf.getProvinsiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        conf.deleteKabupaten(kabupaten);
        model.addAttribute("kabupaten", conf.getNama());
        return "kabupaten";
    }

//    @GetMapping(path = "/listkabupaten")
//    public ResponseEntity<Optional<Kabupaten>> getKabupaten(@PathVariable("id") Integer id){
//        return ResponseEntity.ok().body(conf.getProvinsiById(id));
//    }

//    @PostMapping(path = "/savekab")
//    public String deleteKabupaten(@Valid @ModelAttribute Kabupaten kabupaten, BindingResult res){
//        if(res.hasErrors()){
//            return "insertkabupaten";
//        }
//        conf.deleteKabupaten(kabupaten);
//        return "redirect:list-kabupaten";
//    }





//    @GetMapping(path = "provinsi-deleted/{id}")
//    public ModelAndView deleteProvinsi(@PathVariable("id") Integer id){
//        Map<String, Provinsi> map = new HashMap<>();
//        Provinsi prop = new Provinsi();
//        prop.setKodeBps(94);
//        prop.setNamaProvinsi("PAPUA");
//        map.put("provinsi", config.getDeleteProvinsi(prop));
//        return new ModelAndView("deleteProvinsi", map);
//    }



    // kecamatan

    @GetMapping(path = "/list-kecamatan")
    public ModelAndView getKecamatan(){
        Map<String, List<Kecamatan>> map = new HashMap<>();
        map.put("kecamatan", configure.getNama());
        return new ModelAndView("kecamatan", map);
    }

    @GetMapping(path = "/kecamatandetails/{id}")
    public String getKecdetails(@PathVariable("id") Integer id, Model model){
        model.addAttribute("kecamatan", configure.getKecamatanById(id).get());
        model.addAttribute("kabupaten", conf.getNama());
        return "insertkecamatan";
    }

    @GetMapping(path = "/carikecamatan")
    public ModelAndView getOneKecamatan(@RequestParam(name= "namaKec")Optional<String> namaParam){
        Map<String, List<Kecamatan>> map = new HashMap<>();
        map.put("kecamatan", configure.getNama(namaParam));
        return new ModelAndView("kecamatan", map);
    }

    @GetMapping(path = "/addkecamatan")
    public String saveKecamatan(Model model){
        model.addAttribute("kecamatan", new Kecamatan());
        model.addAttribute("kabupaten", conf.getNama());
        return "insertkecamatan";
    }

    @PostMapping(path = "/savekec")
    public String insertKecamatan(@Valid @ModelAttribute Kecamatan kecamatan, BindingResult res){
        if(res.hasErrors()){
            return "insertkecamatan";
        }
        configure.insertOrUpdateKabupaten(kecamatan);
        return "redirect:list-kecamatan";
    }

    @GetMapping(path = "/listkecamatanjson")
    public ResponseEntity<List<Kecamatan>> getAllKecamatan(){
        return ResponseEntity.ok().body(configure.getNama());
    }

    @GetMapping(path = "/api/listkecamatanjson")
    public ResponseEntity<List<Kecamatan>> getAllKec(){
        return ResponseEntity.ok().body(configure.getNama());
    }

    @PostMapping(path = "/api/kecamatan/save")
    public ResponseEntity<Map<String, Object>> saveKecamatanJson(@RequestBody Kecamatan kecamatan){
        Map<String,Object> status = new HashMap<>();
        configure.insertOrUpdateKabupaten(kecamatan);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @GetMapping(path = "/api/kecamatandetails/{id}")
    public ResponseEntity<Kecamatan> getKecamatanDetails(@PathVariable("id") Integer id){
        Optional<Kecamatan> prov = configure.getKecamatanById(id);
        if(prov.isPresent()){
            return ResponseEntity.ok().body(prov.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/api/listkabupaten/{id}")
    public ResponseEntity<List<Kabupaten>> getKecamatan(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(conf.getNama(id));
    }

    @PostMapping(path = "/api/listprvinsidatatable")
    public ResponseEntity<DataTableResponse<Provinsi>> getDataProvinsi(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(service.datatables(dataTableRequest));
    }

    @PostMapping(path = "/api/listkabupatendatatable")
    public ResponseEntity<DataTableResponse<Kabupaten>> getDataKabupaten(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(_service.datatables(dataTableRequest));
    }

    @PostMapping(path = "/api/listkecamatandatatable")
    public ResponseEntity<DataTableResponse<Kecamatan>> getDataKecamatan(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(services.datatables(dataTableRequest));
    }

}
