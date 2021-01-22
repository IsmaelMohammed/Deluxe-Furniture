
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	;
	$order_id=$_REQUEST['pro_id'];
$typ=$_REQUEST['typ'];
	$result=0;
    $re=1;
	$id="";
if($typ=="seller"){
	$r=mysql_query("delete from  customer_order where Order_Id ='$order_id'  ",$dbLink);
	}
else {
	$r=mysql_query("delete from  new_pro_order where Order_Id='$order_id'  ",$dbLink);
	} 
	
	if($r){
	
		$result=1;
	}else{
		$result=0;
		
	}
	
	echo($result);
	//echo($id);
	mysql_close($dbLink);
?>
