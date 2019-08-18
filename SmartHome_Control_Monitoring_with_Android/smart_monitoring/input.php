<?php

$conn = mysqli_connect('localhost', 'ecbkcom_f-home', 'futurehome123456789', 'ecbkcom_f-home');

$data1 = $_POST['temp'];
$data2 = $_POST['humi'];
$data3 = $_POST['ppm'];
$data4 = $_POST['ldr'];
if(isset($data1, $data2, $data3, $data4)){
    echo "data diterima";
    $query = "UPDATE temp SET temp='$data1', humi='$data2', ppm='$data3', ldr='$data4' WHERE id = 1 ";
    $go = mysqli_query($conn,$query);
}else{
	echo "failed";
}
?>