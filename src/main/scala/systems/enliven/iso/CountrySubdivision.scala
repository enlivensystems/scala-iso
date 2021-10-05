package systems.enliven.iso

import systems.enliven.iso.ISOCountry.ISOCountry

/**
  * ISO 3166-2 is part of the ISO 3166 standard
  * and defines codes for identifying the principal subdivisions
  * (e.g., provinces or states) of all countries coded in ISO 3166-1.
  * The official name of the standard is
  * "Codes for the representation of names of countries and their subdivisions – Part 2: Country subdivision code."
  */
trait CountrySubdivision extends Enum {

  sealed class EnumVal(val country: ISOCountry, val subdivisionName: String, val value: String) extends Value

  type ISOCountrySubdivision = EnumVal
}

object ISOCountrySubdivision
    extends ISOCountrySubdivisionAfrica
    with ISOCountrySubdivisionAsia
    with ISOCountrySubdivisionEurope
    with ISOCountrySubdivisionNorthAmerica
    with ISOCountrySubdivisionSouthAmerica
    with ISOCountrySubdivisionOceania {

  /**
    * Retrieves ISOCountrySubdivision based on 3166-2 xx-xx code.
    * https://www.iso.org/obp/ui/#search
    *
    * @param subdivisionCode Country code, ie. US-NY, US-AL
    * @return ISOCountrySubdivision
    */
  def apply(subdivisionCode: String): ISOCountrySubdivision =
    ISOCountrySubdivision.values.find(subdivisionCode == _.toString) match {
      case Some(country) ⇒ country
      case None ⇒ throw new ParseException(s"Invalid 3166-2 code '$subdivisionCode' for ISOCountrySubdivision")
    }

  /**
    * Retrieves Option[ISOCountrySubdivision] based on 3166-2 xx-xx code.
    * https://www.iso.org/obp/ui/#search
    *
    * @param subdivisionCode Country code, ie. US-NY, US-AL
    * @return Option[ISOCountrySubdivision]
    */
  def from(subdivisionCode: String): Option[ISOCountrySubdivision] =
    ISOCountrySubdivision.values.find(subdivisionCode == _.toString)

  /**
    * Retrieves Seq[ISOCountrySubdivision] based on xx-xx code.
    * https://www.iso.org/obp/ui/#search
    *
    * @param country ie. ISOCountry.JAPAN, ISOCountry.CANADA
    * @return Option[ISOCountrySubdivision]
    */
  def fromCountry(country: ISOCountry): Vector[ISOCountrySubdivision] =
    ISOCountrySubdivision.values.filter(_.country == country)
}
