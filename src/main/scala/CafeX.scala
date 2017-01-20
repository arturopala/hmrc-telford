package hmrc.exercise

import scala.util.Try

case class Item(name: String, price: BigDecimal)

class CafeX(menu: List[Item]) {

    def bill(items: List[String]): Try[BigDecimal] = ???

}

object CafeX {
    def apply(menu: List[Item]): CafeX = new CafeX(menu)
}
