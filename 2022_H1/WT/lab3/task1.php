<html>
<head>
    <title>additional task 2</title>
    <style>
        p {
            white-space: nowrap;
        }
    </style>
</head>
<body>
<form method="post">
    <input type="text" name="text"/>
    <input type="submit" value="convert">
</form>
</body>
</html>


<?php
$text = $_POST["text"];
if (isset($text) && !empty($text)) {
    $words = explode(" ", $text);
    $word_cnt = 0;
    foreach ($words as $word) {
        $word_cnt += 1;
        if ($word_cnt % 3 == 0) {
            $word = strtoupper($word);
        }
        $word_len = (strlen($word));
        if ($word_len >= 3) {
            for ($i = 0; $i < $word_len; $i++) {
                if ($i == 2) {
                    echo "<span style='color: blueviolet'>$word[$i]</span>";
                } else {
                    echo "$word[$i]";
                }
            }
            echo PHP_EOL;
        } else {
            echo $word, PHP_EOL;
        }
    }
} else {
    echo "[WARN] Empty text";
}
?>
