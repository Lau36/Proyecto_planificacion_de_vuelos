import planificacion_vuelos._

val vuelo1 = Vuelo("DL", 593, "ATL", 19, 3, "DFW", 11, 17, 1)

/*val itenAcom = itinerarios(org1,dst1)
//itenAcom.map((v:Vuelo)=> vueloConGMT(v))

vueloConGMT(vuelo1)
tiempoVuelo(vuelo2)

//menorTiempoVuelo(org1,dst1)

/*itinerariosMenorCambio(org1,dst1)*/

//obtenerGmt(org1)
pasarAgmt(vuelo1.HS,vuelo1.MS,obtenerGmt(org1))
pasarAgmt(vuelo1.HL,vuelo1.ML,obtenerGmt(dst1))

itenerariosMenorTiempoTotal(org1,dst1)
tiempoVuelo(Vuelo("DL",775,"ATL",14,38,"DFW",18,8,1))*/

itinerarios(vuelo1.Org,vuelo1.Dst)
genEscalas(vuelo1.Org,vuelo1.Dst)
itinerarios2(vuelo1.Org,vuelo1.Dst)




