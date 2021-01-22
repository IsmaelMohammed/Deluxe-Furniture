
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$user_id=$_REQUEST['user_id'];
	$result=0;
    $re=1;
	$id="";
	$r=mysql_query("select * from new_pro_order where Order_Status='1' and User_ID='$user_id' ",$dbLink);
	$num_row=mysql_num_rows($r);
	
	if($num_row>=1){
		while($row=mysql_fetch_array($r))
	{
		
		
		$id=$row['Supplier_ID'];
	}
		$result=1;
	}else{
		$result=0;
		
	}
	
	echo($result);
	//echo($id);
	mysql_close($dbLink);
?>