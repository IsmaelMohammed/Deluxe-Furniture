
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
	$result="";
	$typ="";
	$sta="";
	$flag['code']=0;
	$r=mysql_query("select * from user_acount where u_name='$username' and Password='$password' ",$dbLink);
	while($row=mysql_fetch_array($r))
	{
		
	$typ=$row['user_type'];
	$sta=$row['account_status'];
	}

	
	$x = mysql_num_rows($r);
	if($x>=1){
		$result="true";
		
	}else{
		$result="false";
	}
	
	

	echo($result);
	echo($typ);
	echo($sta);
	
	mysql_close($dbLink);
?>