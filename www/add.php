<?php
define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_query("SET character_set_results=utf8", $dbLink)or die(mysql_error());
    mb_language('uni'); 
    mb_internal_encoding('UTF-8');
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
    mysql_query("set names 'utf8'",$dbLink)or die(mysql_error());

	 mysql_query("SET character_set_results=utf8", $dbLink);
	 $Fanme=$_REQUEST['Rname'];
	  $Lname=$_REQUEST['lname'];
	   $Phone=$_REQUEST['phone'];
		
	    $username=$_REQUEST['user'];
		$password=$_REQUEST['psw'];
		$usr=$_REQUEST['usr'];
$loca=$_REQUEST['loca'];
		$r=mysql_query("select * from user_acount",$dbLink);
		$x=mysql_num_rows($r)+1;
		
		$x=$x+1;
		
		$Id="Del".$x;
        $sql_query = "insert into user_acount values('$Fanme','$Lname','$Phone','$username','$password','$usr','$loca','$Id','1')";
       $result="";
	if($r=mysql_query($sql_query,$dbLink))
	{
		$result="true";
	} else{
		$result=mysql_error();
	}
	echo ($result);
mysql_close($dbLink);

?>