<?php

class Logger
{
    private $filename = '';
    private $toFile = false;

    public function __construct($toFile = false, $filename = 'log.txt')
    {
        $this->filename = $filename;
        $this->toFile = $toFile;
    }

    public function log($message)
    {
        $formattedMessage = $this->addTimeToMessage($message);
        if ($this->toFile) {
            $this->logToFile($formattedMessage);
        } else {
            echo '<div>' . $formattedMessage . '</div>';
        }
    }

    public function logToFile($message)
    {
        $logFile = fopen($this->filename, 'a');
        fwrite($logFile, $message);
        fclose($logFile);
    }

    public function addTimeToMessage($message){
        return self::getCurrentDateTime() . $message . PHP_EOL;
    }

    public function getCurrentDateTime()
    {
        return date('[Y-m-d H:i:s] ');
    }

}