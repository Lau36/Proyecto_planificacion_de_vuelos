import planificacion_vuelos._
import org.scalameter._

val standardConfig = config (
  Key.exec.minWarmupRuns := 20,
  Key.exec.minWarmupRuns := 40,
  Key.exec.benchRuns := 25,
  Key.verbose := false
) withWarmer(Warmer.Default())

val vuelo1 =  Vuelo("DL", 269, "ATL", 12, 0, "LAX", 13, 30, 0)

val itiSeqTiempo = standardConfig measure {itinerario(vuelo1.Org,vuelo1.Dst)}
val itiParTiempo = standardConfig measure {itinerariosPar(vuelo1.Org,vuelo1.Dst)}

itinerario(vuelo1.Org,vuelo1.Dst)
itinerariosPar(vuelo1.Org,vuelo1.Dst)










