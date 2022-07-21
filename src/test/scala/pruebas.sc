import planificacion_vuelos._

val vuelo1 = Vuelo("AS", 239, "LAX", 16, 10, "SEA", 18, 40, 0)

val org1 = obtenerOrigen(vuelo1)
val dst1 = obtenerDestino(vuelo1)
itineracios(org1,dst1)