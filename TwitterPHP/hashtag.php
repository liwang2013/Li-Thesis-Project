<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Hashtag Table</title>
<style type="text/css">
.paginate {
	font-family: Arial, Helvetica, sans-serif;
	font-size: .7em;
}

a.paginate {
	border: 1px solid #000080;
	padding: 2px 6px 2px 6px;
	text-decoration: none;
	color: #000080;
}

a.paginate:hover {
	background-color: #000080;
	color: #FFF;
	text-decoration: underline;
}

a.current {
	border: 1px solid #000080;
	font: bold .7em Arial,Helvetica,sans-serif;
	padding: 2px 6px 2px 6px;
	cursor: default;
	background:#2F63FB;
	color: #FFF;
	text-decoration: none;
}

span.inactive {
	border: 1px solid #999;
	font-family: Arial, Helvetica, sans-serif;
	font-size: .7em;
	padding: 2px 6px 2px 6px;
	color: #999;
	cursor: default;
}

table {
	margin: 8px;
	border: #87B61D;
}

th {
	font-family: Arial, Helvetica, sans-serif;
	font-size: .7em;
	background: #98C133;
	color: #FFF;
	padding: 2px 6px;
	border-collapse: separate;
	border: 1px solid #000;
}

td {
	font-family: Arial, Helvetica, sans-serif;
	font-size: .7em;
	background: #E6F1C9;
	border: 1px solid #DDD;
}

#field1::-webkit-input-placeholder { color:#6DEFFF; } //input placeholder text color

#div1 {
	float: left;
}

#div2 {
	float: right;
	margin-right: 500px;
}

</style>

<script>
function hilite(elem)
{
	elem.style.background = '#F66934';  
}

function lowlite(elem)
{
	elem.style.background = '';
}
</script>

</head>

<body bgcolor="#F9FCFC">

<div style="color:#8670C0">
	<h2 align="center">Hashtag Table</h2>
</div>

<!--hashtag table-->
<form method="post" action="hashtag2.php">
	<input type="text" value="<?php echo htmlspecialchars($_POST['input']); ?>" placeholder="Hashtag Query" 
	id="field1" name="input" STYLE="color: #FFFFFF; font-family: Verdana; font-weight: bold; 
	font-size: 12px; background-color: #72A4D2;" size="12" maxlength="40">
	
	<select name="dbase">
		<option value="">-- DBase --</option>
		<?php if($_POST['dbase'] == true){ ?>
 		<option value="<?php echo $_POST['dbase']; ?>" selected="selected"><?php echo $_POST['dbase']; ?></option>
 		<?php }else if($_GET['dbase']){ ?>
 		<option value="<?php echo $_GET['dbase']; ?>" selected="selected"><?php echo $_GET['dbase']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
		<option value="Sep">Sep</option>
		<option value="Oct">Oct</option>
		<option value="Nov">Nov</option>
		<option value="All">All</option>
	</select>
	
	<select name="hashtagquerytype">
		<option value="">-- Query --</option>
		<?php if($_POST['hashtagquerytype'] == true){ ?>
 		<option value="<?php echo $_POST['hashtagquerytype']; ?>" selected="selected"><?php echo $_POST['hashtagquerytype']; ?></option>
 		<?php }else if($_GET['hashtagquerytype']){ ?>
 		<option value="<?php echo $_GET['hashtagquerytype']; ?>" selected="selected"><?php echo $_GET['hashtagquerytype']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
 		<option value="Like">Like</option>
		<option value="Equal">Equal</option>
	</select>
	
	<input type="submit" value="Submit">
</form>

<!--pie plot-->
<div STYLE="position:absolute; TOP:150px; LEFT:500px; HEIGHT:20px">
<form method="post" action="hashtag2.php">
	
	<input type="text" value="<?php echo htmlspecialchars($_POST['pieinput']); ?>" placeholder="Pie Plot"
	id="field1" name="pieinput" STYLE="color: #FFFFFF; font-family: Verdana; font-weight: bold; 
	font-size: 12px; background-color: #72A4D2;" size="18" maxlength="40"/>
	
	<select name="piemonth">
		<option value="">-- Month --</option>
		<?php if($_POST['piemonth'] == true){ ?>
 		<option value="<?php echo $_POST['piemonth']; ?>" selected="selected"><?php echo $_POST['piemonth']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
		<option value="Sep">Sep</option>
		<option value="Oct">Oct</option>
		<option value="Nov">Nov</option>
		<option value="All">All</option>
	</select>
	
	<select name="piequerytype">
		<option value="">-- Query --</option>
		<?php if($_POST['piequerytype'] == true){ ?>
 		<option value="<?php echo $_POST['piequerytype']; ?>" selected="selected"><?php echo $_POST['piequerytype']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
		<option value="Like">Like</option>
		<option value="Equal">Equal</option>
	</select>
	
	<input type="submit" value="Submit">
</form>
</div>

<!--bar plot-->	
<div STYLE="position:absolute; TOP:410px; LEFT:500px">
<form method="post" action="hashtag2.php">

	<input type="text" value="<?php echo htmlspecialchars($_POST['barinput']); ?>" placeholder="Bar Plot" 
	id="field1" name="barinput" STYLE="color: #FFFFFF; font-family: Verdana; font-weight: bold; 
	font-size: 12px; background-color: #72A4D2;" size="18" maxlength="40">
	
	<select name="barmonth">
		<option value="">-- Month --</option>
		<?php if($_POST['barmonth'] == true){ ?>
 		<option value="<?php echo $_POST['barmonth']; ?>" selected="selected"><?php echo $_POST['barmonth']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
		<option value="Sep">Sep</option>
		<option value="Oct">Oct</option>
		<option value="Nov">Nov</option>
		<option value="All">All</option>
	</select>
	
	<select name="barweek">
		<option value="">-- Week --</option>
		<?php if($_POST['barweek'] == true){ ?>
 		<option value="<?php echo $_POST['barweek']; ?>" selected="selected"><?php echo $_POST['barweek']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
		<option value="NA">NA</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
	</select>
	
	<select name="barquerytype">
		<option value="">-- Query --</option>
		<?php if($_POST['barquerytype'] == true){ ?>
 		<option value="<?php echo $_POST['barquerytype']; ?>" selected="selected"><?php echo $_POST['barquerytype']; ?></option>
 		<?php }else{ ?>
 		<option value=""> </option>
  		<?php } ?>
		<option value="Like">Like</option>
		<option value="Equal">Equal</option>
	</select>
	
	<input type="submit" value="Submit">
</form>
</div>

<a href="javascript:document.location.reload()" ONMOUSEOVER="window.status='Refresh'; return true">
<img STYLE="position:absolute; TOP:180px; LEFT:500px; WIDTH:500px; HEIGHT:225px" src="/Twitter/img/pie.jpg?dummy=8484744" >
</a>

<a href="javascript:document.location.reload()" ONMOUSEOVER="window.status='Refresh'; return true" ONMOUSEOUT="window.status='lol'">
<img STYLE="position:absolute; TOP:440px; LEFT:500px; WIDTH:500px; HEIGHT:225px" src="/Twitter/img/bar.jpg?dummy=8484744" >
</a>

<?php
//include paginator.php once
require_once('paginator.php');
require_once('barshow.php');
require_once('pieshow.php');

if($_POST['pieinput'])
	pieshow($_POST['pieinput']);

if($_POST['barinput'])
	barshow($_POST['barinput']);

//connect to database
mysql_connect("localhost","root","root") or die (mysql_error());
mysql_select_db("tweets") or die (mysql_error());



//database query from input field or using default query
if($_POST['input']) {
	// select hashtag table
	if($_POST['dbase'] == "Sep")
		$htable = "hashtagsep";
	else if($_POST['dbase'] == "Oct")
		$htable = "hashtagoct";
	else if($_POST['dbase'] == "Nov")
		$htable = "hashtagnov";
	else
		$htable = "hashtagoct";
	
	// select query method: like or =
	if($_POST['hashtagquerytype']=="Equal")
		$q = "SELECT * FROM " .$htable ." WHERE hashtag = '" .$_POST['input'] ."'";
	else
		$q = "SELECT * FROM " .$htable ." WHERE hashtag LIKE '%" .$_POST['input'] ."%' ";
}
else if($_GET['input']) {
	// select hashtag table
	if($_GET['dbase'] == "Sep")
		$htable = "hashtagsep";
	else if($_GET['dbase'] == "Oct")
		$htable = "hashtagoct";
	else if($_GET['dbase'] == "Nov")
		$htable = "hashtagnov";
	else
		$htable = "hashtagoct";

	// select query method: like or =
	if($_GET['hashtagquerytype']=="Equal")
		$q = "SELECT * FROM " .$htable ." WHERE hashtag = '" .$_GET['input'] ."'";
	else
		$q = "SELECT * FROM " .$htable ." WHERE hashtag LIKE '%" .$_GET['input'] ."%' ";
}
else
	$q = "SELECT * FROM hashtagoct ORDER BY ID ASC";
	
$sql = mysql_query($q);

$pages = new Paginator;
$pages->items_total = mysql_num_rows($sql);       
$pages->mid_range = 7;
$pages->paginate();

echo $pages->display_pages();
echo $pages->display_jump_menu(); // displays the page jump menu
echo $pages->display_items_per_page(); // displays the items per page menu

//add page limitation to query
$query = $q ." $pages->limit";
$result = mysql_query($query);

$pagenumlow = $pages->low + 1;
$pagenumhigh = $pages->high + 1;
echo "<p class=\"paginate\">$q (retrieve records $pagenumlow-$pagenumhigh 
		from table - $pages->items_total item total / $pages->items_per_page items per page)";
echo '<br />';

//display data in table
echo "<table>";
echo "<tr><th>ID</th><th>Hashtag</th><th>TweetID</th><th>CreateTime</th></tr>";

while($row = mysql_fetch_row($result))
{
	echo "<tr onmouseover=\"hilite(this)\" onmouseout=\"lowlite(this)\"><td>$row[0]</td><td>$row[1]</td><td>$row[2]</td><td>$row[3]</td></tr>\n";
}
echo "</table>";

echo $pages->display_pages();

?>

</body>
</html>