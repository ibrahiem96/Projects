//
// F# program to compute windchill
//
//Ibrahiem Mohammad
//University of Illinois
//CS341, Spring 2016
//HW 4
//

#light

module Function


let windchill T W =
  //let T = 20.0
  let WC = 35.7 + 0.6*(T) - 35.7*System.Math.Pow(W, 0.16) + 0.43*(T)*System.Math.Pow(W, 0.16)
  WC