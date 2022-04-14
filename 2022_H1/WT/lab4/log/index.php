<?php
include 'Logger.php';

$logger = new Logger(false);
$logger->log('Hello World');
$logger->log('Hello World');
sleep(2);
$logger->log('Hello World');

$logger = new Logger(true);
$logger->log('Hello World');
$logger->log('Hello World');
sleep(2);
$logger->log('Hello World');