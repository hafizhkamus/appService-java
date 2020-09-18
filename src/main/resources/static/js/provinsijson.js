$(document).ready(function () {
    $.getJSON("/list-provinsi", function(data) {
    let cekBoxDiv = '';
    $.each(data, function (key, val) {
            cekBoxDiv += "<option class='form-control' value="+val.kodeBps+">"+val.namaProvinsi+"<option>";
        });
            $("#dataprovinsi").html(cekBoxDiv);
        });
    })

   fuction getKab(id){
      $.getJSON("/listkabupaten/"+id , function(data) {
        let cekBoxDiv = '';
        $.each(data, function (key, val) {
                cekBoxDiv += "<option class='form-control' value="+val.kodeBps+">"+val.Kabupaten+"<option>";
            });
                $("#datakabupaten").html(cekBoxDiv);
            });
        })
   }