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
	 $panme=$_REQUEST['Pname'];
	  $quan=$_REQUEST['quan'];
$ordid=$_REQUEST['orderId'];

	   $location=$_REQUEST['loca'];
	    $upric=$_REQUEST['upric']."Birr";
		$row=mysql_query("select* from product",$dbLink);
		$x=$_REQUEST['prid'];;
		$desc=$_REQUEST['descri'];
		
        $sql_query = "insert into product values('$panme','$x','$upric','$location','$quan','$desc')";
       $result="";
	if($r=mysql_query($sql_query,$dbLink))
	{
$upd_quant="select Quantity from new_pro_order where Order_Id='$ordid'";
$upd_query=mysql_query($upd_quant,$dbLink);
$fetch_quan=mysql_fetch_assoc($upd_query);
$old_quant=$fetch_quan['Quantity'];
$new_quant=$old_quant-$quan;
$update=mysql_query("update new_pro_order set Quantity='$new_quant' where Order_Id='$ordid'",$dbLink);
if($update){
$result="true";
}else{$result="false";}
	} else{
		$result="false";
	}
	echo ($result);
mysql_close($dbLink);

?>