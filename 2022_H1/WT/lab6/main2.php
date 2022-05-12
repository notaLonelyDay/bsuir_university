<?php

function setError($text){
    echo "$text";
    die();
}

if (!isset($_COOKIE["data"])){
    setError("Data not set");
}

$data = json_decode($_COOKIE["data"]);
$login = $data[0];
$password = $data[1];


echo "Login: $login<br>";
echo "Password: $password<br>";