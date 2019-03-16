package usecases.dtos.output

import domain.models.CardId

// 作成されていればSomeでIDを返し、失敗したらNoneを返す
case class PraiseCardOutputDto(praisedCardId: Option[CardId])
