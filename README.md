วิธีการรันโปรแกรม
	-ดับเบิ้ลคลิก 6210401295.jar ที่อยู่ใน โฟลเดอร์ Application
	-หากดับเบิ้ลคลิกไม่ได้ ให้รันคำสั่ง cmd โดย directory ที่ทำการรันคำสั่งต้องเป็นตำแหน่งเดียวกันกับที่เก็บ 6210401295.jar รันคำสั่ง :  java -jar 6210401295.jar

โครงสร้างไฟล์
	- Application : เก็บ file ต่างๆ ที่จำเป็นสำหรับการรันโปรแกรม
		- csv : เก็บ file csv 
			- admin.csv 		: ไฟล์ csv ที่เก็บรายชื่อบัญชี ของ ผู้ดูแลระบบ
			- items.csv 		: ไฟล์ csv ที่เก็บรายการสิ่งของต่างๆ ที่คงอยู่ในส่วนกลาง
			- receivedItem.csv 	: ไฟล์ csv ที่เก็บรายการสิ่งของต่างๆ ที่ ผู้พักอาศัย รับไปจาก ส่วนกลางแล้ว
			- resident.csv 		: ไฟล์ csv ที่เก็บรายชื่อบัญชี ของ ผู้พักอาศัย
			- room.csv 		: ไฟล์ csv ที่เก็บรายการข้อมูล ห้องพัก
			- staff.csv 		: ไฟล์ csv ที่เก็บรายชื่อบัญชี เจ้าหน้าที่ส่วนกลาง
		- images 	   : เก็บรูปภาพที่ ถูกอัพโหลดขึ้น โปรแกรม
		- lib 		   : เก็บ library ที่ใช้ในโปรแกรม
		- 6210401295.jar : ไฟล์ JAR สำหรับ เปิด เริ่มต้นโปรแกรม
		- 6210401295.pdf : เอกสารคู่มือการใช้งานโปรแกรม
	- src/main
		- java/condo
			- controllers 	: โฟลเดอร์เก็บคลาสต่างๆ ที่ใช้ควบคุมการทำงาน ของไฟล์ fxml
			- models 	: เก็บคลาสต่างๆ ที่เกี่ยวข้องกับการทำงาน ของโปรแกรม
			- services 	: เก็บคลาสต่างๆ ที่ใช้ อ่าน-เขียน ไฟล์ csv
		- resources
			- stylesheet 	: เก็บ stylesheet สำหรับใช้งาน ใน ไฟล์ fxml
			- fxml 		: เก็บ ไฟล์ fxml
			- images 	: เก็บ ไฟล์รูปภาพที่ถูกฝังกับ ไฟล์ fxml ตั้งแต่เริ่ม



สิ่งที่พัฒนาในแต่ละสัปดาห์

- สัปดาห์ที่ 1 (6-12 กันยายน 2563) : 
	- เริ่มเขียนโครง class ต่างๆ เช่น superclass(Account) ,subclass ของ Account(StaffAccount/AdminAccount) และ Class(AccountManagement) 
	- แก้ไข AccountManagement เพื่อทดลองการอ่านเขียน CSV ใน project 
	- พัฒนา GUI ใน fxml 
	- เพิ่มการใช้ maven ใน project

- สัปดาห์ที่ 2 (13-19 กันยายน 2563) : 
	- สร้าง controlles(AdminPageController,CheckUsernamePasswordPageController,HomeController),เพิ่ม Class(SortByDataAndTime), แก้ไข Class(StaffAccount) 
	- แก้ไข fxml (admin_page,check_username_password_page,home.fxml และ staff_page)

- สัปดาห์ที่ 3 (20-26 กันยายน 2563) : 
	- สร้าง (superclass(Item) ,subclass(Document,Parcel) ,ItemManagement ,Room ,RoomManagement) ใน models 
	- แก้ไข StaffAccount

- สัปดาห์ที่ 4 (27 กันยายน - 3 ตุลาคม 2563) : 
	- สร้าง class (ReadWriteAccountCsv,ReadWriteItemCsv,ReadWriteRoomCsv) ใน services โดยจะย้าย method ที่ เกี่ยวข้องกับการอ่านเขียน CSV จาก class ต่างๆ ใน models มาไว้ใน class เหล่านี้ และ ปรับแก้ controllers ที่เกี่ยวข้อง เพื่อที่จะสามารถอ่านเขียน CSV ใน JAR file ได้  , 
	- สร้าง StaffPageController ใน controllers เพื่อให้หน้า staff_page.fxml ทำงานได้ตามเกณฑ์กำหนด  
	- แก้ไข/ปรับปรุง controllers(AdminPageController) models(StaffAccount)
	- แก้ไข fxml(staff_page)

- สัปดาห์ที่ 5 (4-10 กันยายน 2563) : 
	- ใช้ exception handler ใน method ต่างๆ แทนการ return String 
	- เพิ่มส่วนการทำงานของ หน้ายืนยันการนำผู้เข้าพักออก (ข้อ 13.5)

- สัปดาห์ที่ 6 (11-17 กันยายน 2563)
	- เพิ่มส่วนการทำงานในส่วน ระบบบัญชีของ ผู้เข้าพักอาศัย
	- เพิ่มหน้า creator profile
	- เพิ่ม README.md
	- เพิ่มโฟลเดอร์ Application ที่เก็บ JAR file
	- ตรวจสอบและแก้ไข โปรแกรมให้เป็นไปตาม เกณฑ์กำหนด
	- เพิ่มไฟล์ pdf




