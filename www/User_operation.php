
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$userid=$_REQUEST['user_id'];
	$status=$_REQUEST['status'];
	
    $re=1;
	$result="";
	$typ="";
	$flag['code']=0;
	$r=mysql_query("update user_acount set account_status='$status' where id='$userid'",$dbLink);
	

	
	
	if($r){
		$result="true";
		
	}else{
		$result="false";
	}
	
	

	echo($result);
	
	
	mysql_close($dbLink);
?>