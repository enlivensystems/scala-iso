package systems.enliven.iso

import org.scalatest.wordspec.AnyWordSpec
import systems.enliven.iso.ISOCountry.ISOCountry

class ISOCountrySpec extends AnyWordSpec {

  "ISOCountry enum object" should {

    "return ISOCountry from an alpha-2 code" in {
      assert(ISOCountry.apply("US").isInstanceOf[ISOCountry])
      assert(ISOCountry("US").isInstanceOf[ISOCountry])
    }

    "return Option[ISOCountry] from an alpha-2 code" in {
      assert(ISOCountry.from("US").isInstanceOf[Option[ISOCountry]])
      assert(ISOCountry.from("US").isDefined)
    }

    "return ISOCountry using the country numeric code" in {
      assert(ISOCountry.apply(840).isInstanceOf[ISOCountry])
      assert(ISOCountry(840).isInstanceOf[ISOCountry])
    }

    "return Option[ISOCountry] using the country numeric code" in {
      assert(ISOCountry.from(840).isInstanceOf[Option[ISOCountry]])
      assert(ISOCountry.from(840).isDefined)
    }

    "return None when country alpha-2 code does not exist" in {
      assert(ISOCountry.from("XXX").isEmpty)
    }

    "return None when country numeric code does not exist" in {
      assert(ISOCountry.from(999).isEmpty)
    }

    "return ISOCountry alpha-2 value property" in {
      assert(ISOCountry(840).value == "US")
    }

    "return list of ISOCountry based on ISOContinent" in {
      assert(
        ISOCountry.fromContinent(ISOContinent.OCEANIA) ==
          Vector.apply[ISOCountry](
            ISOCountry("AS"),
            ISOCountry("AU"),
            ISOCountry("CK"),
            ISOCountry("FJ"),
            ISOCountry("PF"),
            ISOCountry("GU"),
            ISOCountry("KI"),
            ISOCountry("MH"),
            ISOCountry("FM"),
            ISOCountry("NR"),
            ISOCountry("NC"),
            ISOCountry("NZ"),
            ISOCountry("NU"),
            ISOCountry("NF"),
            ISOCountry("MP"),
            ISOCountry("PW"),
            ISOCountry("PG"),
            ISOCountry("PN"),
            ISOCountry("WS"),
            ISOCountry("SB"),
            ISOCountry("TK"),
            ISOCountry("TO"),
            ISOCountry("TV"),
            ISOCountry("UM"),
            ISOCountry("VU"),
            ISOCountry("WF")
          )
      )
    }

    "return ISOCountry numericalCode property" in {
      assert(ISOCountry("US").numericalCode == 840)
    }
    "return ISOCountry englishName property" in {
      assert(ISOCountry("US").englishName == "United States of America")
    }
    "return ISOCountry alpha3Code property" in {
      assert(ISOCountry("US").alpha3Code == "USA")
    }
    "return ISOCountry continent property" in {
      assert(ISOCountry("US").continent == ISOContinent.NORTH_AMERICA)
    }

    "return ParseException when alpha-2 code does not exist" in {
      val caught = intercept[ParseException] {
        ISOCountry("XXX")
      }
      assert(caught.getMessage.indexOf("-1") == -1)
      assert(caught.getMessage == "Invalid alpha-2 code 'XXX' for ISOCountry")
    }

    "return ParseException when numeric code does not exist" in {
      val caught = intercept[ParseException] {
        ISOCountry(999)
      }
      assert(caught.getMessage.indexOf("-1") == -1)
      assert(caught.getMessage == "Invalid numeric code '999' for ISOCountry")
    }
  }
}
