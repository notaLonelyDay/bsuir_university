

var baseUrl = ""
var last = "en"
var textFile
var outFile
var saveText
var saveCrypted

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

function saveFile(data){
        // Convert the text to BLOB.
        const textToBLOB = new Blob([data], { type: 'text/plain' });
        const sFileName = 'formData.txt';	   // The file to save the data.

        let newLink = document.createElement("a");
        newLink.download = sFileName;

        if (window.webkitURL != null) {
            newLink.href = window.webkitURL.createObjectURL(textToBLOB);
        }
        else {
            newLink.href = window.URL.createObjectURL(textToBLOB);
            newLink.style.display = "none";
            document.body.appendChild(newLink);
        }
        newLink.click();
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
    saveCrypted = document.getElementById("save_crypted");
    saveText = document.getElementById("save_text");
    textFile.addEventListener('input',() => {textFileChanged();});
    outFile.addEventListener('input',() => {outFileChanged();});
    outFile.addEventListener('input',() => {outFileChanged();});

    saveCrypted.addEventListener('click',() => {
        data = out.value;
        saveFile(data);
    });
    saveText.addEventListener('click',() => {
        data = text.value;
        saveFile(data);
    });
    //algo.change(function() {
    //  alert( "Handler for .change() called." );
    //});
    algo.addEventListener('change',() => {algoChanged();});
    text.addEventListener('input',() => {textChanged();});
    key.addEventListener('input',() => {keyChanged();});
    out.addEventListener('input',() => {outChanged();});
});

