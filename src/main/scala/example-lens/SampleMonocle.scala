package examplelens

import monocle.macros.GenLens
import monocle.syntax.all._
import shapeless.test.typed

object SampleMonocle {
  println("run SampleMonocle")

  val user = User.default

  // Read
  val userName = user.focus(_.name).get
  typed[String](userName)
  assert(userName == "Sato")

  val city = user.focus(_.company.address.city).get
  typed[City](city)
  assert(city.name == "Tokyo")

  // Write
  val user2 = user.focus(_.name).replace("Bob")
  assert(user2.name == "Bob")

  val user3 = user.focus(_.address.city.name).replace("India")
  assert(user3.address.city.name == "India")

  // Lensを作る事もできる
  val userAddressCityCountryLens = GenLens[User](_.address.city.country)

  val country = userAddressCityCountryLens.get(user)
  typed[Country](country)
  assert(country.name == "Nippon")
}
