package systems.enliven.iso

//Slightly adapted from https://gist.github.com/d6y/376f1a4b178c343ff415
trait Enum {
  // DIY enum type
  import java.util.concurrent.atomic.AtomicReference
  // Concurrency paranoia

  type EnumVal <: Value // This is a type that needs to be found in the implementing class

  private val _values = new AtomicReference(Vector[EnumVal]()) // Stores our enum values

  // Adds an EnumVal to our storage, uses CCAS to make sure it's thread safe, returns the ordinal
  final private def addEnumVal(newVal: EnumVal): Int = {
    import _values.{compareAndSet, get}
    val oldVec = get
    val newVec = oldVec :+ newVal
    if ((get eq oldVec) && compareAndSet(oldVec, newVec))
      newVec.indexWhere(_ eq newVal)
    else addEnumVal(newVal)
  }

  def values: Vector[EnumVal] = _values.get

  // Here you can get all the enums that exist for this type

  // This is the trait that we need to extend our EnumVal type with, it does the book-keeping for us
  protected trait Value extends Ordered[Value] {
    self: EnumVal =>
    // Enforce that no one mixes in Value in a non-EnumVal type
    final val ordinal = addEnumVal(this) // Adds the EnumVal and returns the ordinal

    def compare(that: Value): Int = this.ordinal - that.ordinal

    def value: String // All enum values should have a value

    override def toString: String = value

    // And that name is used for the toString operation
    override def equals(other: Any): Boolean = this eq other.asInstanceOf[AnyRef]

    override def hashCode: Int = 31 * (this.getClass.## + value.## + ordinal)
  }

}

sealed private class EnumValueParseException(message: String) extends Exception(message)

final private class ParseException(message: String) extends EnumValueParseException(message)
