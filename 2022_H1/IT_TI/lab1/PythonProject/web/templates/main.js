

var baseUrl = ""
var last = "en"

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

$(function() {
    var algo = document.getElementById("algo");
    var text = document.getElementById("text");
    var key = document.getElementById("key");
    var out = document.getElementById("out");
    var error = document.getElementById("error");
    //algo.change(function() {
    //  alert( "Handler for .change() called." );
    //});
    algo.addEventListener('change',() => {algoChanged();});
    text.addEventListener('input',() => {textChanged();});
    key.addEventListener('input',() => {keyChanged();});
    out.addEventListener('input',() => {outChanged();});
});

