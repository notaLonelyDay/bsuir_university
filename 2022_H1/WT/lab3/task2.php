<?php
// methoda var 2
/*
 * Вариант 2: Работа с файлом с информацией о компаниях.
Создать файл companies.csv (разделитель полей запятая).
На странице создать форму для добавления в базу информации о компаниях: name, address, phone, email.
В случае отсутствия (name) или существовании такой компании в базе выдать предупреждение.
Вывести информацию из файла в отдельном блоке. Сделать поиск по названию.
 */
function clean_text($string)
{

    $string = trim($string);
    $string = stripslashes($string);

    $string = htmlspecialchars($string);
    return $string;
}

$error = '';
$name = '';
$email = '';
$phone = '';
$address = '';
$findName ='';

if(isset($_POST['submit'])) {
    //echo "yeah isset post submit<br>";
    if(empty($_POST["name"]))
    {
        $error .= '<p><label class="text-danger">Please enter the name</label></p>';
    }
    else
    {
        $name = clean_text($_POST["name"]);
        if(!preg_match("/^[a-zA-Z ]*$/",$name))
        {
            $error .= '<p><label class="text-danger">Only letters and white space allowed</label></p>';
        }
    }
    if(empty($_POST["email"]))
    {
        $error .= '<p><label class="text-danger">Please enter the company email</label></p>';
    }
    else
    {
        $email = clean_text($_POST["email"]);
        if(!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $error .= '<p><label class="text-danger">Invalid email format</label></p>';
        }
    }
    if(empty($_POST["phone"])) {
        $error .= '<p><label class= "text-danger" >Please enter the company phone</label></p>';
    }
    else
    {
        $phone = clean_text($_POST["phone"]);
    }
    if(empty($_POST["address"])) {
        $error .= '<p><label class= "text-danger" >Please enter the company address</label></p>';
    }
    else
    {
        $address = clean_text($_POST["address"]);
    }
    if( strpos(file_get_contents("companies.csv"),$name) !== false) {
        $error .= '<p><label class= "text-danger" >There is already a company with this name in the data base</label></p>';
    }
    //echo 'bebra';
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $address = $_POST['address'];
    $filename = "companies.csv";
    if ($error == '') {

        $f = fopen($filename, "a");

        if ($f === false) {
            echo '<p><label class= "text-danger" >Can\'t open the file</label></p>';
            //die('Error opening the file ' . $filename);
        }
        $numRows = count(file($filename));

        $form_data = array(
            'sr_no' => $numRows,
            'name' => $name,
            'email' => $email,
            'phone' => $phone,
            'address' => $address
        );


        fputcsv($f, $form_data);
        /*
        foreach ($_POST as $row) {
            fputcsv($f, $row);
        }
        */
        $error = '';
        $name = '';
        $email = '';
        $phone = '';
        $address = '';
    }
}
?>
<!DOCTYPE html>

<html>
<head>
    <style>
        label {
            display: block;
            width: 150px;
            text-align: center;
        }
    </style>
</head>

<body>
<!-- Вариант 2: Работа с файлом с информацией о компаниях.
Создать файл companies.csv (разделитель полей запятая).
На странице создать форму для добавления в базу информации о компаниях: name, address, phone, email.
В случае отсутствия (name) или существовании такой компании в базе выдать предупреждение.
Вывести информацию из файла в отдельном блоке. Сделать поиск по названию.
-->

<form action="task2.php" method="post">
    <?php echo $error; ?>
    <br>
    <div>
        <label>  Name: </label>

        <input type="text" name="name" value="<?php echo $name; ?>">
    </div>
    <div>
        <label> Address: </label>
        <input type="text" name="address" value="<?php echo $address; ?>"><br>
    </div>
    <div>
        <label> Phone: </label>
        <input type="number" name="phone" value="<?php echo $phone ?>"><br>
    </div>
    <div>
        <label> Email: </label>
        <input type="email" name="email" value="<?php echo $email; ?>"><br>
    </div>

    <div>
        <input type="submit" name="submit" value="submit">
    </div>
    <div>
        <input type="text" name="findName" value="<?php echo $findName; ?>" >
        <input type="submit" name="find" value="find">

        <?php

        //echo var_dump($_POST);
        //echo "before post <br>";
        if(isset($_POST['findName']) && ($_POST['findName'] !== '')) {
            //echo '<br>search<br>';
            $found = false;
            echo "<table border='1'>\n\n";
            $f = fopen("companies.csv", "r");
            $findName = clean_text($_POST["findName"]);
            while (($line = fgetcsv($f)) !== false) {
                //echo "$findName <br>";
                if(in_array($findName,$line)) {
                    $found = true;
                    echo "<tr>";
                    foreach ($line as $cell) {
                        //echo "$cell<br>";

                        echo "<td>" . htmlspecialchars($cell) . "</td>";

                    }
                }
                echo "</tr>\n";
            }
            fclose($f);
            if ($found) {
                echo "\n</table>";
            } else {
                echo '<p><label class= "text-danger" >Company not found </label></p>';
            }
            $findName ='';

        }
        ?>
    </div>

    <div>
        <label> <br> Data table </label>
        <?php

        echo "\n\n <table border='1'>\n\n";
        $f = fopen("companies.csv", "r");
        while (($line = fgetcsv($f)) !== false) {
            echo "<tr>";
            foreach ($line as $cell) {
                echo "<td>" . htmlspecialchars($cell) . "</td>";
            }
            echo "</tr>\n";
        }
        fclose($f);
        echo "\n</table></body></html>";
        ?>

    </div>


</form>

</body>
</html>