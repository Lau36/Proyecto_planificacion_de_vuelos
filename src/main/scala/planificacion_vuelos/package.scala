package object planificacion_vuelos {
  import scala.collection.{Map, Seq, mutable}
  import scala.util.Random
  import annotation.tailrec
  import scala.collection.parallel.{ParSeq, ParMap}
  import scala.collection.parallel.CollectionConverters._

    case class Aeropuerto(Cod:String,X:Int,Y:Int,GMT:Int)
    case class Vuelo(Aln:String, Num:Int, Org:String, HS:Int, MS:Int, Dst:String, HL:Int, ML:Int, Esc:Int)

    val aeropuertos=List(
      Aeropuerto("ABQ", 195, 275, -800),
      Aeropuerto("ATL", 470, 280, -600),
      Aeropuerto("BNA", 430, 240, -700),
      Aeropuerto("BOS", 590, 100, -600),
      Aeropuerto("DCA", 540, 180, -600),
      Aeropuerto("DEN", 215, 205, -800),
      Aeropuerto("DFW", 310, 305, -700),
      Aeropuerto("DTW", 445, 140, -600),
      Aeropuerto("HOU", 330, 355, -700),
      Aeropuerto("JFK", 565, 130, -600),
      Aeropuerto("LAX", 55, 270, -900),
      Aeropuerto("MIA", 535, 390, -600),
      Aeropuerto("MSP", 340, 115, -700),
      Aeropuerto("MSY", 405, 345, -700),
      Aeropuerto("ORD", 410, 155, -700),
      Aeropuerto("PHL", 550, 155, -600),
      Aeropuerto("PHX", 120, 290, -800),
      Aeropuerto("PVD", 595, 122, -600),
      Aeropuerto("RDU", 530, 230, -600),
      Aeropuerto("SEA", 55, 45, -900),
      Aeropuerto("SFO", 10, 190, -900),
      Aeropuerto("STL", 380, 210, -700),
      Aeropuerto("TPA", 500, 360, -600)
    )
    val vuelosB5= List(
      Vuelo("AS", 239, "LAX", 16, 10, "SEA", 18, 40, 0),
      Vuelo("AS", 394, "SEA", 6, 50, "ORD", 12, 28, 0),
      Vuelo("AS", 610, "SEA", 13, 40, "PHX", 17, 25, 0),
      Vuelo("AS", 612, "SEA", 10, 15, "PHX", 13, 55, 0),
      Vuelo("AS", 96, "SEA", 17, 0, "LAX", 19, 30, 0),
      Vuelo("AS", 91, "LAX", 7, 0, "SEA", 13, 5, 0),
      Vuelo("AS", 223, "LAX", 12, 0, "SEA", 14, 40, 0),
      Vuelo("AS", 22, "SEA", 7, 0, "LAX", 14, 0, 0),
      Vuelo("AS", 210, "SEA", 8, 0, "LAX", 10, 35, 0),
      Vuelo("CO", 530, "DEN", 16, 9, "MSY", 13, 51, 3),
      Vuelo("CO", 1708, "ABQ", 10, 26, "DEN", 11, 39, 0),
      Vuelo("CO", 280, "PHX", 10, 50, "DFW", 16, 7, 1),
      Vuelo("CO", 261, "BOS", 16, 45, "DEN", 10, 46, 1),
      Vuelo("CO", 891, "DCA", 14, 30, "SEA", 10, 20, 1),
      Vuelo("CO", 642, "ORD", 10, 10, "DFW", 14, 51, 1),
      Vuelo("CO", 255, "DEN", 20, 28, "SEA", 12, 14, 0),
      Vuelo("CO", 171, "DEN", 18, 30, "SEA", 10, 29, 0),
      Vuelo("CO", 629, "DCA", 17, 40, "SFO", 12, 12, 1),
      Vuelo("CO", 196, "SFO", 6, 30, "DEN", 15, 2, 0),
      Vuelo("CO", 780, "DEN", 16, 1, "PHL", 11, 40, 0),
      Vuelo("CO", 467, "PHL", 17, 40, "PHX", 12, 20, 1),
      Vuelo("CO", 578, "DEN", 16, 1, "BOS", 12, 57, 1),
      Vuelo("CO", 786, "DEN", 13, 4, "BOS", 19, 1, 0),
      Vuelo("CO", 249, "DEN", 18, 5, "PHX", 10, 10, 0),
      Vuelo("CO", 479, "DEN", 12, 0, "LAX", 13, 29, 0),
      Vuelo("CO", 390, "ORD", 9, 15, "BOS", 14, 11, 1),
      Vuelo("CO", 445, "DEN", 12, 5, "SFO", 13, 57, 0),
      Vuelo("CO", 514, "MIA", 12, 25, "ORD", 16, 26, 1),
      Vuelo("CO", 233, "DFW", 18, 45, "DEN", 19, 34, 0),
      Vuelo("CO", 562, "PHX", 8, 0, "DEN", 14, 4, 0),
      Vuelo("CO", 1184, "ABQ", 17, 25, "DEN", 18, 39, 0),
      Vuelo("CO", 840, "DEN", 10, 25, "DFW", 13, 20, 0),
      Vuelo("CO", 758, "SEA", 6, 35, "DCA", 17, 22, 1),
      Vuelo("CO", 598, "LAX", 15, 30, "ORD", 12, 46, 1),
      Vuelo("CO", 742, "DEN", 16, 14, "ATL", 11, 9, 0),
      Vuelo("CO", 174, "LAX", 8, 50, "ORD", 17, 22, 1),
      Vuelo("CO", 395, "DTW", 7, 0, "DEN", 11, 0, 0),
      Vuelo("CO", 818, "SFO", 8, 55, "DEN", 12, 25, 0),
      Vuelo("CO", 131, "DCA", 6, 45, "LAX", 13, 44, 2),
      Vuelo("CO", 846, "PHX", 14, 35, "ATL", 11, 46, 1)
    )


    //Encontrando rutas
    //vuelo(aln: aeoro, num: codVuelo, org: aeroOrig, hs: horaSale, ms:minutoSale, dst: aeroLlega, hl: horallega, ml:minutoLlega, ess: numEscalas )


    def obtenerOrigen(origen: Vuelo):String = {
      origen.Org
    }
    def obtenerDestino(destino: Vuelo):String = {
      destino.Dst
    }

  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos.
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */
  def itineracios(a1:String, a2:String): List[Vuelo] = {
      for {
        vuelos <- vuelosB5
        if(obtenerOrigen(vuelos)==a1 && obtenerDestino(vuelos)==a2)
      }yield vuelos

    }

  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos de forma paralela.
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */
  def itineraciosPar(a1:String, a2:String): List[Vuelo] = {
    val vuelos = for {
      vuelos <- vuelosB5.par
      if obtenerOrigen(vuelos)==a1 && obtenerDestino(vuelos)==a2
    }yield vuelos
    vuelos.toList

  }

  /**
   * Esta funcion nos ayudara a encontrar los itinerarios que cumplan una determinada ruta y a su vez
   * cuenten con el menor numero de escalas posibles.
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */
  def itineraciosMenorCambio(a1:String, a2:String): List[Vuelo] = {
    val vuelos = for {
      vuelos <- vuelosB5.par
      if (obtenerOrigen(vuelos) == a1 && obtenerDestino(vuelos) == a2)
    } yield vuelos

    vuelos.toList.sortBy((x: Vuelo) => x.Esc).take(3)
  }



}
