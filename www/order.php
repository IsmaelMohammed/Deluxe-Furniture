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
	  
	  $quantity=$_REQUEST['quantity'];
	$seller_id=$_REQUEST['seller_id'];

	  $operation=$_REQUEST['operation'];
	   $result="";
	  if($operation=="order"){
		$row=mysql_query("select* from customer_order",$dbLink);
		$x=mysql_num_rows($row)+1;
		$dat=date('d-m-y');
		$ordid='order'.$x;
        $sql_query = "insert into customer_order values('$ordid','$itemid','$usrid','$seller_id','$quantity','$dat','0')";
      
	if($r=mysql_query($sql_query,$dbLink))
	{
		$result="true";
	} else{
		$result="false";
	}
	  }else if ($operation=="cart"){
		  $row=mysql_query("select* from cart",$dbLink);
		$x=mysql_num_rows($row)+1;

		$cartid='Cart'.$x;


		$get_pro_info=mysql_query("select * from product where Product_ID='$itemid'",$dbLink);
			
			while($data=mysql_fetch_array($get_pro_info)){
		$proname=$data['Product_Name'];
		$unitpric=$data['Unit_Price'];


$sql_query="insert into cart values('$proname','$itemid','$unitpric','$usrid','$cartid')";
if($r=mysql_query($sql_query,$dbLink))
	{
		$result="true";
	} else{
		$result="false";
	}

				}	  
	  }
	echo ($result);
mysql_close($dbLink);

?>