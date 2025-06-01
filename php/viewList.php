<?php
include 'config.php';

$sql = "SELECT * FROM info";
$result = mysqli_query($conn, $sql);

$data = array();
while($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode($data);

mysqli_close($conn);
?>