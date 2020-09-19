$(document).ready(function () {
    $.getJSON("/list-provinsi", function(data){
       let cekBoxDiv = '';
       $.each(data, function (key, val){
          cekBoxDiv += "<option value="+val.kodeBps+">"+val.namaProvinsi+"</option>"
       });
       $("#dataprovinsi").html(cekBoxDiv);
    });
})

 $(document).ready (function() {
        $.getJSON("/listkabjson",function (data){
        let cekBoxDiv = '';
            $.each(data, function (key,val){
                cekBoxDiv += "<option value="+val.kodeBPS+">"+val.namaKabupaten+"</option>";
            });
        $("#datakab").html(cekBoxDiv);
        });
    })

    $(document).ready (function() {
        $.getJSON("/listkecmatanjson",function (data){
        let cekBoxDiv = '';
            $.each(data, function (key,val){
                cekBoxDiv += "<option value="+val.kodeBPS+">"+val.namaKecamatan+"</option>";
            });
        $("#datakec").html(cekBoxDiv);
        });
    })

     function getKab(id){
 $(document).ready (function() {
         $.getJSON("/listkabupaten/"+id,function (data){
         let cekBoxDiv = '';
             $.each(data, function (key,val){
                 cekBoxDiv += "<option value="+val.kodeBPS+">"+val.namaKabupaten+"</option>";
             });
         $("#datakabupaten").html(cekBoxDiv);
         });
     })
 }