import planificacion_vuelos._

val vuelo1 = Vuelo("DL", 593, "ATL", 19, 3, "DFW", 11, 17, 1)
val org1 = obtenerOrigen(vuelo1)
val dst1 = obtenerDestino(vuelo1)

itinerarios(org1,dst1)

menorTiempoVuelo(org1,dst1)

itinerariosMenorCambio(org1,dst1)


