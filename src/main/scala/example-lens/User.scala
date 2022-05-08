package examplelens

final case class Country(name: String, code: String)
final case class City(name: String, country: Country)
final case class Address(
    number: Int,
    street: String,
    city: City
)
final case class Company(name: String, address: Address)

final case class User(
    name: String,
    company: Company,
    address: Address
)

object User {
  val default = User(
    name = "Sato",
    company = Company(
      name = "FooBarBaz",
      address = Address(
        number = 1,
        street = "foo",
        city = City(
          name = "Tokyo",
          country = Country(name = "Japan", code = "JP")
        )
      )
    ),
    address = Address(
      number = 2,
      street = "bar",
      city = City(
        name = "Edo",
        country = Country(name = "Nippon", code = "JP")
      )
    )
  )
}
