package systems.enliven.iso

import org.scalatest.wordspec.AnyWordSpec
import systems.enliven.iso.ISOCountry.ISOCountry
import systems.enliven.iso.ISOCurrency.ISOCurrency

class ISOCurrencySpec extends AnyWordSpec {

  "ISOCurrency enum object" should {

    "return ISOCurrency from currency code" in {
      assert(ISOCurrency.apply("USD").isInstanceOf[ISOCurrency])
      assert(ISOCurrency("USD").isInstanceOf[ISOCurrency])
    }

    "return Option[ISOCurrency] from currency code" in {
      assert(ISOCurrency.from("USD").isInstanceOf[Option[ISOCurrency]])
      assert(ISOCurrency.from("USD").isDefined)
    }

    "return ISOCurrency using the Currency numeric code" in {
      assert(ISOCurrency.apply(840).isInstanceOf[ISOCurrency])
      assert(ISOCurrency(840).isInstanceOf[ISOCurrency])
    }

    "return Option[ISOCurrency] using the Currency numeric code" in {
      assert(ISOCurrency.from(840).isInstanceOf[Option[ISOCurrency]])
      assert(ISOCurrency.from(840).isDefined)
    }

    "return ISOCurrency using the ISOCountry" in {
      assert(ISOCurrency.apply(ISOCountry.JAPAN).isInstanceOf[ISOCurrency])
      assert(ISOCurrency(ISOCountry.JAPAN).isInstanceOf[ISOCurrency])
    }

    "return Option[ISOCurrency] using ISOCountry" in {
      assert(ISOCurrency.from(ISOCountry.JAPAN).isInstanceOf[Option[ISOCurrency]])
      assert(ISOCurrency.from(ISOCountry.JAPAN).isDefined)
    }

    "return None when Currency code does not exist" in {
      assert(ISOCurrency.from("XXXX").isEmpty)
    }

    "return None when Currency numeric code does not exist" in {
      assert(ISOCurrency.from(9999).isEmpty)
    }

    "return ISOCurrency code value property" in {
      assert(ISOCurrency.apply(840).value == "USD")
    }

    "return ISOCurrency numericalCode property" in {
      assert(ISOCurrency.apply("USD").numericalCode == 840)
    }

    "return ISOCurrency minorUnit property" in {
      assert(ISOCurrency.apply("USD").minorUnit == 2)
    }
    "return ISOCurrency countries property" in {
      assert(ISOCurrency.apply("JPY").countries == Seq(ISOCountry.JAPAN))
    }

    "return ParseException when currency code does not exist" in {
      val caught = intercept[ParseException] {
        ISOCurrency("XXXX")
      }
      assert(caught.getMessage.indexOf("-1") == -1)
      assert(caught.getMessage == "Invalid currency code 'XXXX' for ISOCurrency")
    }

    "return ParseException when numeric code does not exist" in {
      val caught = intercept[ParseException] {
        ISOCurrency(9999)
      }
      assert(caught.getMessage.indexOf("-1") == -1)
      assert(caught.getMessage == "Invalid numeric code '9999' for ISOCurrency")
    }

    "return ParseException when ISOCountry does not exist" in {
      val caught = intercept[ParseException] {
        ISOCurrency(new ISOCountry("", 0, "", "", ISOContinent.ANTARCTICA))
      }
      assert(caught.getMessage.indexOf("-1") == -1)
      assert(caught.getMessage == "Invalid country '' for ISOCurrency")
    }
  }
}
