package utils

case class InvalidParameterException(message: String) extends RuntimeException(message)
