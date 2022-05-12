<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Output Database</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<?php

class Solution {
    private $data;
    private $tables;
    public function __construct($mySQL, $SQL){
        $this->mySQL = $mySQL;
        $this->SQL = $SQL;
        $request = mysqli_query($mySQL, $SQL);
        if ($request)
            $this->data = mysqli_fetch_all($request);
    }

    public function initTables() {
        $this->tables[] = null;
        $j = 0;
        foreach ($this->data as $field)
        {
            foreach ($field as $element)
            {
                $this->tables[$j] = $element;
                $j++;
            }
        }
    }

    public function getTableOutput() {
        $tables = $this->tables;
        for ($i = 0; $i < count($this->tables); $i++)
        {
            echo '<div class="element"><div class="gradient-all">', $tables[$i], '</div><table align="center" width="100%">';

            $SQL = "DESCRIBE " . $tables[$i];
            $data = null;
            $request = mysqli_query($this->mySQL, $SQL);
            if ($request)
                $data = mysqli_fetch_all($request);
            echo '<tr>';
            foreach ($data as $item)
            {
                if ($item[3] == "PRI")
                {
                    echo '<th><b>', "$item[0], type: $item[1], PRIMARY",'</b></th>';
                }
                else if ($item[3] == "")
                {
                    echo '<th><b>', "$item[0], type: $item[1]",'</b></th>';
                }
                else
                {
                    echo '<th><b>', "$item[0], type: $item[1], SECONDARY",'</b></th>';
                }
            }
            echo '</tr>';


            $SQL = "SELECT * FROM $tables[$i]";
            $data = null;
            $request = mysqli_query($this->mySQL, $SQL);
            if ($request)
                $data = mysqli_fetch_all($request);

            foreach ($data as $field)
            {
                echo '<tr>';
                foreach ($field as $item)
                {
                    echo '<td>', $item,'</td>';
                }
                echo '</tr>';
            }

            echo '</table></div>';
        }
    }
}

$mySQL = mysqli_connect("localhost", "root", "123", "database");
$SQL = "SHOW TABLES";
$result = new Solution($mySQL, $SQL);
$result->initTables();
$result->getTableOutput();

?>

</body>
</html>
