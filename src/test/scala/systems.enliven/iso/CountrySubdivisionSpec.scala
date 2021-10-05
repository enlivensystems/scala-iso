package systems.enliven.iso

import org.scalatest.wordspec.AnyWordSpec
import systems.enliven.iso.ISOCountrySubdivision.ISOCountrySubdivision

class CountrySubdivisionSpec extends AnyWordSpec {

  "ISOCountrySubdivision enum object" should {

    "return ISOCountrySubdivision from an 3166-2 code" in {
      assert(ISOCountrySubdivision.apply("US-NY").isInstanceOf[ISOCountrySubdivision])
      assert(ISOCountrySubdivision("US-NY").isInstanceOf[ISOCountrySubdivision])
    }

    "return Option[ISOCountrySubdivision] from an 3166-2 code" in {
      assert(ISOCountrySubdivision.from("US-NY").isInstanceOf[Option[ISOCountrySubdivision]])
      assert(ISOCountrySubdivision.from("US-NY").isDefined)
    }

    "return list of subdivisions from a ISOCountry" in {
      assert(
        ISOCountrySubdivision.fromCountry(ISOCountry.AUSTRALIA) == Seq(
          ISOCountrySubdivision.`Australian Capital Territory`,
          ISOCountrySubdivision.`New South Wales`,
          ISOCountrySubdivision.`Northern Territory`,
          ISOCountrySubdivision.`Queensland`,
          ISOCountrySubdivision.`South Australia`,
          ISOCountrySubdivision.`Tasmania`,
          ISOCountrySubdivision.`Victoria - Australia`,
          ISOCountrySubdivision.`Western Australia`
        )
      )
    }

    "return None when 3166-2 code does not exist" in {
      assert(ISOCountrySubdivision.from("XX-XX").isEmpty)
    }

    "return ISOCountrySubdivision subdivisionName property" in {
      assert(ISOCountrySubdivision("US-NY").subdivisionName == "New York")
      assert(ISOCountrySubdivision("JP-13").subdivisionName == "Tokyo")
    }

    "return ISOCountrySubdivision 3166-2 property" in {
      assert(ISOCountrySubdivision("US-NY").value == "US-NY")
      assert(ISOCountrySubdivision("JP-13").value == "JP-13")
    }

    "return ISOCountry country property" in {
      assert(ISOCountrySubdivision("US-NY").country == ISOCountry.UNITED_STATES)
      assert(ISOCountrySubdivision("JP-13").country == ISOCountry.JAPAN)
    }

    "return ParseException when 3166-2 code does not exist" in {
      val caught = intercept[ParseException] {
        ISOCountrySubdivision("XXX")
      }
      assert(caught.getMessage.indexOf("-1") == -1)
      assert(caught.getMessage == "Invalid 3166-2 code 'XXX' for ISOCountrySubdivision")
    }
  }
}
