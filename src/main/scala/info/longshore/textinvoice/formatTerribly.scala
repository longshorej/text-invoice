package info.longshore.textinvoice

/** Given a bill formats it in a plain text format using terrible code */
object formatTerribly {
  def apply(bill: Bill): String = {
    val addressPrefix = "            |                                "

    val formattedAddress = bill.address.split('\n').map(addressPrefix + _).mkString("\n")
  
    val entries = bill.items.zipWithIndex.map { case (item, i) =>
      val amount = item.hours * bill.rate

      split(i, item.description, item.hours, amount)
    }
  
    val totalHours = bill.items.foldLeft(0D)(_ + _.hours)
    val totalAmount = totalHours * bill.rate
  
    val fHours = f"$totalHours%06.2f"
    val fAmount = f"$totalAmount%7.2f"
  
    val inlineAmount = f"$totalAmount%1.2f"
  
    s"""
            |Consultancy: ${bill.consultancy}
            |Invoice:     ${bill.invoice}
            |Date:        ${bill.date}
            |
            |
            |
            |
            |  Item No.      Description                                                                Hours               Amount
            |  -------------------------------------------------------------------------------------------------------------------
${entries.mkString("\n")}
            |  -------------------------------------------------------------------------------------------------------------------
            |  Total                                                                                   $fHours            $$ $fAmount
            |
            |
            |
            |
            |                                Please send payment of $$$inlineAmount to:
            |
$formattedAddress
            |
            |                                Thank you!
          """.trim.stripMargin
  }
  
  private def split(no: Int, entry: String, hours: Double, amount: Double): String = {
    val size = 75
    val pad = 1
    
    val prefix = "                "
    
    def step(ws: Seq[String], current: String, accum: Vector[String]): Vector[String] = {
      def currentFixed =
        if (accum.isEmpty)
          "  %03d          ".format(no + 1) +
          current +
          (" " * (size - current.length)) +
          f"$hours%06.2f" +
          "            $ " +
          f"$amount%7.2f"
        else
          prefix + current
      
      ws.headOption match {
        case None =>
          accum :+ currentFixed
        
        case Some(w) =>
          if (current.length + w.length + 1 > size - pad) step(ws.tail, w, accum :+ currentFixed)
          else step(ws.tail, s"$current $w", accum)
      }
    }
    
    val words =
      for {
        word  <- entry.split("[\\s]")
        parts <- word.grouped(size)
      } yield parts
    
    step(words, "", Vector.empty).mkString("\n")
  }
}
