<?php
$server='localhost';
$user ='root';
$pass='';
$database ='student_ws';

$conn = new mysqli($server,$user,$pass,$database);

if ($conn->connect_error) {
    die(json_encode(["error" => "Kết nối thất bại: " . $conn->connect_error]));
}

class SinhVien {
    public $ID, $Name, $email, $phone;

    function __construct($id, $name, $email, $phone) {
        $this->ID = $id;
        $this->Name = $name;
        $this->email = $email;
        $this->phone = $phone;
    }
}

$mangSV = array();
$sql = "SELECT * FROM students";
$result = $conn->query($sql); // 🔹 Đảm bảo biến $result được khai báo

if ($result) {
    while ($row = $result->fetch_assoc()) {
        $mangSV[] = new SinhVien($row['id'], $row['name'], $row['email'], $row['phone']);
    }
    echo json_encode($mangSV);
} else {
    echo json_encode(["error" => "Truy vấn thất bại: " . $conn->error]);
}

$conn->close();

?>
