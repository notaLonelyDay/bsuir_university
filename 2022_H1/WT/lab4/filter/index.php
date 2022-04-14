<?php

function echoTableLine($arr, $type)
{
    echo "<tr>";
    foreach ($arr as $val) {
        echo "<" . $type . ">$val</" . $type . ">";
    }
    echo "</tr>";
}


if ($_SERVER['REQUEST_METHOD'] === 'GET') {

    $f = fopen("messages.csv", 'r');
    $params = fgets($f);
    $params = explode(",", $params);

    echo "<table>";
    echoTableLine($params, "th");


    while (true) {
        $str = fgets($f);
        if ($str == null)
            break;
        $arrayString = explode(",", $str);
        echoTableLine($arrayString, "td");
    }
    echo "</table>";
}

function preprocessMessage($message){

    return "";
}

function addRecord()
{
    $name = htmlspecialchars($_POST['fname']);
    $message = htmlspecialchars($_POST['fmessage']);

    if (empty($name)) {
        echo "Name is empty";
        die();
    }

    file_put_contents("messages.csv", strval($name) . "," . preprocessMessage($message) . PHP_EOL, FILE_APPEND);

    header('Location: /lab4/filter/index.php', true, 301);
}


if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    addRecord();
}

include 'form.html';
