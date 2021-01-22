<?php

$HostName = "localhost";

//Define your database username here.
$HostUser = "root";

//Define your database password here.
$HostPass = "";

//Define your database name here.
$DatabaseName = "DeluxeF";
$conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);
 

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 $DefaultId = 0;
 
 $ImageData = $_POST['image_path'];
 
 $ImageName = '';


$GetOldIdSQL ="SELECT * FROM image ";
 
 $Query = mysqli_query($conn,$GetOldIdSQL);
 
 $row = mysqli_fetch_assoc($Query);
 $nam=mysqli_num_rows($Query); 
 //echo $nam+1;
 $Itemid ="SELECT * FROM supplier_product ";
 
 $itemQuery = mysqli_query($conn,$Itemid);
 
 $itemrow = mysqli_fetch_assoc($itemQuery);
 $itid=mysqli_num_rows($itemQuery);
 $ImagePath = "images/$nam.png";
 
 $ServerURL = "http://192.168.43.173/$ImagePath";
 
 $InsertSQL = "insert into image  values ('id','$itid','$ServerURL')";
 
 if(mysqli_query($conn, $InsertSQL)){

 file_put_contents($ImagePath,base64_decode($ImageData));

 echo "Your Image Has Been Uploaded.";
 }
 
 mysqli_close($conn);
 }else{
 echo "Not Uploaded";
 }

?>