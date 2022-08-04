import planificacion_vuelos._
import org.scalameter._

val standardConfig = config (
  Key.exec.minWarmupRuns := 20,
  Key.exec.minWarmupRuns := 40,
  Key.exec.benchRuns := 25,
  Key.verbose := false
) withWarmer(Warmer.Default())

val vuelo = Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0)



//TIEMPOS DE LAS FUNCIONES
val itiSeqTiempo = standardConfig measure {itinerario(vuelo.Org,vuelo.Dst)}
val itiParTiempo = standardConfig measure {itinerariosPar(vuelo.Org,vuelo.Dst)}

val tiempoMenorTiempoTotal = standardConfig measure{itenerariosMenorTiempoTotal(vuelo.Org,vuelo.Dst)}
val tiempoMenorTiempoTotalPar = standardConfig measure{itenerariosMenorTiempoTotalPar(vuelo.Org,vuelo.Dst)}

val tiempoMenoresEscalasSeq = standardConfig measure{itinierariosMenorEscalas(vuelo.Org,vuelo.Dst)}
val tiempoMenoresEscalasPar = standardConfig measure{itinierariosMenorEscalasPar(vuelo.Org,vuelo.Dst)}

val tiempoMenorTiempoSeq = standardConfig measure{itenerariosMenorTiempo(vuelo.Org,vuelo.Dst)}
val tiempoMenorTiempoPar = standardConfig measure{itenerariosMenorTiempoPar(vuelo.Org,vuelo.Dst)}

val tiempoItinerarioSalidaSeq = standardConfig measure{itinerariosSalidaSeq(vuelo.Org,vuelo.Dst, 13, 10)}
val tiempoItinerarioSalidaPar = standardConfig measure{itinerariosSalidaPar(vuelo.Org,vuelo.Dst, 13, 10)}



//VALORES DE LAS FUNCIONES
val itinerarioSeq = itinerario(vuelo.Org,vuelo.Dst)
val itinerarioPar = itinerariosPar(vuelo.Org,vuelo.Dst)

val menorTiempoTotalSeq = itenerariosMenorTiempoTotal(vuelo.Org,vuelo.Dst)
val menorTiempoTotalPar = itenerariosMenorTiempoTotalPar(vuelo.Org,vuelo.Dst)

val menoresEscalasSeq = itinierariosMenorEscalas(vuelo.Org,vuelo.Dst)
val menoreEscalasPar = itinierariosMenorEscalasPar(vuelo.Org,vuelo.Dst)

val menorTiempoSeq = itenerariosMenorTiempo(vuelo.Org,vuelo.Dst)
val menorTiempoPar = itenerariosMenorTiempoPar(vuelo.Org,vuelo.Dst)

val itinerarioSalidaSeq = itinerariosSalidaSeq(vuelo.Org,vuelo.Dst, 13, 10)
val itinerarioSalidaPar = itinerariosSalidaPar(vuelo.Org,vuelo.Dst, 13, 10)
















