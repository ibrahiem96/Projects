#light

module UnitTests

open NUnit.Framework
open Functions

let rec identical L1 L2 = 
  match L1, L2 with
  | [], [] -> true
  | [], _  -> false
  | _, []  -> false
  | e1::tl1, e2::tl2 -> (e1=e2) && (identical tl1 tl2)


[<TestFixture>]
type UnitTests() = 
  //
  // fix:
  //
  [<Test>]
  member this.fix_Test_01() = 
    let R = fix 0 100 []
    Assert.IsTrue( (R = []) )

  [<Test>]
  member this.fix_Test_02() = 
    let R = fix 0 100 [88;22;-3;0;1;100;101;-11;999]
    let C = [88;22;0;0;1;100;100;0;100]
    Assert.IsTrue( identical R C )

  [<Test>]
  member this.fix_Test_03() = 
    let R = fix "c" "w" ["a";"b";"c";"d";"e";"z";"y";"x";"w";"a";"z"]
    let C = ["c";"c";"c";"d";"e";"w";"w";"w";"w";"c";"w"]
    Assert.IsTrue( identical R C )

  [<Test>]
  member this.fix_Test_04() = 
    let L = [for i in 1..1000000 -> i]
    let R = fix 1 1000000 L
    Assert.IsTrue( identical R R )


  //
  // fix2:
  //
  [<Test>]
  member this.fix2_Test_01() = 
    let R = fix2 10 90 [[11;12;13]; [8;9;91;0]; [-999]; [100]]
    let C = [[11;12;13] ; [10;10;90;10]; [10]; [90]]
    Assert.IsTrue( identical R C )

  [<Test>]
  member this.fix2_Test_02() = 
    let R = fix2 32 212 [[80;-1;100] ; [0] ; [80;78;80;999]]
    let C = [[80;32;100] ; [32] ; [80;78;80;212]]
    Assert.IsTrue( identical R C )


  //
  // reduce:
  //
  [<Test>]
  member this.reduce_Test_01() = 
    let R = reduce (fun x y -> x*y) [1;2;9;10]
    Assert.IsTrue( (R = 180) )

  [<Test>]
  member this.reduce_Test_02() = 
    let R = reduce (fun x y -> x+y) [1;2;9;10]
    Assert.IsTrue( (R = 22) )

  [<Test>]
  member this.reduce_Test_03() =
    let R = reduce (fun x y -> x+y) [99]
    Assert.IsTrue( (R = 99) )
  
  [<Test>]
  member this.reduce_Test_04() = 
    let R = reduce (fun x y -> x+y)  [-99;99]
    Assert.IsTrue( (R = 0) )

  [<Test>]
  member this.reduce_Test_05() = 
    let R = reduce (fun x y -> x*y) [1;2;3;4;5]
    Assert.IsTrue( (R = 120) )

  [<Test>]
  member this.reduce_Test_06() = 
    let R = reduce (fun x y -> x+y) ["a";"b";"c";"d"]
    Assert.IsTrue( (R = "abcd") )

  [<Test>]
  member this.reduce_Test_07() = 
    let L = [for i in 1..1000000 -> 1]
    let R = reduce (fun x y -> x+y) L
    Assert.IsTrue( (R = 1000000) )

  //
  // intAverage:
  //
  [<Test>]
  member this.intAverage_Test_01() = 
    let R = intAverage [12345]
    Assert.IsTrue( (R = 12345) )

  [<Test>]
  member this.intAverage_Test_02() = 
    let R = intAverage [100;100;100;100;99] 
    Assert.IsTrue( (R = 99) )


  //
  // averages:
  //
  [<Test>]
  member this.averages_Test_01() = 
    let SL = ["das"; "jia"; "sean"]
    let LL = [[100;80;90]; [88;90;59]; [100;100;99]]
    let R  = averages SL LL 
    let C  = [("das",90); ("jia",79); ("sean",99)]
    Assert.IsTrue( identical R C )

  [<Test>]
  member this.averages_Test_02() = 
    let R = averages [] []
    Assert.IsTrue( (R = []) )

  //
  // histogram:
  //
  [<Test>]
  member this.histogram_Test_01() = 
    let L = [100;0;90;80;99;70;60;59;10;0;0;79]
    let H = histogram L 
    let C = [3;1;0;0;0;1;1;2;1;3]
    Assert.IsTrue( identical H C)

  [<Test>]
  member this.histogram_Test_02() = 
    let H = histogram []
    let C = [0;0;0;0;0;0;0;0;0;0]
    Assert.IsTrue( identical H C)

  [<Test>]
  member this.histogram_Test_03() = 
    let H = histogram [0..100]
    let C = [10;10;10;10;10;10;10;10;10;11]
    Assert.IsTrue( identical H C)
