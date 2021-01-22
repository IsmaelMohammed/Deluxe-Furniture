
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	;
	$pro_id=$_REQUEST['pro_id'];
	$result=0;
    $re=1;
	$id="";

	$r=mysql_query("delete from  cart where Cart_ID='$pro_id'   ",$dbLink);
	

	
	if($r){
	
		$result=1;
	}else{
		$result=0;
		
	}
	
	echo($result);
	//echo($id);
	mysql_close($dbLink);
?>
