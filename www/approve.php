
<?php
	define('HOSTNAME', 'localhost');
    define('USERNAME', 'root');
    define('PASSWORD', '');
    define('DATABASE', 'DeluxeF');

    $dbLink = mysql_connect(HOSTNAME, USERNAME, PASSWORD)or die(mysql_error());
    mysql_select_db(DATABASE, $dbLink)or die(mysql_error());
  
	 
	;
	$order_id=$_REQUEST['pro_id'];
$typ=$_REQUEST['typ'];
	$result=0;
    $re=1;
	$id="";

if($typ=="seller"){
	$upd=mysql_query("update customer_order set Status='1' where Order_Id='$order_id'  ",$dbLink);
	

	if($upd){
$dat=date('d-m-y');
$pro_id=$_REQUEST['pid'];
$seller_id=$_REQUEST['uid'];
$order=$_REQUEST['ordq'];
$get=mysql_query("select * from product where Product_ID='$pro_id'",$dbLink);
while($row=mysql_fetch_array($get)){
$name=$row['Product_Name'];
$location=$row['Product_Location'];
$old_quan=$row['Quantity'];
$new_quan=$old_quan-$order;
$r=mysql_query("update product set Quantity='$new_quan' where Product_ID='$pro_id'  ",$dbLink);
$sold=mysql_query("insert into sold_products values('$name','$pro_id','$location','','$order','$dat','$seller_id') ",$dbLink);
	

}

		}




}else{
$pro_id=$_REQUEST['pid'];
$order=$_REQUEST['ordq'];
$r=mysql_query("update new_pro_order set Order_Status='1' where Order_Id='$order_id'  ",$dbLink);

$upd_old=mysql_query("select * from supplier_product where Product_ID='$pro_id'",$dbLink);
while($row2=mysql_fetch_array($upd_old)){
$oldval=$row2['Quantity'];
$newq=$oldval-$order;
$up=mysql_query("update supplier_product set Quantity='$newq' where Product_ID='$pro_id'  ",$dbLink);
	}

}
	
	if($r){
	
		$result=1;
	}else{
		$result=0;
		
	}
	
	echo($result);
	//echo($id);
	mysql_close($dbLink);
?>