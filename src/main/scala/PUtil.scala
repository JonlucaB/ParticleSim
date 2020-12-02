package ParticleSim

import scala.io.StdIn._
import scalafx.scene.paint.{Color, PhongMaterial}
import scala.xml._
import scala.collection.mutable

object PUtil
{

    //----------------------- LOADING INFO STREAMS FOR OBJECTS AND DATA --------------------------------
    def loadParticlesFromFile(file: String): List[Particle] = {
      val particlesXML = XML.load(file)
      var ret = List[Particle]()
      for(n <- particlesXML \ "particle") ret :+= Particle(n)
      ret
    }
    //-------------------------------------------------------------------------------------------------

    //----------------------- CHECK TO SEE IF ANY PARAMETERS HAVE BEEN REACHED ------------------------
    def check(maxTime: Long = -1L, maxIts: Long = -1L, iterations: Long = -1L, timePass: Long = -1L, startTime: Long = -1L, pauseTimePassed: Long = -1L): Boolean = {
        if (timePass != -1) (System.nanoTime - startTime - pauseTimePassed)/1e9 < maxTime
        else if (maxIts != -1) iterations < maxIts
        else false
    }

    def HitReturnToContinue = {
        println("----------------------------------------Hit Enter To Continue----------------------------------------\n")
        val plcHldr = readLine()
    }


    // Materials for making shapes
    val redMaterial = new PhongMaterial {
      diffuseColor = Color.DarkRed
      specularColor = Color.Red
    }
    val greenMaterial = new PhongMaterial {
      diffuseColor = Color.DarkGreen
      specularColor = Color.Green
    }
    val blueMaterial = new PhongMaterial {
      diffuseColor = Color.DarkBlue
      specularColor = Color.Blue
    }
    val whiteMaterial = new PhongMaterial {
      diffuseColor = Color.White
      specularColor = Color.LightBlue
    }
    val greyMaterial = new PhongMaterial {
      diffuseColor = Color.DarkGrey
      specularColor = Color.Grey
    }

    val randomMaterialSequence = Seq(redMaterial, greenMaterial, blueMaterial, whiteMaterial, greyMaterial)

    def randomMatieral: PhongMaterial = randomMaterialSequence(scala.util.Random.nextInt(randomMaterialSequence.length))
}
