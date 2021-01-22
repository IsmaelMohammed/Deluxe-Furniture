
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
	$userid=$_REQUEST['user_id'];
	$supplierid=$_REQUEST['supplier_id'];
	$itemid=$_REQUEST['item_id'];
    $re=1;
	$result="";
	$typ="";
	$flag['code']=0;
	$r="select * from  supplier_product where Product_ID='$itemid' ";
	
$result = $conn->query($r);

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