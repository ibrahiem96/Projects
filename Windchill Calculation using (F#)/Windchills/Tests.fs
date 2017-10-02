//
// F# unit testing to check if windchill program is working as it is designed
//
//Ibrahiem Mohammad
//University of Illinois
//CS341, Spring 2016
//HW 4
//


#light

module Tests

open NUnit.Framework

[<TestFixture>]
type Tests() = 
  
   [<Test>]
   member this.Windchill_Test_01() =
    let R = Function.windchill 0.0 25.0
    let Correct = -24.05009
    Assert.IsTrue(System.Math.Abs(R-Correct) < 0.0001)

   [<Test>]
   member this.Windchill_Test_02() =
     let R = Function.windchill 2.0 16.0
     let Correct = -17.39218
     Assert.IsTrue(System.Math.Abs(R-Correct) < 0.0001)

   [<Test>]
    member this.Windchill_Test03() =
      let R = Function.windchill 5.0 15.0
      let Correct = -13.04484
      Assert.IsTrue(System.Math.Abs(R-Correct) < 0.0001)