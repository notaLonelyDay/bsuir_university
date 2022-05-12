<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Output Database</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

</body>
</html>

<?php

class GetDB
{
    private string $serverName = "localhost";
    private string $userName = "root";
    private string $password = "123";
    private string $databaseName = "Books";

    public $connect;

    public function Connect()
    {
        $this->connect = new mysqli($this->serverName, $this->userName, $this->password, $this->databaseName);
        if ($this->connect->connect_error) {
            die("Connection failed: " . $this->connect->connect_error);
        }
        $this->connect->set_charset("utf8");
    }

    public function OutputDB()
    {
        if (empty($this->connect)) {
            die("Not connected to database");
        }

        $sqlBooks = "SELECT * FROM BookInfo";
        if ($res = $this->connect->query($sqlBooks)) {
            foreach ($res as $row) {
                echo "<table class='table_dark'>";
                echo "<tr> 
                      <th>Title</th> 
                      <th>Author</th> 
                  </tr>";
                $bookId = $row["Id"];
                $title = $row["Title"];
                $author = $row["Author"];

                echo "<tr>";
                echo "<td>$title</td> <td>$author</td>";
                echo "</tr>";
                echo "</<table>";

                $sqlReviews = "SELECT * FROM BookReviews WHERE BookId = $bookId";
                $reviews = $this->connect->query($sqlReviews)->fetch_all();
//                var_dump($reviews);
                if(count($reviews) > 0) {
                    echo "<table class='table_dark'>";
                    echo "<tr> 
                      <th>Author</th> 
                      <th>Rating</th> 
                  </tr>";

                    foreach ($reviews as $review) {
                        $author = $review[3];
                        $rating = $review[2];
                        echo "<tr>";
                        echo "<td>$author</td> <td>$rating</td>";
                        echo "</tr>";
                    }
                    echo "</<table>";
                }
            }
        }
        $this->connect->close();
    }
}

$myDB = new GetDB();
$myDB->Connect();
$myDB->OutputDB();

?>
