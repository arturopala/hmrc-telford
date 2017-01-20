package hmrc.exercise

import scala.util.Try

case class Item(name: String, isFood: Boolean, isHot: Boolean, price: BigDecimal)

class CafeX(menu: List[Item]) {

    def bill(items: List[String]): Try[BigDecimal] = Try {
        val purchase = items.map(findItem)
        val total = purchase.map(_.price).sum
        total + tip(purchase, total)
    }

    private def findItem(item: String): Item =
        menu.find(_.name == item).getOrElse(throw new Exception(s"Menu item '$item' not found"))

    private def tip(items: List[Item], total: BigDecimal) = {
        val hasHotFood = items.exists(i => i.isHot && i.isFood)
        val hasFood = items.exists(_.isFood)
        if (hasHotFood) (total * BigDecimal("0.20")).min(BigDecimal("20.00"))
        else if (hasFood) total * BigDecimal("0.10")
        else BigDecimal("0.00")
    }

}

object CafeX {
    def apply(menu: List[Item]): CafeX = new CafeX(menu)
}
