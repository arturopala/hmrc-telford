package hmrc.exercise

import org.scalatest.{WordSpecLike, Matchers, GivenWhenThen}
import org.scalatest.prop.PropertyChecks
import org.scalacheck._
import scala.util.{Success, Failure}

class CafeXSpec extends WordSpecLike with Matchers {

    implicit def toBigDecimal(price: String): BigDecimal = BigDecimal.exact(price)

    "A Cafe X " when {

        val menu: List[Item] = List(
            Item("Cola", price = "0.50"),
            Item("Coffee", price = "1.00"),
            Item("Cheese Sandwich", price = "2.00"),
            Item("Steak Sandwich", price = "4.50"))

        "passed in a list of purchased items" should {

            "produce a total bill" in {
                val cafe = new CafeX(menu)
                cafe.bill(List("Cola", "Coffee")) shouldBe Success(BigDecimal("1.50"))
                cafe.bill(List("Cola", "Cola")) shouldBe Success(BigDecimal("1.00"))
                cafe.bill(List("Coffee", "Cola", "Cheese Sandwich")) shouldBe Success(BigDecimal("3.50"))
                cafe.bill(List("Coffee", "Cola", "Cheese Sandwich", "Steak Sandwich")) shouldBe Success(BigDecimal("8.00"))
                cafe.bill(List("Coffee", "Cola", "Cheese Sandwich", "Steak Sandwich", "Cola", "Cheese Sandwich")) shouldBe Success(BigDecimal("10.50"))
            }

            "produce a failure when item is not in a menu" in {
                val cafe = new CafeX(menu)
                cafe.bill(List("Cola Light")).isFailure shouldBe true
                cafe.bill(List("Cola", "Cola Light")).isFailure shouldBe true
                cafe.bill(List("Cola Light", "Cola")).isFailure shouldBe true
                cafe.bill(List("Cola", "Cola Light", "Cola")).isFailure shouldBe true
            }

        }

    }

}
