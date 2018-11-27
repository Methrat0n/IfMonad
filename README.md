# IfMonad

The If Monad allows easier composition of conditions and helps build more complex expressions. 
It may help when validating data, if you need to test your values over multiple rules.
```scala
val director = "Steven Spielberg"
for {
  themes <- 
    If(director.contains(" "),
      themeQuery.getByDirectorFullName(director), 
    Else(
      nameQuery.getByDirectorSimpleName(director)
    ))
  bestMovie <- 
    If(themes.contains("Sci Fi"),
      SciFiQuery.takeFirstFromDirector(director),
    Else(
      MovieQuery.takeOneByTheme(themes.head)
    ))
} yield bestMovie 
```

This same selection could have been done using Either or Option but is clearer in this form.
Later, if we want to return to our usual Monads, we can use the toEither helper.
