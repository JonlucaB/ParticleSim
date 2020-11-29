import scala.xml._
import scala.io.StdIn._
import scala.collection.mutable
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color
import scalafx.animation.AnimationTimer
import scala.io.{Source, BufferedSource}
import scalafx.scene.image.Image
import scalafx.scene.image.{Image, ImageView, WritableImage, PixelReader, PixelWriter}
import scalafx.scene.layout.TilePane
import scalafx.scene.control.Label
import scala.io.Source
import scala.xml._
import scala.collection.mutable

//Will not forever be main. Main will eventually be translated into the 'runner' of a simulation, i.e. main will be the the one that prints, stops, and starts a system

object Main extends JFXApp {
    //TIMER VALUES  -----------------------------
    val maxIts = 1000
    val windowSize = 1000
    val maxTime = 10 //MAX TIMER IN SECONDS
    var paused = false
    var pausedTime = 0L
    var pausedTimePassed = 0L
    val useIts = false
    val useTime = true
    //--------------------------------------------

    //POOLS OF PARTICLES AND SYSTEMS -------------
    val particlePool = mutable.ListBuffer[Particle]()
    val systemPool = mutable.ListBuffer[PSystem]()
    val testSystem = new PGravSystem("TestSystem1", Vect("View", 0, 0, 0), Vect("Forward", 0, 0, 1), Vect("Up", 0, 1, 0), windowSize)
    //--------------------------------------------
    // println(testSystem.printParticles(true, true, true))
    
    //----------------------- LODAING INFO STREAMS FOR OBJECTS AND DATA --------------------------------
    val particlesXML = XML.load("particles.xml")
    
    for(n <- particlesXML \ "particle") testSystem.addParts(Particle(n))
    //-------------------------------------------------------------------------------------------------
    systemPool += testSystem
    testSystem.addParts(particlePool)
        
    testSystem.run(0)
    //SIMULATION SETUP VALUES ----------------
    val startTime = System.nanoTime()
    var lastTime = -1L
    var its = 0
    //----------------------------------------

    Platform.setImplicitExit(false)

    Platform.runLater(() -> {

    });

    stage = new JFXApp.PrimaryStage {
        title = "Particle Simulator"
        scene = new Scene(windowSize, windowSize) {
            
            def getColor(t: (Double, Color)): Color = {
                //println(t._2.toString)
                if(t._1 > 0) t._2 else Color.Black
            }

            val img = new WritableImage(windowSize, windowSize)
            val writer = img.pixelWriter
            
            val timer = AnimationTimer(time => {
                if(lastTime == 0) lastTime = time
                else if (check(its) && !paused) {
                    val dT = (time - lastTime)/1e9d
                    lastTime = time
                
                    testSystem.run(dT)
                    for(r <- 0 until windowSize) {
                        for(c <- 0 until windowSize) {
                            writer.setColor(r, c, getColor(testSystem.points(r)(c)))
                        }
                    }
                    val out = new ImageView(img)
                    val tilePane = new TilePane
                    tilePane.children = List(out)
                    root = tilePane
                }
            })
        }
    }
    
    testSystem.documentParticleData("./Saves/ParticlesAtTimeStamp"+System.nanoTime()+".xml")
    
    def check(iterations: Long): Boolean = {
        var timePass = !useTime
        
        if(useTime) timePass = (System.nanoTime - startTime - pausedTimePassed)/1e9 < maxTime
        
        var itsPass = !useIts 
        
        if(useIts) itsPass = iterations < maxIts
        
        timePass && itsPass
    }
    
    def restart = {
        println("Simulation restarted, please enter the following:\n")
        
        println("Max Iterations: ")
        val maxIts = readLong()
        
        println("Max Time (in seconds): ")
        var maxTime = readLong()
        
        println("Terminate run on a time-basis (T/F) :")
        var useTime = readLine() match {
            case "F" | "f" | "False" | "false" => false
            case "T" | "t" | "True" | "true" => true
            case _ => false
        }
        
        println("Terminate run on an iteration-basis (T/F) :")
        var useIts = readLine() match {
            case "F" | "f" | "False" | "false" => false
            case "T" | "t" | "True" | "true" => true
            case _ => false
        }
        testSystem.restart
    }
    def pause = {
        if(paused) {
            paused = !paused
            pausedTimePassed += (System.nanoTime() - pausedTime)
        }
        else pausedTime = System.nanoTime()
    }
}