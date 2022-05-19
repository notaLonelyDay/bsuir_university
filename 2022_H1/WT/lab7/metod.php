<?php
require 'phpmailer/PHPMailer.php';
require 'phpmailer/SMTP.php';


function clean_text($string)
{

    $string = trim($string);
    $string = stripslashes($string);

    $string = htmlspecialchars($string);
    return $string;
}

$warning = '';
$error = '';
$name = '';
$email = '';
$phone = '';
$topic = '';
$message = '';


if (isset($_POST['submit'])) {

    if (empty($_POST["email"])) {
        $error .= '<p><label class="text-danger">Please enter the email</label></p>';
    }
    if (empty($_POST["topic"])) {
        $error .= '<p><label class= "text-danger" >Please enter the topic</label></p>';
    }
    if (empty($_POST["message"])) {
        $error .= '<p><label class= "text-danger" >Please enter the message</label></p>';
    }

    $email = $_POST["email"];
    $emails = explode(',', $_POST['email']);
    $topic = $_POST['topic'];
    $message = $_POST['message'];
    if ($error == '') {
        $email2 = new PHPMailer\PHPMailer\PHPMailer();
        $email2->IsSMTP();
        $email2->SMTPAuth = true;
        $email2->SMTPSecure = 'ssl';
        $email2->Host = "smtp.gmail.com";
        $email2->Port = 465;
        $email2->Username = "testksis4@gmail.com";
        $email2->Password = "Kollega777";
        $email2->SetFrom("testksis4@gmail.com");
        foreach ($emails as $email_address) {
            $email_cleaned = clean_text($email_address);
            if (!filter_var($email_cleaned, FILTER_VALIDATE_EMAIL)) {
                $error .= "<p><label class=\"text-danger\">Invalid email format  $email_cleaned</label></p>";
            } else {
                $email2->AddAddress($email_cleaned);
            }
            file_put_contents("sent.txt", $email_cleaned . PHP_EOL, FILE_APPEND);
        }
        $email2->Subject = $topic;
        $email2->Body = $message;

        if (($email2->getAllRecipientAddresses() == 0) ||  !$email2->Send()) {
            echo "Error " . $email2->ErrorInfo;
        } else {
            echo "Emails has been sent!";
        }
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <style>
        label {
            display: block;
            width: 300px;
            text-align: center;
        }

        .error_text {
            color: red;
        }

    </style>
</head>

<body>
<form action="metod.php" method="post">
    <div class="error_text">
        <?php echo $error;
        echo $warning ?>
    </div>
    <br>
    <div>
        <label> Send to (emails separated with ,): </label>
        <input type="text" name="email" size="100" value="<?php echo $email; ?>"><br>
    </div>
    <div>
        <label> Topic: </label>
        <input type="text" size="100" name="topic" value="<?php echo $topic; ?>"><br>
    </div>
    <div>
        <label> Message: </label>
        <input type="text" size="100" name="message" value="<?php echo $message; ?>"><br>
    </div>
    <div>
        <input type="submit" name="submit" value="submit">
    </div>
</form>
</body>
</html>