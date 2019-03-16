package usecases.dtos.output

import domain.models.CardId
import usecases.dtos.OutputDto

// 作成されていればSomeでIDを返し、失敗したらNoneを返す
case class SendCardOutputDto(createdCardId: Option[CardId]) extends OutputDto
