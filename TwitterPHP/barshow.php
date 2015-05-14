<?php

function barshow($query) {

require_once ('jpgraph/jpgraph.php');
require_once ('jpgraph/jpgraph_bar.php');

//connect to database
mysql_connect("localhost","root","root") or die (mysql_error());
mysql_select_db("tweets") or die (mysql_error());

// select hashtag table
if($_POST['barmonth'] == "Sep") {
	$twttable = "tweetsep";
}
else if($_POST['barmonth'] == "Oct")
	$twttable = "tweetoct";
else if($_POST['barmonth'] == "Nov")
	$twttable = "tweetnov";
else if($_POST['barmonth'] == "All")
	$twttable = "tweet";
else
	$twttable = "tweetoct";

if ($_POST['barquerytype']=="Equal")
	$q = "SELECT * FROM " .$twttable ." WHERE tweet = '" .$query ."'";
else
	$q = "SELECT * FROM " .$twttable ." WHERE tweet LIKE '%" .$query ."%'";

$result = mysql_query($q);

if ($_POST['barweek']=="1") {
	$date = array(7);
	while($row = mysql_fetch_row($result))
	{
		$str = explode(" ", $row[4]);
		$str[2] = (int) $str[2];
		if($str[2] < 8)
			$date[$str[2] - 1]++;
	}
}
else if ($_POST['barweek']=="2") {
	$date = array(7);
	while($row = mysql_fetch_row($result))
	{
		$str = explode(" ", $row[4]);
		$str[2] = (int) $str[2];
		if($str[2] > 7 && $str[2] < 15)
			$date[$str[2] - 8]++;
	}
}
else if ($_POST['barweek']=="3") {
	$date = array(7);
	while($row = mysql_fetch_row($result))
	{
		$str = explode(" ", $row[4]);
		$str[2] = (int) $str[2];
		if($str[2] > 14 && $str[2] < 22)
			$date[$str[2] - 15]++;
	}
}
else if ($_POST['barweek']=="4") {
	$date = array(7);
	while($row = mysql_fetch_row($result))
	{
		$str = explode(" ", $row[4]);
		$str[2] = (int) $str[2];
		if($str[2] > 21 && $str[2] < 29)
			$date[$str[2] - 22]++;
	}
}
else if ($_POST['barmonth']=="Sep") {
	$date = array(19);
	while($row = mysql_fetch_row($result))
	{
		$str = explode(" ", $row[4]);
		$str[2] = (int) $str[2];
		$date[$str[2] - 12]++;
	}
}
else {
	$date = array(31);
	while($row = mysql_fetch_row($result))
	{
		$str = explode(" ", $row[4]);
		$str[2] = (int) $str[2];
		$date[$str[2] - 1]++;
	}
}

// Create the graph. These two calls are always required
$graph = new Graph(700,300,'auto');
$graph->SetScale("textlin");
$graph->SetShadow();
$graph->SetMargin(40,30,20,40);

//$theme_class=new UniversalTheme;
//$graph->SetTheme($theme_class);

$graph->SetBox(false);

//$graph->ygrid->SetFill(false);  //true for gray and white stripes
//$graph->xaxis->SetTickLabels(A);
//$graph->yaxis->HideLine(false);
//$graph->yaxis->HideTicks(false,false);

// Create the bar plots
$plot = new BarPlot($date);

$plot->SetColor("white");
$plot->SetFillColor("#cc1111");
$graph->Add($plot);

// Setup the titles
$graph->title->Set("Bar Plot");
$graph->xaxis->title->Set('October');
$graph->yaxis->title->Set('Hashtag#');

//$graph->title->SetFont(FF_FONT1,FS_BOLD);
$graph->yaxis->title->SetFont(FF_FONT1,FS_BOLD);
$graph->xaxis->title->SetFont(FF_FONT1,FS_BOLD);

// Display the graph
//$graph->Stroke();

// must use absolute path
$graph->Stroke("/Applications/XAMPP/xamppfiles/htdocs/Twitter/img/bar.jpg"); 

function datashow() {
	for($k=0; $k<32; $k++) {
		echo $date[$k]."   ";
		echo '<br />';
	}
}

datashow();
}

?>