<?php

$conn = mysqli_connect('localhost', 'ecbkcom_yakin', 'pengkorjuara', 'ecbkcom_pokokeyakin');

$data1 = $_POST['lat_str'];
$data2 = $_POST['lng_str'];
$data3 = $_POST['alt_str'];
$data4 = $_POST['mph_str'];
$data5 = $_POST['blut'];

if(isset($data1, $data2, $data3, $data4 ,$data5)){
    echo "data diterima";
    $query = "UPDATE gps SET latitude='$data1', longitude='$data2', altitude='$data3', mph='$data4', bluetooth='$data5' WHERE id = 1 ";
    $go = mysqli_query($conn,$query);
}else{
	echo "failed";
}
?>