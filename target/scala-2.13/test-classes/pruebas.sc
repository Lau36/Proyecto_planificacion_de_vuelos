import planificacion_vuelos._
import org.scalameter._

val standardConfig = config (
  Key.exec.minWarmupRuns := 20,
  Key.exec.minWarmupRuns := 40,
  Key.exec.benchRuns := 25,
  Key.verbose := false
) withWarmer(Warmer.Default())

val vuelo1 = Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0) //Vuelo("AA", 580, "SFO", 12, 25, "ORD", 11, 5, 1)
val vuelo2 = Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0)
val vuelo3 = Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0)


//TIEMPOS DE LAS FUNCIONES
val itiSeqTiempo = standardConfig measure {itinerario(vuelo1.Org,vuelo1.Dst)}
val itiParTiempo = standardConfig measure {itinerariosPar(vuelo1.Org,vuelo1.Dst)}

val tiempoMenorTiempoTotal = standardConfig measure{itenerariosMenorTiempoTotal(vuelo1.Org, vuelo1.Dst)}
val tiempoMenorTiempoTotalPar = standardConfig measure{itenerariosMenorTiempoTotalPar(vuelo1.Org, vuelo1.Dst)}

val tiempoMenoresEscalasSeq = standardConfig measure{itinierariosMenorEscalas(vuelo1.Org, vuelo1.Dst)}
val tiempoMenoresEscalasPar = standardConfig measure{itinierariosMenorEscalasPar(vuelo1.Org, vuelo1.Dst)}

val tiempoMenorTiempoSeq = standardConfig measure{itenerariosMenorTiempo(vuelo1.Org, vuelo1.Dst)}
val tiempoMenorTiempoPar = standardConfig measure{itenerariosMenorTiempoPar(vuelo1.Org,vuelo1.Dst)}

val tiempoItinerarioSalidaSeq = standardConfig measure{itinerariosSalidaSeq(vuelo1.Org, vuelo1.Dst, vuelo1.HL, vuelo1.ML)}
val tiempoItinerarioSalidaPar = standardConfig measure{itinerariosSalidaPar(vuelo1.Org, vuelo1.Dst, vuelo1.HL, vuelo1.ML)}



//VALORES DE LAS FUNCIONES

//VUELO 1
/*
val itinerarioSeq = itinerario(vuelo1.Org,vuelo1.Dst)
val itinerarioPar = itinerariosPar(vuelo1.Org,vuelo1.Dst)

val menorTiempoTotalSeq = itenerariosMenorTiempoTotal(vuelo1.Org, vuelo1.Dst)
val menorTiempoTotalPar = itenerariosMenorTiempoTotalPar(vuelo1.Org, vuelo1.Dst)

val menoresEscalasSeq = itinierariosMenorEscalas(vuelo1.Org, vuelo1.Dst)
val menoreEscalasPar = itinierariosMenorEscalasPar(vuelo1.Org, vuelo1.Dst)

val menorTiempoSeq = itenerariosMenorTiempo(vuelo1.Org, vuelo1.Dst)
val menorTiempoPar = itenerariosMenorTiempoPar(vuelo1.Org,vuelo1.Dst)

val itinerarioSalidaSeq = itinerariosSalidaSeq(vuelo1.Org, vuelo1.Dst, 13, 10)
val itinerarioSalidaPar = itinerariosSalidaPar(vuelo1.Org, vuelo1.Dst, 13, 10)*/

/*
//VUELO 2
val itinerarioSeq = itinerario(vuelo2.Org,vuelo2.Dst)

val itinerarioPar = itinerariosPar(vuelo2.Org,vuelo2.Dst)



val menorTiempoTotalSeq = itenerariosMenorTiempoTotal(vuelo2.Org,vuelo2.Dst)

val menorTiempoTotalPar = itenerariosMenorTiempoTotalPar(vuelo2.Org,vuelo2.Dst)




val menoresEscalasSeq = itinierariosMenorEscalas(vuelo2.Org,vuelo2.Dst)

val menoreEscalasPar = itinierariosMenorEscalasPar(vuelo2.Org,vuelo2.Dst)



val menorTiempoSeq = itenerariosMenorTiempo(vuelo2.Org,vuelo2.Dst)

val menorTiempoPar = itenerariosMenorTiempoPar(vuelo2.Org,vuelo2.Dst)



val itinerarioSalidaSeq = itinerariosSalidaSeq(vuelo2.Org,vuelo2.Dst,20, 3)

val itinerarioSalidaPar = itinerariosSalidaPar(vuelo2.Org,vuelo2.Dst, 20, 3)
*/

//VUELO 3
val itinerarioSeq = itinerario(vuelo3.Org,vuelo3.Dst)
val itinerarioPar = itinerariosPar(vuelo3.Org,vuelo3.Dst)

val menorTiempoTotalSeq = itenerariosMenorTiempoTotal(vuelo3.Org,vuelo3.Dst)
val menorTiempoTotalPar = itenerariosMenorTiempoTotalPar(vuelo3.Org,vuelo3.Dst)

val menoresEscalasSeq = itinierariosMenorEscalas(vuelo3.Org,vuelo3.Dst)
val menoreEscalasPar = itinierariosMenorEscalasPar(vuelo3.Org,vuelo3.Dst)

val menorTiempoSeq = itenerariosMenorTiempo(vuelo3.Org,vuelo3.Dst)
val menorTiempoPar = itenerariosMenorTiempoPar(vuelo3.Org,vuelo3.Dst)

val itinerarioSalidaSeq = itinerariosSalidaSeq(vuelo3.Org,vuelo3.Dst, 13, 10)
val itinerarioSalidaPar = itinerariosSalidaPar(vuelo3.Org,vuelo3.Dst, 13, 10)














