<?php

function saveCookies($login, $password)
{
    setcookie("data", json_encode([$login, $password]));
}


if ($_SERVER["REQUEST_METHOD"] === "GET") {
    include "login.html";
    die();
}

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    if (isset($_POST["go"])) {
        $login = $_POST["login"];
        $password = $_POST["password"];
        saveCookies($login, $password);
    }
}