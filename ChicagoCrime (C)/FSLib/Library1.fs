module FSAnalysis

#light

open System
open FSharp.Charting
open FSharp.Charting.ChartTypes
open System.Drawing
open System.Windows.Forms

//
// Parse one line of CSV data:
//
//   Date,IUCR,Arrest,Domestic,Beat,District,Ward,Community,Year
//   09/03/2015 11:57:00 PM,0820,true,false,0835,008,18,66,2015
//   ...
//
// Returns back a tuple with most of the information:
//
//   (date, iucr, arrested, domestic, community, year)
//
// as string*string*bool*bool*int*int.
//
let private ParseOneCrime (line : string) = 
  let elements = line.Split(',')
  let date = elements.[0]
  let iucr = elements.[1]
  let arrested = Convert.ToBoolean(elements.[2])
  let domestic = Convert.ToBoolean(elements.[3])
  let community = Convert.ToInt32(elements.[elements.Length - 2])
  let year = Convert.ToInt32(elements.[elements.Length - 1])
  (date, iucr, arrested, domestic, community, year)


// 
// Parse file of crime data, where the format of each line 
// is discussed above; returns back a list of tuples of the
// form shown above.
//
// NOTE: the "|>" means pipe the data from one function to
// the next.  The code below is equivalent to letting a var
// hold the value and then using that var in the next line:
//
//  let LINES  = System.IO.File.ReadLines(filename)
//  let DATA   = Seq.skip 1 LINES
//  let CRIMES = Seq.map ParseOneCrime DATA
//  Seq.toList CRIMES
//
let private ParseCrimeData filename = 
  System.IO.File.ReadLines(filename)
  |> Seq.skip 1  // skip header row:
  |> Seq.map ParseOneCrime
  |> Seq.toList


//
// Given a list of crime tuples, returns a count of how many 
// crimes were reported for the given year:
//
let private CrimesThisYear crimes crimeyear = 
  let crimes2 = List.filter (fun (date, iucr, arrested, domestic, community, year) -> year = crimeyear) crimes
  let numCrimes = List.length crimes2
  numCrimes

//
// CrimesByYear:
//
// Given a CSV file of crime data, analyzes # of crimes by year, 
// returning a chart that can be displayed in a Windows desktop
// app:
//
let CrimesByYear(filename) = 
  //
  // debugging:  print filename, which appears in Visual Studio's Output window
  //
  printfn "Calling CrimesByYear: %A" filename
  //
  let crimes = ParseCrimeData filename
  //
  let (_, _, _, _, _, minYear) = List.minBy (fun (date, iucr, arrested, domestic, community, year) -> year) crimes
  let (_, _, _, _, _, maxYear) = List.maxBy (fun (date, iucr, arrested, domestic, community, year) -> year) crimes
  //
  let range  = [minYear .. maxYear]
  let counts = List.map (fun year -> CrimesThisYear crimes year) range
  let countsByYear = List.map2 (fun year count -> (year, count)) range counts
  //
  // debugging: see Visual Studio's Output window (may need to scroll up?)
  //
  printfn "Counts: %A" counts
  printfn "Counts by Year: %A" countsByYear
  //
  // plot:
  //
  let myChart = 
    Chart.Line(countsByYear, Name="Total # of Crimes")
  let myChart2 = 
    myChart.WithTitle(filename).WithLegend();
  let myChartControl = 
    new ChartControl(myChart2, Dock=DockStyle.Fill)
  //
  // return back the chart for display:
  //
  myChartControl


//let private checkBool x =
 // x = true

let private ParseArrestData filename = 
  System.IO.File.ReadLines(filename)
  |> Seq.skip 1  // skip header row:
  |> Seq.map ParseOneCrime
  |> Seq.toList
  

//
// Given a list of crime tuples, returns a count of how many 
// crimes were reported for the given year:
//
let private ArrestsThisYear arrests arrestyear = 
  let arrests2 = List.filter (fun (date, iucr, arrested, domestic, community, year) -> year = arrestyear && arrested = true) arrests
  //let arrests3 = List.filter (fun x -> checkBool x) arrests2
  let numArrests = List.length arrests2
  numArrests


//
// Arrest Percentage: 
// 
// Given a CSV file of crime data, analyzes # of crimes by year by the number of arrests made per year,  
// returning a chart that can be displayed in a Windows desktop
// app:
//
let ArrestPercentage(filename) = 
  //
  // debugging:  print filename, which appears in Visual Studio's Output window
  //
  printfn "Calling Total Crimes Per Year vs. Number of arrests er year: %A" filename
  //
  
  let crimes = ParseCrimeData filename
  let arrests = ParseArrestData filename
  //
  let (_, _, _, _, _, minYear) = List.minBy (fun (date, iucr, arrested, domestic, community, year) -> year) arrests
  let (_, _, _, _, _, maxYear) = List.maxBy (fun (date, iucr, arrested, domestic, community, year) -> year) arrests
  //
  let range  = [minYear .. maxYear]
  let counts = List.map (fun year -> CrimesThisYear crimes year) range
  let counts2 = List.map (fun year -> ArrestsThisYear arrests year) range
  let countsByYear = List.map2 (fun year count -> (year, count)) range counts
  let countsByYear2 = List.map2 (fun year count -> (year, count)) range counts2


  //
  // debugging: see Visual Studio's Output window (may need to scroll up?)
  //
  printfn "Arrest Counts: %A" counts2
  printfn "Arrests by Year: %A" countsByYear2

  //
  // plot:
  //
  let myChart = 
    Chart.Combine([
                  Chart.Line(countsByYear, Name="Total # of Crimes")
                  Chart.Line(countsByYear2, Name="# of Arrests")
                  ])
  let myChart2 = 
    myChart.WithTitle(filename).WithLegend();
  let myChartControl = 
    new ChartControl(myChart2, Dock=DockStyle.Fill)
  //
  // return back the chart for display:
  //
  myChartControl


let parseCode (s : string) =
  let data = s.Split(',')
  let code = data.[0]
  (code, data.[1] + ": " + data.[2])


let parseCodeInLine filename = 
  System.IO.File.ReadLines(filename)
  |> Seq.skip 1 //skip header row:
  |> Seq.map parseCode
  |> Seq.toList
  
let filterByCrimeCode crimes code crimeyear =
  let code_list = List.filter (fun (date, iucr, arrested, domestic, community, year) -> iucr = code && year = crimeyear) crimes
  let numCrimes = List.length code_list
  numCrimes
  
let checker_1 x y =
  if x = y then "code found"
  else "code not found"
  
let catchCodeErr(filename) cCode =
  let cCodes = parseCodeInLine filename
  match List.tryFind (fun (x,y) -> x = cCode ) cCodes with
  | Some (iucr, msg) -> msg
  | None -> "Code Invalid"

let searchByCrimeCode(filename) code =
  //
  // debugging:  print filename, which appears in Visual Studio's Output window
  //
  printfn "Calling Total Crimes by Crime Code %A" filename
  //
  
  let crimes = ParseCrimeData filename
  //let arrests = ParseArrestData filename
  //
  let (_, _, _, _, _, minYear) = List.minBy (fun (date, iucr, arrested, domestic, community, year) -> year) crimes
  let (_, _, _, _, _, maxYear) = List.maxBy (fun (date, iucr, arrested, domestic, community, year) -> year) crimes
  //
  let range  = [minYear .. maxYear]
  let counts = List.map (fun year -> CrimesThisYear crimes year) range
  let counts2 = List.map (fun year -> filterByCrimeCode crimes code year) range
  let countsByYear = List.map2 (fun year count -> (year, count)) range counts
  let countsByYear2 = List.map2 (fun year count -> (year, count)) range counts2


  //
  // debugging: see Visual Studio's Output window (may need to scroll up?)
  //
  //printfn "Arrest Counts: %A" counts2
  //printfn "Arrests by Year: %A" countsByYear2

  //
  // plot:
  //
  let myChart = 
    Chart.Combine([
                  Chart.Line(countsByYear, Name="Total # of Crimes")
                  Chart.Line(countsByYear2, Name= catchCodeErr("IUCR-codes.csv") code)
                  ])
  let myChart2 = 
    myChart.WithTitle(filename).WithLegend();
  let myChartControl = 
    new ChartControl(myChart2, Dock=DockStyle.Fill)
  //
  // return back the chart for display:
  //
  myChartControl


let private parseArea (s : string) = 
  let data = s.Split(',')
  let area = Convert.ToInt32(data.[0])
  let msg = data.[1]
  (area, msg)

let private parseAreaByLine filename = 
  System.IO.File.ReadLines(filename)
  |> Seq.skip 1  // skip header row:
  |> Seq.map parseArea
  |> Seq.toList

let crimesByArea crimes code crimeyear =
  if code <> 0 then
    let filtered = List.filter (fun (date, iucr, arrested, domestic, community, year) -> community = code && year = crimeyear) crimes
    let numCrimes = List.length filtered
    numCrimes
  else 
    0

let catchAreaCodeErr(filename) name = 
  let aCodes = parseAreaByLine filename
  match List.tryFind (fun (x, y) -> y = name) aCodes with
    | Some (code, msg) -> msg
    | None -> "0"

let private catchAreaCodeErr_2(filename) name = 
  let aCodes = parseAreaByLine filename
  match List.tryFind (fun (x, y) -> y = name) aCodes with
    | Some (code, msg) -> code
    | None -> 0

let searchByAreaCode(filename) code =
  //
  // debugging:  print filename, which appears in Visual Studio's Output window
  //
  printfn "Calling Total Crimes by Area Code %A" filename
  //
  
  let crimes = ParseCrimeData filename
  //let arrests = ParseArrestData filename
  //
  let (_, _, _, _, _, minYear) = List.minBy (fun (date, iucr, arrested, domestic, community, year) -> year) crimes
  let (_, _, _, _, _, maxYear) = List.maxBy (fun (date, iucr, arrested, domestic, community, year) -> year) crimes
  //
  let range  = [minYear .. maxYear]
  let name = catchAreaCodeErr_2("Areas.csv") code
  let counts = List.map (fun year -> CrimesThisYear crimes year) range
  let counts2 = List.map (fun year -> crimesByArea crimes name year) range
  let countsByYear = List.map2 (fun year count -> (year, count)) range counts
  let countsByYear2 = List.map2 (fun year count -> (year, count)) range counts2


  //
  // debugging: see Visual Studio's Output window (may need to scroll up?)
  //
  //printfn "Arrest Counts: %A" counts2
  //printfn "Arrests by Year: %A" countsByYear2

  //
  // plot:
  //
  let myChart = 
    Chart.Combine([
                  Chart.Line(countsByYear, Name="Total # of Crimes")
                  Chart.Line(countsByYear2, Name= catchAreaCodeErr("Areas.csv") code)
                  ])
  let myChart2 = 
    myChart.WithTitle(filename).WithLegend();
  let myChartControl = 
    new ChartControl(myChart2, Dock=DockStyle.Fill)
  //
  // return back the chart for display:
  //
  myChartControl

let private filterByChicagoLandArea crimes code = 
  let cCodes = List.filter (fun (date, iucr, arrested, domestic, community, year) -> community = code) crimes
  let numCrimes = List.length cCodes
  numCrimes


let crimesInChicago(filename) = 
  //
  // debugging:  print filename, which appears in Visual Studio's Output window
  //
  printfn "Calling Total Crimes by Chicago Area %A" filename
  //
  
  let crimes = ParseCrimeData filename
  //let arrests = ParseArrestData filename
  //
  let (_, _, _, _, minComm, _) = List.minBy (fun (date, iucr, arrested, domestic, community, year) -> community) crimes
  let (_, _, _, _, maxComm, _) = List.maxBy (fun (date, iucr, arrested, domestic, community, year) -> community) crimes
  //
  let range  = [minComm .. maxComm]
  let crimesInChicago = List.map (fun x -> filterByChicagoLandArea crimes x) range
  let crimesByArea = List.map2 (fun x y -> (x, y)) range crimesInChicago

  //
  // debugging: see Visual Studio's Output window (may need to scroll up?)
  //
  //printfn "Arrest Counts: %A" counts2
  //printfn "Arrests by Year: %A" countsByYear2

  //
  // plot:
  //
  //
  
  let myChart = 
    Chart.Line(crimesByArea, Name= "Total Crimes in Chicago Land Area")                
  let myChart2 = 
    myChart.WithTitle(filename).WithLegend();
  let myChartControl = 
    new ChartControl(myChart2, Dock=DockStyle.Fill)

  //
  // return back the chart for display:
  //
  myChartControl

