import scala.io.StdIn._
import scala.collection.mutable
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color
import scalafx.animation.AnimationTimer


abstract class PSystem(val name: String = "", val view: Vect, val fwdVect: Vect, val upVect: Vect, val windowSize: Int)
{
    // case class drawParticle(c: Color, pos: Vect)

    val test = "This Is a Test"
    val particles = mutable.ListBuffer[Particle]()
    val backUpParticles = particles
    
    val debug = false

    val points = Array.fill(windowSize)(Array.fill(windowSize)(0.0, Color.White))

    private var runTerminated = false
    
    //adds an arbitrary amount of particles into the system
    def addParts(ps: Particle*) = for(p <- ps) {
        println(particles.length)
        particles += p
        backUpParticles += p
    }
    def addParts(ps: mutable.ListBuffer[Particle]) = for(p <- ps) {
        println(p.toString(true, true, true))
        particles += p
        backUpParticles += p
    }
    
    //starts the simulatin, takes four arguments that determine how it is terminated
    def run(dT: Double  /*maxIts: Long, maxTime: Long, useIts: Boolean, useTime: Boolean*/): Array[Array[(Double, Color)]] = {
        // var time = System.nanoTime()
        // val startTime = time
        // var lastTime = -1L
        // var its = 0
        // while (check(its))
        // {
        //     if (paused)
        //     {
        //         //Do something that displays red text?
        //     }

            // val dT = (time - lastTime)/1e9d
            // lastTime = Time

            update(dT)

            for( t <- 0 until windowSize) { //iteratig through rows  //iterating -1 to 1 in var i  i = -1+(t*(2/length))
           
                for( c <- 0 until windowSize) {//iterating through columns //iterating -1 to 1 in var j    j = -1+(c*(2/length))
                
                    points(t)(c) = findSect(t, c)
                }
            }

            points
        }
        // stage = new JFXApp.PrimaryStage {
        //     title = name
        //     scene = new Scene(1000, 1000) {
        //         val border = new BorderPane
        //         val canvas  = new Canvas(1000, 1000)
        //         val gc = canvas.graphicsContext2D
        //         border.center = canvas
        //         root = border
                
        //         canvas.height <== border.height
        //         canvas.width <== border.width
                
        //         var time = System.nanoTime()
        //         val startTime = time
        //         var lastTime = -1L
        //         var its = 0
                
        //         val timer = AnimationTimer(time => {
        //             if(lastTime == 0) lastTime = time
        //             else if (check(its) && !paused) {
        //                 val dT = (time - lastTime)/1e9d
        //                 lastTime = time
                        
        //                 drawnParticles = particles.map(p => p.toDrawParticle)
        //                 gc.fill = Color.Black
        //                 gc.fillText((time/1e9).toString, 0, 0)
        //                 gc.fill = Color.White
        //                 gc.fillRect(0, 0, canvas.width.value, canvas.height.value)
                        
        //                 for (drawParticle(r, c, p) <- drawnParticles) {
        //                     val  (x, y, z) = p.getData
        //                     gc.fill = c
        //                     gc.fillOval(x * 100, y * 100, r, r)
        //                 }
        //             }
        //             else if (paused)
        //             {
        //                 gc.fill = Color.Red
        //                 gc.fillText("Simulation Paused", canvas.width.value/3, canvas.width.value/2)
        //             }
        //             else {
        //                 gc.fill = Color.Red
        //                 gc.fillText("Simulation Complete", 0, 0)
        //             }
        //         })

        // def check(iterations: Long): Boolean = {
        //     var timePass = !useTime
            
        //     if(useTime) timePass = (time - startTime - pausedTimePassed)/1e9 < maxTime
            
        //     var itsPass = !useIts 
            
        //     if(useIts) itsPass = iterations < maxIts
            
        //     timePass && itsPass
        // }
         //}
         // runTerminated = true
    

    def findSect(t: Int, c: Int): (Double, Color) = {
            val i = t*(2.0/windowSize)
          
            val j = c*(2.0/windowSize)

            val l = fwdVect x upVect
            
            val rayDrawn = new Ray(view, fwdVect + ((upVect * j) + (l * i)))

            val (particleSects, trash) = (Array.tabulate(particles.length)(p => rayDrawn.intersect(particles(p)))).partition(sect => sect._1 > 0.0)
          
            if(particleSects.isEmpty) (-1.0, Color.Black) else particleSects.minBy(_._1)
    }
    
    // def draw: Unit = {
    //     gc.fill = Color.White
    //     gc.fillRect(0, 0, canvas.width.value, canvas.height.value)
    
    // }
    
    def documentParticleData(fileName: String) = {
        try {
            val writeXMLParticles = <Particles>{particles.map(p => p.toXML)}</Particles>
            scala.xml.XML.save(fileName, writeXMLParticles)
        }
        catch {
            case _ : Throwable => println("Invalid fileName: '"+fileName+"' given as argument to provide in System\n")
        }
    }
    
    def update(dT: Double)
    
    
    //pauses and unpauses the simulation
    
    //resets the simulation by resetting the particles to their beginning state
    def restart = {
        particles.clear()
        var i = 0
        backUpParticles.map(p => {
            particles(i) = p
            i+=1})
            if(runTerminated) run(0)
        }
        
    def printParticles(acc: Boolean, vel: Boolean, pos: Boolean): Unit = 
    {
        println("Printing all particles in system -> "+name)
        for(particle <- particles) println(particle.toString(acc, vel, pos))
    }
}