
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
	$r=mysql_query("select * from image where Item_id='$username'  ",$dbLink);
	while($row=mysql_fetch_array($r))
	{
		
		
		$id=$row['URL'];
	}

	echo($id);
	mysql_close($dbLink);
?>