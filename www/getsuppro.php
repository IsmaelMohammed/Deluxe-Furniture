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
	 //$username=$_REQUEST['username'];
        $sql_query = "SELECT * FROM supplier_product order by Product_ID Desc";
        $dbResult = mysql_query( $sql_query, $dbLink)or die(mysql_error());
$rows = array();
while($row = mysql_fetch_assoc($dbResult))
        {
           $rows[] = $row;
        }
mysql_close($dbLink);
echo json_encode($rows);
?>