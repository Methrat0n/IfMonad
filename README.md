# IfMonad

###### because keywords are way too complicated

You know how we used to do condition the old way ?

```scala
val field = "prenom"

if(field == "prenom")
  "guillermo"
else
  "del toro"
```

That look very complex to me, lots of concepts I have to learn. But now, we can write it in a way simpler syntax :

```scala
val field = "prenom"
val name =
  If(field == "prenom",
    "guillermo",
  Else(
    "del toro"))
```

Which is _waaaaay_ better. We just use a Monad, a concept every one learn very early.

So what can we do with this new cool Monad ?

 - We can map over or conditions

```scala
val movie = name.map(firstName => 
  If(firstName == "steven", 
    "E.T")
)

ifTest.elseMap(firstName => 
  If(firstName == "steven", 
    "E.T")
)
```

 - FlatMap to compose conditions.


```scala
val author = movie.flatMap(movie => 
  If(movie == "HellBoy", 
    "Mike Mignola")
)
```

 - We can type our function to expose our internal implementation and show our callers we use _if_ inside. Maybe they'd want to check if our _if_ dont fail ?
 
 ```scala
def someTest(...): IfElse[A, B]
 ```
 
 <hr />
 
 If you want to participate to the simplification of the scala langage and make everything a Monad your help will be appreciated.
 
 Next things to do :
 
  - While Monad to compose iterations
  - Val Monad to compose value
  - Empty Monad to compose the abscence of values