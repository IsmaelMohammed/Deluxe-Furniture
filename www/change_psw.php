
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	$user_id=$_REQUEST['userid'];
	$newpsw=$_REQUEST['psw'];
    $re=1;
	$result="";
	$typ="";
	$sta="";
	$flag['code']=0;
	$r=mysql_query("update user_acount set password='$newpsw' where id='$user_id'",$dbLink);
	
	if($r>=1){
		$result="true";
		
	}else{
		$result="false";
	}
	
	

	echo($result);
	
	
	mysql_close($dbLink);
?>