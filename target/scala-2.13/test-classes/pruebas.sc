import planificacion_vuelos._

val vuelo1 = Vuelo("DL", 593, "ATL", 19, 3, "DFW", 11, 17, 1)


//itenAcom.map((v:Vuelo)=> vueloConGMT(v))



//menorTiempoVuelo(org1,dst1)

/*itinerariosMenorCambio(org1,dst1)*/

/*obtenerGmt(vuelo1.Org)
obtenerGmt(vuelo1.Dst)
pasarAgmt(vuelo1.HS,vuelo1.MS,obtenerGmt(vuelo1.Org))
pasarAgmt(vuelo1.HL,vuelo1.ML,obtenerGmt(vuelo1.Dst))

itenerariosMenorTiempoTotal(vuelo1.Org,vuelo1.Dst)
tiempoVuelo(Vuelo("DL",775,"ATL",14,38,"DFW",18,8,1))*/

itinerarioV(vuelo1.Org,vuelo1.Dst)
obtenerGmt(vuelo1.Org)
obtenerGmt(vuelo1.Dst)
tiempoVuelo(vuelo1)

itinerarioGMT(itinerarioV(vuelo1.Org,vuelo1.Dst).head)
itinerarioGMT(itinerarioV(vuelo1.Org,vuelo1.Dst)(1))
itinerarioGMT(itinerarioV(vuelo1.Org,vuelo1.Dst)(2))

val itiV = itinerarioV(vuelo1.Org,vuelo1.Dst)
val itiL = itinerarioL(vuelo1.Org,vuelo1.Dst)

itenerariosMenorTiempoTotal(vuelo1.Org,vuelo1.Dst)


//itinerarios2(vuelo1.Org,vuelo1.Dst)





