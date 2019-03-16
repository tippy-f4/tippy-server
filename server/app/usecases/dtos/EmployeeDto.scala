package usecases.dtos

import domain.models.Employee
import play.api.libs.json.{ Format, Json }

case class EmployeeDto(
  id: String,
  name: String,
  nick_name: String,
  faceImage: String
)

object EmployeeDto {
  implicit val jsonFormat: Format[EmployeeDto] = Json.format[EmployeeDto]

  def model2Dto(employee: Employee): EmployeeDto = {
    new EmployeeDto(
      employee.id.value,
      employee.name.value,
      employee.nickName.value,
      employee.faceImage.url
    )
  }
}
