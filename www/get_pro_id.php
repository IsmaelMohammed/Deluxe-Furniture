
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$username=$_REQUEST['pid'];
    $re=1;
	$id="";
	$r=mysql_query("select * from cart where Cart_ID='$username'  ",$dbLink);
	while($row=mysql_fetch_array($r))
	{
		
		
		$id=$row['Product_ID'];
	}

	echo($id);
	mysql_close($dbLink);
?>