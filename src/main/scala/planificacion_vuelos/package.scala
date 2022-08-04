
package object planificacion_vuelos {

  import scala.collection.{Map, Seq, mutable}
  import scala.util.Random
  import annotation.tailrec
  import scala.collection.parallel.{ParSeq, ParMap}
  import scala.collection.parallel.CollectionConverters._

  case class Aeropuerto(Cod: String, X: Int, Y: Int, GMT: Int)

  case class Vuelo(Aln: String, Num: Int, Org: String, HS: Int, MS: Int, Dst: String, HL: Int, ML: Int, Esc: Int)

  val aeropuertos = List(
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
  val vuelosB5 = List(
    Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 719, "PVD", 13, 20, "SEA", 14, 56, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
      Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("AA", 181, "MIA", 9, 15, "PVD", 11, 10, 0),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("AA", 64, "DFW", 8, 0, "SEA", 16, 23, 0),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("AA", 1, "MIA", 9, 0, "LAX", 11, 39, 0),
    Vuelo("AA", 272, "LAX", 7, 25, "SEA", 13, 7, 0),
    Vuelo("AA", 227, "MIA", 9, 24, "SFO", 10, 17, 0),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 523, "TPA", 7, 45, "DFW", 15, 1, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 2, "SFO", 8, 30, "TPA", 16, 46, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 719, "PVD", 13, 20, "SEA", 14, 56, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 719, "PVD", 13, 20, "SEA", 14, 56, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 719, "PVD", 13, 20, "SEA", 14, 56, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("AA", 27, "MIA", 8, 0, "SEA", 11, 27, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1),
    Vuelo("4X", 201, "MSY", 8, 35, "HOU", 12, 20, 2),
    Vuelo("4X", 372, "MSY", 11, 0, "HOU", 12, 55, 1),
    Vuelo("4X", 213, "MSY", 14, 0, "HOU", 17, 40, 3),
    Vuelo("4X", 374, "MSY", 16, 0, "HOU", 17, 55, 1),
    Vuelo("4X", 216, "HOU", 14, 30, "MSY", 18, 10, 3),
    Vuelo("4X", 370, "MSY", 6, 15, "HOU", 11, 0, 1),
    Vuelo("4X", 375, "HOU", 18, 20, "MSY", 10, 15, 1),
    Vuelo("4X", 214, "HOU", 9, 0, "MSY", 12, 40, 3),
    Vuelo("4X", 371, "HOU", 8, 35, "MSY", 10, 30, 1),
    Vuelo("AA", 324, "STL", 8, 30, "ORD", 14, 5, 0),
    Vuelo("AA", 828, "MSY", 17, 10, "BNA", 18, 37, 0),
    Vuelo("AA", 719, "PVD", 13, 20, "SEA", 14, 56, 0),
    Vuelo("AA", 212, "DFW", 17, 21, "ORD", 19, 41, 0),
    Vuelo("AA", 673, "ORD", 21, 30, "PVD", 12, 7, 0),
    Vuelo("AA", 25, "DFW", 18, 37, "PHX", 10, 1, 0),
    Vuelo("4X", 373, "HOU", 13, 15, "MSY", 15, 10, 1)
  )


  type Itinerario = List[Vuelo]

  def toItin(vuelo: Vuelo) = List(vuelo)

  def vuelosOrigen(origen: String, vuelos: List[Vuelo]): List[Vuelo] = vuelos.filter(p => p.Org == origen)
  def vuelosOrigenPar(origen: String, vuelos: ParSeq[Vuelo]): ParSeq[Vuelo] = vuelos.filter(p => p.Org == origen).par

  def escalasPosibles(origen: String, vuelos: List[Vuelo]): List[Vuelo] = vuelos.filterNot(v => v.Org== origen || v.Dst== origen)
  def escalasPosiblesPar(origen: String, vuelos: ParSeq[Vuelo]): ParSeq[Vuelo] = vuelos.filterNot(v => v.Org== origen || v.Dst== origen).par


  def lleganDestino(destino: String, itinerarios: List[Itinerario]): List[Itinerario] = itinerarios.filter((itin:Itinerario) => itin.last.Dst == destino)
  def lleganDestinoPar(destino: String, itinerarios: ParSeq[Itinerario]): ParSeq[Itinerario] = itinerarios.filter((itin:Itinerario) => itin.last.Dst == destino)



  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos de forma paralela contando los vuelos directos y escalas.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de itinerarios
   */

  def itinerario(a1: String, a2: String): List[Itinerario] = {
    def auxItinerario(a1: String, vuelo: List[Vuelo]): List[Itinerario] = {
      if (vuelo.isEmpty) {
        List(List())

      } else {

        val vuelosConMismoOrigen = vuelosOrigen(a1, vuelo).map(vuelo => List(vuelo))

        val itinEscala = for {
          vueloOrigen <- vuelosConMismoOrigen.map(f => f.head)
          vueloEscala <- auxItinerario(vueloOrigen.Dst, escalasPosibles(vueloOrigen.Org, vuelo))
        } yield vueloOrigen :: vueloEscala
        vuelosConMismoOrigen ++ itinEscala
      }
    }

    lleganDestino(a2, auxItinerario(a1, vuelosB5))
  }

  def itinerariosPar(a1: String, a2: String): ParSeq[Itinerario] = {
    def auxItinerario(a1: String, vuelo: List[Vuelo]): ParSeq[Itinerario] = {
      if (vuelo.isEmpty) {
        List(List()).par

      } else {

        val vuelosConMismoOrigen = vuelosOrigenPar(a1, vuelo.par).map(vuelo => List(vuelo))

        val itinEscala = for {
          vueloOrigen <- vuelosConMismoOrigen.map(f => f.head)
          vueloEscala <- auxItinerario(vueloOrigen.Dst, escalasPosiblesPar(vueloOrigen.Org, vuelo.par).toList)
        } yield vueloOrigen :: vueloEscala
        vuelosConMismoOrigen ++ itinEscala
      }
    }
    lleganDestinoPar(a2, auxItinerario(a1, vuelosB5))
  }




  /**
   * Funcion que nos ayuda a obtener el gmt de un aeropuerto, devolviendo su hora y minuto en una lista
   * @param aero
   * @return List(hora, minuto)
   */
  def obtenerGmt(aero: String):List[Int] = {
    val gmt = for {
      aero1 <- aeropuertos
      if aero == aero1.Cod
    } yield aero1

    List(gmt(0).GMT / 100, gmt(0).GMT % 100)
  }


  /**
   * Funcion que recibe una hora, un minuto y una lisa de enteros que son la hora y el minuto GMT de un aeropuerto
   * y devuelve la nueva hora y minuto en una lista, posterior a acomodar su gmt
   * @param hora
   * @param min
   * @param gmt
   * @return List(hora, minuto)
   */
  def pasarAgmt(hora: Int, min: Int, gmt: List[Int]): List[Int] = {

    if (gmt.head > 0) { //restar
      if ((hora - gmt.head) < 0) { //baja de 0 la hora
        if ((min - gmt.last) < 0) { //baja de 0 el minuto
          val h = 24 + (hora - gmt.head)
          val m = 60 + (min - gmt.last)
          List(h, m)
        } else { //se mantiene el minuto
          val h = 24 + (hora - gmt.head)
          val m = min - gmt.last
          List(h, m)
        }
      } else { //se mantiene la hora
        if ((min - gmt.last) < 0) { //baja de 0 el minuto
          val h = hora - gmt.head
          val m = 60 + (min - gmt.last)
          List(h, m)
        } else { //se mantiene el minuto
          val h = hora - gmt.head
          val m = min - gmt.last
          List(h, m)
        }
      }
    } else if (gmt.head < 0) { //sumar
      if ((hora + (-gmt.head)) > 24) { //sube de 24 la hora
        if ((min + (-gmt.last)) > 60) { //sube de 60 el minuto
          val h = (hora + (-gmt.head)) - 24
          val m = (min + (-gmt.last)) - 60
          List(h, m)
        } else { //se mantiene el minuto
          val h = (hora + (-gmt.head)) - 24
          val m = min + gmt.last
          List(h, m)
        }
      } else { //se mantiene la hora
        if ((min + (-gmt.last)) > 60) { //sube de 60 el minuto
          val h = hora + (-gmt.head)
          val m = (min + (-gmt.last)) - 60
          List(h, m)
        } else { //se mantiene el minuto
          val h = hora + (-gmt.head)
          val m = min + gmt.last
          List(h, m)
        }
      }
    } else { //queda igual
      val h = hora
      val m = min
      List(h, m)
    }

  }

  /**
   * Funcion que recibe un itinerario y evuelve cada vuelo de este con su hora en horario gmt, para así
   * saber realmente a que hora salio un vuelo de manera general
   * @param itinerario
   * @return Itinerario
   */
  def itinerarioGMT(itinerario: Itinerario): Itinerario = {
    def vueloConGMT(v1: Vuelo): Vuelo = {
      val horaSalida = pasarAgmt(v1.HS, v1.MS, obtenerGmt(v1.Org))
      val horaLlegada = pasarAgmt(v1.HL, v1.ML, obtenerGmt(v1.Dst))
      Vuelo(v1.Aln, v1.Num, v1.Org, horaSalida(0), horaSalida(1), v1.Dst, horaLlegada(0), horaLlegada(1), v1.Esc)
    }

    for {
      vuelos <- itinerario
    } yield vueloConGMT(vuelos)
  }

  /**
   * funcion que recibe un vuelo y usando su hora de salida y de llegada calcula cuanto tardo en realizar el viaje,
   * devolviendo su hora en formato 24 horas. once horas seria 1100,  una hora sería 100, un minuto sería 1
   * @param vuelo
   * @return List[hora]
   */
  def tiempoVuelo(vuelo: Vuelo):List[String] = {
    if (vuelo.HL < vuelo.HS) {
      if (vuelo.ML < vuelo.MS) {
        val hour = 24 - vuelo.HS + vuelo.HL - 1
        val min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      } else if (vuelo.ML == vuelo.MS) {
        val hour = 24 - vuelo.HS + vuelo.HL
        List(hour.toString, "0")
      } else {
        val hour = 24 - vuelo.HS + vuelo.HL
        val min = vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      }
    } else {
      if (vuelo.ML < vuelo.MS) {
        val hour = vuelo.HL - vuelo.HS - 1
        val min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      } else if (vuelo.ML == vuelo.MS) {
        val hour = vuelo.HL - vuelo.HS
        List(hour.toString + "0")
      } else {
        val hour = vuelo.HL - vuelo.HS
        val min = vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      }
    }
  }

  /**
   * Convierte una lista de String que viene con una hora y minuto List(hora, minuto) en un numero entero horaminuto
   * @param hora
   * @return int
   */
  def horatoInt(hora: List[String]): Int = {

    if (hora.last.toInt < 10){
      List(hora.head+"0"+hora.last).head.toInt
    }else{
      List(hora.head+hora.last).head.toInt
    }

  }

  /**
   * Recibe la hora y minuto de salida de primer vuelo, y la hora y minuto de llegada dle ultimo y calcula cuanto
   * tomo realizar el viaje en total
   * @param hs
   * @param ms
   * @param hl
   * @param ml
   * @return Int
   */
  def sacarTiempoViaje(hs:Int, ms:Int, hl:Int, ml:Int): Int ={
    val vueloImaginario = Vuelo("ALN",0,"ORG",hs,ms,"DST",hl, ml,0)
    horatoInt(tiempoVuelo(vueloImaginario))
  }

  /**
   * Recibe un itinerario con sus horas en gmt y las convierte a su hora local
   * @param itinerario
   * @return Itinerario
   */
  def gmtALocal(itinerario: Itinerario): Itinerario ={
    def vueloConGMT(v1: Vuelo): Vuelo = {
      val horaSalida = pasarAgmt(v1.HS, v1.MS, List(-obtenerGmt(v1.Org).head, -obtenerGmt(v1.Org).last))
      val horaLlegada = pasarAgmt(v1.HL, v1.ML, List(-obtenerGmt(v1.Dst).head, -obtenerGmt(v1.Dst).last))
      Vuelo(v1.Aln, v1.Num, v1.Org, horaSalida(0), horaSalida(1), v1.Dst, horaLlegada(0), horaLlegada(1), v1.Esc)
    }
    for {
      vuelos <- itinerario
    } yield vueloConGMT(vuelos)
  }

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada, encuentra al menos tres itinerarios (si los hay)
   * que correspondan a los menores tiempos de viaje (contando tiempo de vuelo y tiempo de espera en tierra)
   * @param a1 origen
   * @param a2 destino
   * @return Lista de itinerarios
   */

  def itenerariosMenorTiempoTotal(a1:String, a2:String): List [Itinerario] = {
    val itinerarios = itinerario(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario))
    val vuelosOrdenadosTiempo = itinerarios.sortBy((i:Itinerario) => sacarTiempoViaje(i.head.HS, i.head.MS, i.last.HS,i.last.MS))
    vuelosOrdenadosTiempo.take(3).map(itinerario => gmtALocal(itinerario))
  }

  def itenerariosMenorTiempoTotalPar(a1:String, a2:String): List [Itinerario] = {
    val itinerarios = itinerariosPar(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario)).toList
    val vuelosOrdenadosTiempo = itinerarios.sortBy((i:Itinerario) => sacarTiempoViaje(i.head.HS, i.head.MS, i.last.HS,i.last.MS))
    vuelosOrdenadosTiempo.take(3).map(itinerario => gmtALocal(itinerario))
  }


  /**
   * Esta funcion suma las escalas de los itinerarios de vuelo contando la escala que hacen cuando se bajan del avion
   *
   * @param itinerario
   * @return int
   */

  def sumEscalas(itinerario: Itinerario): Int = {
    itinerario.collect { case vuelo: Vuelo => vuelo.Esc }.sum + itinerario.length - 1
  }

  /**
   * Esta funcion nos ayudara a encontrar los itinerarios que cumplan una determinada ruta y a su vez
   * cuenten con el menor numero de escalas posibles.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de itinerarios
   */
  def itinierariosMenorEscalas(a1: String, a2: String): List[Itinerario] = {
    itinerario(a1, a2).sortBy(itinerario => sumEscalas(itinerario)).take(3)
  }

  def itinierariosMenorEscalasPar(a1: String, a2: String): List[Itinerario] = {
    val itinerario = itinerariosPar(a1,a2).toList
    itinerario.sortBy(itinerario => sumEscalas(itinerario)).take(3)
  }


  /**
   * Esta funcion realiza el tiempo de vuelo de cada itinerario sin contar el tiempo en tierra.
   *
   * @param itinerario
   * @return int
   */

  def sumarHoras(itinerario: Itinerario): Int ={
    itinerario.collect { case vuelo: Vuelo => tiempoVuelo(vuelo)(0).toInt}.sum
  }

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada, encuentra tre itinerarios (si los hay)
   * que hagan el menor numero de cambios de avion sin contar el tiempo total del vuelo.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de itinerarios
   */
  def itenerariosMenorTiempo(a1:String, a2:String): List [Itinerario] = {
    val vuelos = itinerario(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario))
    val vuelosOrdenadosTiempo = vuelos.sortBy((i:Itinerario) => sumarHoras(i))
    vuelosOrdenadosTiempo.take(3).map(itinerario => gmtALocal(itinerario))
  }

  def itenerariosMenorTiempoPar(a1:String, a2:String): List [Itinerario] = {
    val itinerarios = itinerariosPar(a1:String, a2:String)
    val vuelos = itinerarios.map((itinerario:Itinerario) => itinerarioGMT(itinerario)).toList
    val vuelosOrdenadosTiempo = vuelos.sortBy((i:Itinerario) => sumarHoras(i))
    vuelosOrdenadosTiempo.take(3).map(itinerario => gmtALocal(itinerario))
  }



 /**
  * Esta funcion dados un aeropuerto destino a2 y una hora de la cita h:m
  * determina el itinerario tal que la hora de salida de a1 sea la hora más tarde posible para salir del aeropuerto a1
  * y llegar a tiempo a la cita (suponga que la cita es en el mismo aeropuerto).
  * @param a1 origen
  * @param a2 destino
  * @return lista de itinerarios
  *
  */

  def itinerariosSalidaSeq(a1:String, a2:String, h: Int, m: Int): List[Itinerario] = {
   val vuelosATiempo = itinerario(a1, a2).filter((p:Itinerario) => horatoInt(List(p.last.HL.toString, p.last.ML.toString))
     < horatoInt(List(h.toString, m.toString)))
   vuelosATiempo.sortBy((i:Itinerario) => horatoInt(List(i.head.HS.toString, i.head.MS.toString))).reverse.take(3)
  }

  def itinerariosSalidaPar(a1:String, a2:String, h: Int, m: Int): List[Itinerario] = {
    val itinerarios = itinerariosPar(a1,a2)
    val vuelosATiempo = itinerarios.filter((p:Itinerario) => horatoInt(List(p.last.HL.toString, p.last.ML.toString))
      < horatoInt(List(h.toString, m.toString))).toList
    vuelosATiempo.sortBy((i:Itinerario) => horatoInt(List(i.head.HS.toString, i.head.MS.toString))).reverse.take(3)
  }

}
