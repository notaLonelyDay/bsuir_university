

var baseUrl = ""
var last = "en"
var textFile
var outFile

function algoChanged(){

}

function encrypt(){
    last = "en";
    textText = text.value;
    keyText = key.value;
    algoText = algo.value
    url = "api/encrypt";

    var jqxhr = $.post(url,  { "text": textText, "key": keyText, "cypher": algoText} )
      .done(function(data) {
          out.value = data;
  error.value = "";
      })
      .fail(function(jqXHR, textStatus, errorThrown) {
        error.value = "[" + jqXHR.status + "] " + jqXHR.responseText

          out.value = "";
      })
}

function decrypt(){
    last = "de";
    outText = out.value;
    keyText = key.value;
    algoText = algo.value
    url = "api/decrypt";

    var jqxhr = $.post(url,  { "text": outText, "key": keyText, "cypher": algoText} )
      .done(function(data) {
          text.value = data;
  error.value = "";
      })
      .fail(function(jqXHR, textStatus, errorThrown) {
        error.value = "[" + jqXHR.status + "] " + jqXHR.responseText
          text.value = "";
      })
}

function textChanged(){
   encrypt()
}

function keyChanged(){
    if(last == "en")
       encrypt();
    else
        decrypt();
}

function outChanged(){
    decrypt()
}

function loadFromFile(file, input){
    var file = file.files[0];
    if (file) {
        var reader = new FileReader();
        reader.readAsText(file, "UTF-8");
        reader.onload = function (evt) {
            input.value = evt.target.result;
        }
        reader.onerror = function (evt) {
            input.value = "error reading file";
        }
    }
}
function textFileChanged(){
    loadFromFile(textFile, text);
    textChanged();
}
function outFileChanged(){
    loadFromFile(outFile, out);
    outChanged();
}

$(document).ready(function() {
    var algo = document.getElementById("algo");
    var text = document.getElementById("text");
    var key = document.getElementById("key");
    var out = document.getElementById("out");
    var error = document.getElementById("error");
    textFile = document.getElementById("text_file");
    outFile = document.getElementById("out_file");
    textFile.addEventListener('input',() => {textFileChanged();});
    outFile.addEventListener('input',() => {outFileChanged();});

    //algo.change(function() {
    //  alert( "Handler for .change() called." );
    //});
    algo.addEventListener('change',() => {algoChanged();});
    text.addEventListener('input',() => {textChanged();});
    key.addEventListener('input',() => {keyChanged();});
    out.addEventListener('input',() => {outChanged();});
});

