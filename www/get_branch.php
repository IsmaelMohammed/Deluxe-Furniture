
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$username=$_REQUEST['username'];
	$password=$_REQUEST['password'];
    $re=1;
	$id="";
	$r=mysql_query("select * from user_acount where u_name='$username' and password='$password' ",$dbLink);
	while($row=mysql_fetch_array($r))
	{
		
		
		$id=$row['Branch'];
	}

	echo($id);
	mysql_close($dbLink);
?>