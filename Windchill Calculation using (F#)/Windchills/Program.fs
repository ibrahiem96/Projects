//
// F# main method/module
//
//Ibrahiem Mohammad
//University of Illinois
//CS341, Spring 2016
//HW 4
//

#light

[<EntryPoint>]
let main argv = 

    printf "Please enter a temperature (°F): "
    let input = System.Console.ReadLine()
    let T = System.Convert.ToDouble(input)

    printf "Please enter a max wind speed (MPH): "
    let input = System.Console.ReadLine()
    let MaxWS = System.Convert.ToDouble(input) 

    //notice: if you write out entire list, it gives an error saying WS is not a float list. why?
    let WS = [1.0 .. MaxWS]
    let windchills = List.map (fun W -> Function.windchill T W) WS

    printfn ""
    printfn "Windchills: "
    printfn "%A" windchills
    printfn ""

    0 // return 0 => success
