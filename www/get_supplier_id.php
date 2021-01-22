
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$itemid=$_REQUEST['itemid'];
	
    $re=1;
	$id="";
	$r=mysql_query("select Supplier_ID from supplier_product where Product_ID='$itemid' ",$dbLink);
	while($row=mysql_fetch_array($r))
	{
		
		
		$id=$row['Supplier_ID'];
	}

	echo($id);
	mysql_close($dbLink);
?>