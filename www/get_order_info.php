<?php
$servername = "localhost";
//Define your database username here.
$username = "root";
//Define your database password here.
$password = "";
//Define your database name here.
$dbname = "DeluxeF";
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
 die("Connection failed: " . $conn->connect_error);
}
	 //This script is designed by Android-Examples.com
//Define your host here.

	$pro_id=$_REQUEST['order_id'];

// Create connection
//$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
 $typ=$_REQUEST['typ'];
 
 if($typ=="Customer"||$typ=="seller"){
	 $sql = "select * from customer_order where Order_Id='$pro_id' ";

 }else{
		 $sql = "select * from new_pro_order where Order_Id='$pro_id' ";

	 }

$result = $conn->query($sql);

if ($result->num_rows > 0) {
 
 // output data of each row
 while($row[] = $result->fetch_assoc()) {
 
 $json = json_encode($row);
 
 
 }
} else {
 echo "0 results";
}
echo $json;
$conn->close();
?>