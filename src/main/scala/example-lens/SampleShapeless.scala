package examplelens

import shapeless._
import shapeless.test.typed

object SampleShapeless {
  println("run SampleShapeless")

  val user = User.default

  // Create Lens
  val userCompanyLens = lens[User].company
  val userAddressLens = lens[User].address
  val companyAddressLens = lens[Company].address
  val addressCityLens = lens[Address].city
  val cityCountryLens = lens[City].country
  val countryCodeLens = lens[Country].code

  val userCompanyAddressCityLens =
    addressCityLens compose companyAddressLens compose userCompanyLens
  // こうも書ける
  val userCompanyAddressCityLens2 = lens[User].company.address.city

  // Read
  val company = userCompanyLens.get(user)
  typed[Company](company)
  assert(company.name == "FooBarBaz")

  val city1 = userCompanyAddressCityLens.get(user)
  typed[City](city1)
  assert(city1.name == "Tokyo")

  val city2 = userCompanyAddressCityLens2.get(user)
  typed[City](city1)
  assert(city1.name == "Tokyo")

  // Write
  val user2 = userCompanyLens.name.set(user)("FizzBuzz")
  assert(user2.company.name == "FizzBuzz")

  // 複数フィールドを同時に扱う
  val countryNameAndCode =
    (lens[Country].name ~ lens[Country].code) compose
      addressCityLens.country compose
      userAddressLens

  // Read
  val cityNameAndCountry = countryNameAndCode.get(user)
  typed[(String, String)](cityNameAndCountry)
  assert(cityNameAndCountry == ("Nippon", "JP"))

  // Write
  val user3 =
    countryNameAndCode.set(user)(("United States", "US"))
  typed[User](user3)
  assert(
    user3.address.city.country == Country(name = "United States", code = "US")
  )

  // 取得失敗を考慮してOptionを返すようにしたい場合はLensではなくPrismを使う
}
