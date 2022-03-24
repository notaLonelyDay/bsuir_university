<?php

function isNameExist($name)
{
    $file = fopen("companies.csv", "r");
    while (($currentLine = fgets($file)) != false) {
        $params = explode(",", $currentLine);
        if ($name === $params[0])
            return true;
    }
    return false;
}

function echoTableLine($arr, $type)
{
    echo "<tr>";
    foreach ($arr as $val) {
        echo "<" . $type . ">$val</" . $type . ">";
    }
    echo "</tr>";
}


if ($_SERVER['REQUEST_METHOD'] === 'GET') {

    $f = fopen("companies.csv", 'r');
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

function addRecord()
{
    $name = htmlspecialchars($_POST['fname']);
    $adress = htmlspecialchars($_POST['fadress']);
    $phone = htmlspecialchars($_POST['fphone']);
    $email = htmlspecialchars($_POST['femail']);

    if (empty($name)) {
        echo "Name is empty";
        die();
    }
    if (isNameExist($name)) {
        echo "this name already exists";
        die();
    }

    file_put_contents("companies.csv", strval($name) . "," . $adress . "," . $phone . "," . $email . "\n", FILE_APPEND);

    header('Location: /lab3/metod_1.php', true, 301);
}

function searchRecord($name)
{
    $file = fopen("companies.csv", "r");
    $found = false;
    $params = explode(",", fgets($file));
    echoTableLine($params, "tr");
    while (($currentLine = fgets($file)) != false) {
        $params = explode(",", $currentLine);
        if (strstr($params[0], $name)) {
            $found = true;
            echoTableLine($params, "td");
        }
    }
    if (!$found)
        echo "<div>Nothing found!</div>";
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $search = htmlspecialchars($_POST['fsearch']);
    if ($search) {
        echo "<table>";
        searchRecord($search);
        echo "</table>";
    } else {
        addRecord();
    }


}

include 'form.html';
