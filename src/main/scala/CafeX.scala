package hmrc.exercise

import scala.util.Try

case class Item(name: String, price: BigDecimal)

class CafeX(menu: List[Item]) {

    def findItem(item: String): Item =
        menu.find(_.name == item).getOrElse(throw new Exception(s"Menu item '$item' not found"))

    def bill(items: List[String]): Try[BigDecimal] = Try {
        items.map(findItem).map(_.price).sum
    }

}

object CafeX {
    def apply(menu: List[Item]): CafeX = new CafeX(menu)
}
