package io.github.methrat0n.ifMonad

sealed trait IfElse[+If, +Else] {
  def isIf: Boolean
  def isElse: Boolean
  def flatMap[A, B](mapper: If => IfElse[A, B]): IfElse[A, B]
  def map[A](mapper: If => A): IfElse[A, Else]
  def elseMap[A](mapper: Else => A): IfElse[If, A]
  def elseFlatMap[A, B](mapper: Else => IfElse[A, B]): IfElse[A, B]
  def foreach[U](iterater: If => U): Unit
  def elseForeach[U](iterater: Else => U): Unit
}

object IfElse {
  def pure[A](block: => A): If[A] = new If(block)

  final class If[A](block: => A) extends IfElse[A, Nothing] {
    override def isIf: Boolean = true
    override def isElse: Boolean = false
    override def map[B](mapper: A => B): IfElse[B, Nothing] = new If(mapper(block))
    override def flatMap[B, C](mapper: A => IfElse[B, C]): IfElse[B, C] = mapper(block)
    override def elseMap[B](mapper: Nothing => B): IfElse[A, B] = this.asInstanceOf[IfElse[A, B]]
    override def elseFlatMap[C, B](mapper: Nothing => IfElse[C, B]): IfElse[C, B] = this.asInstanceOf[IfElse[C, B]]
    override def foreach[U](iterater: A => U): Unit = iterater(block)
    override def elseForeach[U](iterater: Nothing => U): Unit = ()
  }

  final class Else[A](block: => Option[A]) extends IfElse[Nothing, A] {
    override def isIf: Boolean = false
    override def isElse: Boolean = true
    override def map[B](mapper: Nothing => B): IfElse[B, A] = this.asInstanceOf[IfElse[B, A]]
    override def flatMap[B, C](mapper: Nothing => IfElse[B, C]): IfElse[B, C] = this.asInstanceOf[IfElse[B, C]]
    override def elseMap[B](mapper: A => B): IfElse[Nothing, B] = new Else(block.map(mapper))
    override def elseFlatMap[C, B](mapper: A => IfElse[C, B]): IfElse[C, B] = block.map(mapper).getOrElse(Else(None).asInstanceOf[IfElse[C, B]])
    override def foreach[U](iterater: Nothing => U): Unit = ()
    override def elseForeach[U](iterater: A => U): Unit = block.map(iterater).getOrElse(())
  }

  object If {
    def apply[A](condition: Boolean, block: => A): IfElse[A, Unit] = if(condition) new If(block) else new Else(None)
    def apply[A, B](condition: Boolean, block: => A, els: Else[B]): IfElse[A, B] = if(condition) new If(block) else els
  }

  object Else {
    def apply[A](block: => A): Else[A] = new Else(Some(block))
  }

}