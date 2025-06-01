<?php
include 'config.php';

$id = $_GET['id'];
$name = $_GET['name'];
$address = $_GET['address'];

$sql = "UPDATE info SET name=?, address=? WHERE id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ssi", $name, $address, $id);

if ($stmt->execute()) {
    echo "Record updated successfully";
} else {
    echo "Error: " . $stmt->error;
}

$stmt->close();
$conn->close();
?>