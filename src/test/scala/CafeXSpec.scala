package hmrc.exercise

import org.scalatest.{WordSpecLike, Matchers, GivenWhenThen}
import org.scalatest.prop.PropertyChecks
import org.scalacheck._
import scala.util.{Success, Failure}

class CafeXSpec extends WordSpecLike with Matchers {

    implicit def toBigDecimal(price: String): BigDecimal = BigDecimal.exact(price)

    "A Cafe X " when {

        val menu: List[Item] = List(
            Item("Cola", isFood = false, isHot = false, price = 0.50),
            Item("Coffee", isFood = false, isHot = true, price = 1.00),
            Item("Cheese Sandwich", isFood = true, isHot = false, price = 2.00),
            Item("Steak Sandwich", isFood = true, isHot = true, price = 4.50))

        "passed in a list of purchased items" should {

            "produce a failure when item is not in a menu" in {
                val cafe = new CafeX(menu)
                cafe.bill(List("Cola Light")).isFailure shouldBe true
                cafe.bill(List("Cola", "Cola Light")).isFailure shouldBe true
                cafe.bill(List("Cola Light", "Cola")).isFailure shouldBe true
                cafe.bill(List("Cola", "Cola Light", "Cola")).isFailure shouldBe true
            }

        }

        "all purchased items are drinks" should {
            "no service charge is applied" in {
                val cafe = new CafeX(menu)
                cafe.bill(List("Cola")) shouldBe Success(BigDecimal("0.50"))
                cafe.bill(List("Coffee")) shouldBe Success(BigDecimal("1.00"))
                cafe.bill(List("Cola", "Coffee")) shouldBe Success(BigDecimal("1.50"))
                cafe.bill(List("Cola", "Cola")) shouldBe Success(BigDecimal("1.00"))
                cafe.bill(List("Cola", "Coffee", "Cola")) shouldBe Success(BigDecimal("2.00"))
            }

        }

        "purchased items include any food" should {
            "apply a service charge of 10% to the total bill (rounded to 2 decimal places)" in {
                val cafe = new CafeX(menu)
                cafe.bill(List("Cheese Sandwich")) shouldBe Success(BigDecimal("2.20"))
                cafe.bill(List("Cheese Sandwich", "Cola")) shouldBe Success(BigDecimal("2.75"))
                cafe.bill(List("Cheese Sandwich", "Cola", "Cola")) shouldBe Success(BigDecimal("3.30"))
                cafe.bill(List("Cheese Sandwich", "Coffee")) shouldBe Success(BigDecimal("3.30"))
                cafe.bill(List("Cheese Sandwich", "Coffee", "Cola", "Cola")) shouldBe Success(BigDecimal("4.40"))
            }
        }

        "purchased items include any hot food" should {
            "apply a service charge of 20% to the total bill" in {
                val cafe = new CafeX(menu)
                cafe.bill(List("Steak Sandwich")) shouldBe Success(BigDecimal("5.40"))
                cafe.bill(List("Steak Sandwich", "Cola")) shouldBe Success(BigDecimal("6.00"))
                cafe.bill(List("Steak Sandwich", "Cheese Sandwich", "Cola", "Coffee")) shouldBe Success(BigDecimal("9.60"))
            }
            "apply a maximum £20 service charge" in {
                val cafe = new CafeX(menu)
                cafe.bill(List.fill(23)("Steak Sandwich")) shouldBe Success(BigDecimal("123.50"))
                cafe.bill(List.fill(20)("Steak Sandwich") ++ List.fill(20)("Cola")) shouldBe Success(BigDecimal("120.00"))
            }
        }

    }

}
