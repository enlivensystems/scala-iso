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

object ISOContinent extends Enum {

  sealed class EnumVal(val value: String, val name: String) extends Value

  type ISOContinent = EnumVal

  // format: OFF
  val AFRICA        = new ISOContinent("AF", "Africa")
  val ANTARCTICA    = new ISOContinent("AN", "Antarctica")
  val ASIA          = new ISOContinent("AS", "Asia")
  val EUROPE        = new ISOContinent("EU", "Europe")
  val NORTH_AMERICA = new ISOContinent("NA", "North America")
  val OCEANIA       = new ISOContinent("OC", "Oceania")
  val SOUTH_AMERICA = new ISOContinent("SA", "South America")
  // format: ON

  /**
    * Retrieves ISOContinent based on alpha-2 continent code.
    * https://www.iso.org/obp/ui/#search
    *
    * @param continentCode Country code, ie. NA, AF
    * @return Option[ISOContinent]
    */
  def apply(continentCode: String): ISOContinent =
    ISOContinent.values.find(continentCode == _.toString) match {
      case Some(continent) => continent
      case _ =>
        throw new ParseException(s"Invalid value '$continentCode' for ISOContinent")
    }

  /**
    * Retrieves Option[ISOContinent] based on alpha-2 continent code.
    * https://www.iso.org/obp/ui/#search
    *
    * @param continentCode Country code, ie. NA, AF
    * @return Option[ISOContinent]
    */
  def from(continentCode: String): Option[ISOContinent] =
    ISOContinent.values.find(continentCode == _.toString)

}
