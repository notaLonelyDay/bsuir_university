
var plain_izg = document.getElementById("plain_izg");
var height_izg = document.getElementById("height_izg");
var out_izg = document.getElementById("out_izg");

var plain_vis = document.getElementById("plain_vis");
var key_vis = document.getElementById("key_vis");
var out_vis = document.getElementById("out_vis");

var plain_pleh = document.getElementById("plain_pleh");
var out_pleh = document.getElementById("out_pleh");


function encodeIzg(){
    planText = plain_izg.value;
    height = height_izg.value;
    url = "http://localhost:8080/api/v1/izgorod/encode?plain=" + planText + "&height=" + height;

    console.log(url);

    $.get({
        type: 'GET',
        url: url,
        contentType: "text/html",
        headers: {'Access-Control-Allow-Origin':'*'},
        crossDomain:true, 
            success: function(result){
                    out_izg.value = result;
            },
            error: function(data){
                out_izg.value = "Error";
            }
    });
}

function decodeIzg(){
    outText = out_izg.value;
    height = height_izg.value;

    url = "http://localhost:8080/api/v1/izgorod/decode?cypher=" + outText + "&height=" + height;

    $.get({
        type: 'GET',
        url: url,
        contentType: "text/html",
        headers: {'Access-Control-Allow-Origin':'*'},
        crossDomain:true, 
            success: function(result){
                    plain_izg.value = result;
            },
            error: function(data){
                plain_izg.value = "Error";
            }
    });

}

function encodeVis(){
    planText = plain_vis.value;
    key = key_vis.value;

    url = "http://localhost:8080/api/v1/vigener/encode?plain=" + planText + "&key=" + key;

    console.log(url);

    $.get({
        type: 'GET',
        url: url,
        contentType: "text/html",
        headers: {'Access-Control-Allow-Origin':'*'},
        crossDomain:true, 
            success: function(result){
                    out_vis.value = result;
            },
            error: function(data){
                out_vis.value = "Error";
            }
    });

}

function decodeVis(){

    outText = out_vis.value;
    key = key_vis.value;

    url = "http://localhost:8080/api/v1/vigener/decode?cypher=" + outText + "&key=" + key;

    $.get({
        type: 'GET',
        url: url,
        contentType: "text/html",
        headers: {'Access-Control-Allow-Origin':'*'},
        crossDomain:true, 
            success: function(result){
                    plain_vis.value = result;
            },
            error: function(data){
                plain_vis.value = "Error";
            }
    });

}

function encodePleh(){

    planText = plain_pleh.value;

    url = "http://localhost:8080/api/v1/plepher/encode?plain=" + planText;

    $.get({
        type: 'GET',
        url: url,
        contentType: "text/html",
        headers: {'Access-Control-Allow-Origin':'*'},
        crossDomain:true, 
            success: function(result){
                    out_pleh.value = result;
            },
            error: function(data){
                out_pleh.value = "Error";
            }
    });

}

function decodePleh(){
    outText = out_pleh.value;
    url = "http://localhost:8080/api/v1/plepher/decode?cypher=" + outText;

    $.get({
        type: 'GET',
        url: url,
        contentType: "text/html",
        headers: {'Access-Control-Allow-Origin':'*'},
        crossDomain:true, 
            success: function(result){
                    plain_pleh.value = result;
            },
            error: function(data){
                plain_pleh.value = "Error";
            }
    });

}


plain_izg.addEventListener('input',() => {encodeIzg();});
plain_vis.addEventListener('input',()=>{encodeVis();});
plain_pleh.addEventListener('input',()=>{encodePleh();});

out_izg.addEventListener('input',()=>{decodeIzg();});
out_vis.addEventListener('input',()=>{decodeVis();});
out_pleh.addEventListener('input',()=>{decodePleh();});