<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require 'connection.php';
    addRating();
}

function addRating() {

    global $connect;

    $funRating = $_POST["funRating"];
    $informationRating = $_POST["informationRating"];

    $query = " Insert into ratings(funRating,informationRating) values ('$funRating','$informationRating');";

    mysqli_query($connect, $query) or die (mysql_error($connect));
    mysqli_close($connect);

}



