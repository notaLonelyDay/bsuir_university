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

function preprocessMessage($message)
{

    $matches = [];
    preg_match_all('/https?:\/\/(www\.)?([-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6})\b([-a-zA-Z0-9()@:%_\+.~#?&\/\/=]*)/', $message, $matches);
//    var_dump($message);
//    var_dump($matches[0]);
    $toReplace = array_filter($matches[0], function ($k) {
        return !preg_match('/https?:\/\/(www\.)?(bsuir\.by)\b([-a-zA-Z0-9()@:%_\+.~#?&\/=]*)/', $k);
    });
//    var_dump($toReplace);
    $ans = $message;
    foreach ($toReplace as $value) {
        $ans = str_replace($value, "#THIS_URL_IS_NOT_ALLOWED", $ans);
    }
    return $ans;
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
