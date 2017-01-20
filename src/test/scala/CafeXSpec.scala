package hmrc.exercise

import org.scalatest.{WordSpecLike, Matchers, GivenWhenThen}
import org.scalatest.prop.PropertyChecks
import org.scalacheck._
import scala.util.{Success, Failure}

class CafeXSpec extends WordSpecLike with Matchers {

    "A Cafe X " when {

        "passed in a list of purchased items" should {

            "produce a total bill" in {
            }

            "produce a failure when item is not in a menu" in {
            }

        }

    }

}
