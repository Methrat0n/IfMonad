package io.github.methrat0n.ifMonad

object Example extends App {
  import io.github.methrat0n.ifMonad.IfElse._

  println("start")
  val field = "prenom"
  val ifTest =
    If(field == "prenom",
      "guillermo",
    Else(
      "del toro"))

  ifTest.foreach(println)
  ifTest.elseForeach(println)

  ifTest.flatMap(firstName =>
    If(firstName == "steven",
      "E.T",
    Else(
      "HellBoy"
    ))
  ).elseForeach(println)
  println("end")
}
