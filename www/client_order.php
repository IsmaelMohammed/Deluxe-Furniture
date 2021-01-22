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
	 $itemid=$_REQUEST['itemid'];
	  $usrid=$_REQUEST['userid'];
	  $quantity="";
		$row=mysql_query("select* from customer_order",$dbLink);
		$x=mysql_num_rows($row)+1;
		$dat=date('d-m-y');
		$ordid='order'.$x;
        $sql_query = "insert into customer_order values('$ordid','$itemid','$usrid','$quantity','$dat','0')";
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