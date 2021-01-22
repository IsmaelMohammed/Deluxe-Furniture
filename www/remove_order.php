
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$userid=$_REQUEST['user_id'];
	$supplierid=$_REQUEST['supplier_id'];
	$itemid=$_REQUEST['item_id'];
    $re=1;
	$result="";
	$typ="";
	$flag['code']=0;
	$r=mysql_query("Delete from new_pro_order where Supplier_ID='$supplierid' and User_ID='$userid' and Product_ID='$itemid' ",$dbLink);
	

	
	
	if($r){
		$result="true";
		
	}else{
		$result="false";
	}
	
	

	echo($result);
	echo($typ);
	
	mysql_close($dbLink);
?>