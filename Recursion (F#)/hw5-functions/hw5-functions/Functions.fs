#light

// 
// F# functions
// Ibrahiem Mohammad
// UIC
// CS341, Spring 2016
// HW 5
//

module Functions


let rec fixHelper LB UB L lYet = 
  match L with
  | [] -> lYet                                                            //base case
  | head :: tail when head < LB -> fixHelper LB UB tail (LB :: lYet)      //if elem is < LB change to LB
  | head :: tail when head > UB -> fixHelper LB UB tail (UB :: lYet)      //if elem is > UB change to UB
  | head :: tail -> fixHelper LB UB tail (head::lYet)                     //if neither, attach elem and move to next

//
// fix: checks if given list contains elements greater or less than given bounds, and replaces
// those respective elements with the bounds
//
let fix LB UB L =
  fixHelper LB UB L []

// 
// fix2: applies the function fix to each list within a set of lists
//
let fix2 LB UB LL= 
  List.map (fun x -> fix LB UB x) LL 


let rec reduceHelper F L last = 
  if L = [] then last                 //base case
  else                                //apply function F and recursive call
    let temp = F last L.Head                         
    reduceHelper F L.Tail temp                              

//
// reduce: reduces a list by applying function F to each element until only one element is left
//
let reduce F (L: 'a list) = 
  reduceHelper F L.Tail L.Head
    
//
// intAverage: get the average of a list of numbers
//
let rec intAverage L = 
  match L with
  | [] -> 0                                     //if empty avg = 0
  | _ -> (List.sum L)/List.length L             //else get avg of list

//
// averages: get the average of a list of exam scores and
// return a new list which pairs the student name and their average exam score
//
let averages SL LL = 
  let X = List.map (fun x -> intAverage x) LL             //obtains average of exam score
  List.zip SL X                                           //returns new list of tuples


let histHelper2 x l u =
  x >= l && x < u                                   //predicate to check if within bounds

let histHelper L L2 = 
  let list1 = List.filter(fun x -> histHelper2 x 0 10) L    //applies predicate
  let L_1 = List.length list1                               //stores number of values that meet predicate

  let list2 = List.filter(fun x -> histHelper2 x 10 20) L
  let L_2 = List.length list2

  let list3 = List.filter(fun x -> histHelper2 x 20 30) L
  let L_3 = List.length list3

  let list4 = List.filter(fun x -> histHelper2 x 30 40) L
  let L_4 = List.length list4

  let list5 = List.filter(fun x -> histHelper2 x 40 50) L
  let L_5 = List.length list5

  let list6 = List.filter(fun x -> histHelper2 x 50 60) L
  let L_6 = List.length list6

  let list7 = List.filter(fun x -> histHelper2 x 60 70) L
  let L_7 = List.length list7

  let list8 = List.filter(fun x -> histHelper2 x 70 80) L
  let L_8 = List.length list8

  let list9 = List.filter(fun x -> histHelper2 x 80 90) L
  let L_9 = List.length list9

  let list10 = List.filter(fun x -> histHelper2 x 90 100) L
  let L_10 = List.length list10
  
  L_1::L_2::L_3::L_4::L_5::L_6::L_7::L_8::L_9::L_10::L2    //returns list of 10 values

// 
// histogram: given a list of integer exam scores, this function returns a list of 10
// values that are each a number of how many scores are within each range
//
let histogram L = 
  histHelper L []
  
