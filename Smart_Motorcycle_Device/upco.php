<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$conn = mysqli_connect('localhost', 'ecbkcom_yakin', 'pengkorjuara', 'ecbkcom_pokokeyakin');

$response = array();

if(isset($_GET['id']) && isset($_GET['status'])){
	$id = $_GET['id'];
	$status = $_GET['status'];

	$result = mysqli_query($conn, "UPDATE relay SET status = '$status' WHERE id = '$id'");

	if($result){
		$response["success"] = 1;
		$response["message"] = "Success update relay status";

		echo json_encode($response);
	}
}

?>