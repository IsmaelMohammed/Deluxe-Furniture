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
	 $Fanme=$_REQUEST['Pname'];
	  $Lname=$_REQUEST['quan'];
	   $Phone=$_REQUEST['loca'];
	    $upric=$_REQUEST['upric']."Birr";
		$row=mysql_query("select* from supplier_product",$dbLink);
		$x=mysql_num_rows($row)+1;
		$supplier_id=$_REQUEST['uid'];
		
        $sql_query = "insert into supplier_product values('$supplier_id','$x','path','$Fanme','$Phone','$upric','$Lname')";
       $result="";
	if($r=mysql_query($sql_query,$dbLink))
	{
		$result="true";
	} else{
		$result="false";
	}
	echo ($result);
mysql_close($dbLink);

?>