/*
 * MIT License
 *
 * Copyright (c) 2021 Enliven Systems Kft.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

  sealed class EnumVal(val country: ISOCountry, val subdivisionName: String, val value: String)
   extends Value

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
      case Some(country) => country
      case None => throw new ParseException(
          s"Invalid 3166-2 code '$subdivisionCode' for ISOCountrySubdivision"
        )
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
