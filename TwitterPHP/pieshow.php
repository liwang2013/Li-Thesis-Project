<?php 

function pieshow($query) {
require_once ('jpgraph/jpgraph.php');
require_once ('jpgraph/jpgraph_pie.php');
require_once ('jpgraph/jpgraph_pie3d.php');

// connect to database
mysql_connect("localhost","root","root") or die (mysql_error());
mysql_select_db("tweets") or die (mysql_error());

$str = explode("-", $query);
// count the # of element in the string array
$length = count($str);

$data = array($length);       // data to display
$labels = array($length);     // labels to display

for ($i = 0; $i < $length; $i++) {
	if ($_POST['piequerytype']=="Equal") {
		// select hashtag table
		if($_POST['piemonth'] == "Sep")
			$htable = "hashtagsep";
		else if($_POST['piemonth'] == "Oct")
			$htable = "hashtagoct";
		else if($_POST['piemonth'] == "Nov")
			$htable = "hashtagnov";
		else if($_POST['piemonth'] == "All")
			$htable = "hashtag";
		else
			$htable = "hashtagoct";
		
		$q = "SELECT * FROM " .$htable ." WHERE hashtag = '" .$str[$i] ."'";
	}
		
	else {
		// select hashtag table
		if($_POST['piemonth'] == "Sep")
			$htable = "hashtagsep";
		else if($_POST['piemonth'] == "Oct")
			$htable = "hashtagoct";
		else if($_POST['piemonth'] == "Nov")
			$htable = "hashtagnov";
		else if($_POST['piemonth'] == "All")
			$htable = "hashtag";
		else
			$htable = "hashtagoct";
		
		$q = "SELECT * FROM " .$htable ." WHERE hashtag LIKE '%" .$str[$i] ."%'";
	}
		
	$result = mysql_query($q);
	$data[$i] = mysql_num_rows($result);
	$labels[$i] = "$str[$i]\n(%.1f%%)";
}

// Create the Pie Graph.
$graph = new PieGraph(700,300);

$theme_class= new VividTheme;
$graph->SetTheme($theme_class);

// Set A title for the plot
$graph->title->Set("Comparasion Plot");

// Input data to draw the plot
$p = new PiePlot3D($data);

// Set labels display and position
$p->SetLabels($labels);
$p->SetLabelPos(1);
// Setting the perspective angle to 60 degrees
$p->SetAngle(50);
$p->SetStartAngle(290);
$p->SetCenter(0.5, 0.4);
$p->SetSize(0.4);
$p->SetLegends($str);
$p->ShowBorder();
$p->SetColor('black');
$p->ExplodeSlice(1);
// Add and stroke
$graph->Add($p);
$graph->Stroke("/Applications/XAMPP/xamppfiles/htdocs/Twitter/img/pie.jpg");

}

?>