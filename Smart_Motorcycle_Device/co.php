<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//Creating Array for JSON response
$response = array();

$conn = mysqli_connect('localhost', 'ecbkcom_yakin', 'pengkorjuara', 'ecbkcom_pokokeyakin');
 
// Check if we got the field from the user
if (isset($_GET["id"])) {
    $id = $_GET['id'];
 
     // Fire SQL query to get weather data by id
    $result = mysqli_query($conn, "SELECT *FROM relay WHERE id = '$id'");
    
    //If returned result is not empty
    if (!empty($result)) {

        // Check for succesfull execution of query and no results found
        if (mysqli_num_rows($result) > 0) {
            
            // Storing the returned array in response
            $result = mysqli_fetch_array($result);
            
            // temperoary user array
            //$led = array();
            $relay["id"] = $result["id"];
            $relay["status"] = $result["status"];
          
            //$response["success"] = 1;

            //$response["led"] = array();
            
            // Push all the items 
            //array_push($response["led"], $led);

            echo json_encode($relay);
            // Show JSON response
            //echo json_encode($response);
        } else {
            // If no data is found
            $response["success"] = 0;
            $response["message"] = "No data on relay found";
 
            // Show JSON response
            echo json_encode($response);
        }
    } else {
        // If no data is found
        $response["success"] = 0;
        $response["message"] = "No data on relay found";
 
        // Show JSON response
        echo json_encode($response);
    }
} else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // echoing JSON response
    echo json_encode($response);
}
?>