package sky.aether
package data

class NonEmptyList[T](val head:T, val tail:List[T]) {
  def this(head:T) = this(head, Nil)
}
