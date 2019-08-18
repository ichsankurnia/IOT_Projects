<?php
	$conn = mysqli_connect('localhost', 'ecbkcom_f-home', 'futurehome123456789', 'ecbkcom_f-home');
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "SELECT * FROM temp";
	$result = mysqli_query($conn, $sql);

	$data = array();

	while ($row = mysqli_fetch_array($result))
	{
		array_push($data, array(
			"id" 	=>	$row[0],
			"temp"	=>	$row[1],
			"humi"	=>	$row[2],
			"ppm"	=>	$row[3],
			"ldr"	=>	$row[4]
		));
	}

echo json_encode(array("sensor"=>$data));

?>