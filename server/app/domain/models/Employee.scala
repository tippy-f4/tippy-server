package domain.models

class Employee (
  val id: EmployeeId,
  val name: EmployeeName,
  val nickName: NickName,
  val faceImage: FaceImage
)

case class EmployeeId(value: String) extends AnyVal
case class EmployeeName(value: String) extends AnyVal
case class NickName(value: String) extends AnyVal
case class FaceImage(url: String) extends AnyVal