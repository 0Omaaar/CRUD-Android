<?php
include 'config.php';

$name = $_GET['name'];
$address = $_GET['address'];

$sql = "INSERT INTO info (name, address) VALUES (?, ?)";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $name, $address);

if ($stmt->execute()) {
    echo "Record added successfully";
} else {
    echo "Error: " . $stmt->error;
}

$stmt->close();
$conn->close();
?>