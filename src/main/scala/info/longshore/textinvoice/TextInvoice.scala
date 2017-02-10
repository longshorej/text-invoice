package info.longshore.textinvoice

import argonaut._
import sbt.io.IO

import Argonaut._

case class Bill(consultancy: String,
                address: String,
                invoice: String,
                date: String,
                rate: Double,
                items: Vector[Item])

case class Item(description: String, hours: Double)

object TextInvoice {
  implicit val itemCodec = CodecJson.derive[Item]
  implicit val billCodec = CodecJson.derive[Bill]

  def main(args: Array[String]): Unit = {
    val json = IO.readStream(System.in)

    json.decodeEither[Bill] match {
      case Left(err) =>
        System.err.println(err)

      case Right(ok) =>
        val formatted = formatTerribly(ok)

        System.out.print(formatted)
        System.out.println()
    }
  }
}
