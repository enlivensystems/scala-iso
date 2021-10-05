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
