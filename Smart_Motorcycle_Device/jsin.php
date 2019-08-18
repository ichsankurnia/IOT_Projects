<?php
	$conn = mysqli_connect('localhost', 'ecbkcom_yakin', 'pengkorjuara', 'ecbkcom_pokokeyakin');
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "SELECT * FROM gps";
	$result = mysqli_query($conn, $sql);

	$data = array();

	while ($row = mysqli_fetch_array($result))
	{
		array_push($data, array(
			"id" 	=>	$row[0],
			"latitude"	=>	$row[1],
			"longitude"	=>	$row[2],
			"altitude"	=>	$row[3],
			"mph"	=>	$row[4],
			"bluetooth"	=>	$row[5]
		));
	}

echo json_encode(array("gps"=>$data));

?>