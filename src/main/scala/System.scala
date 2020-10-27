import scala.io.StdIn._
import scala.collection.mutable

abstract class PSystem(val name: String = "") 
{
    val particles = mutable.ListBuffer[Particle]()
    val backUpParticles = particles
    
    val debug = false
    
    private var runTerminated = false

    private var paused = false
    var pausedTime = 0L
    var pausedTimePassed = 0L

    //adds an arbitrary amount of particles into the system
    def addParts(ps: Particle*) = for(p <- ps) {
        particles += p
        backUpParticles += p
    }
    def addParts(ps: mutable.ListBuffer[Particle]) = for(p <- ps) {
        particles += p
        backUpParticles += p
    }
    
    //starts the simulatin, takes four arguments that determine how it is terminated
    def run(maxIts: Long, maxTime: Long, useIts: Boolean, useTime: Boolean) = {
        
        var time = System.nanoTime()
        val startTime = time
        var lastTime = -1L
        var its = 0
        
        def check(iterations: Long): Boolean = {
            var timePass = !useTime
            
            if(useTime) timePass = (time - startTime - pausedTimePassed)/1e9 < maxTime
            
            var itsPass = !useIts 
            
            if(useIts) itsPass = iterations < maxIts
            
            timePass && itsPass
        }
        
        while(check(its))
        {
            time = System.nanoTime()
            if(lastTime > 0 && !paused)
            {
                val dT = (time - lastTime)/1e9d
                update(dT)
            }
            lastTime = time
            its += 1
        }

        runTerminated = true
    }
    
    def draw: Unit
    
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
    def pause = {
        if(paused) {
            paused = !paused
            pausedTimePassed += (System.nanoTime() - pausedTime)
        }
        else pausedTime = System.nanoTime()
    }
    
    //resets the simulation by resetting the particles to their beginning state
    def restart = {
        
        particles.clear()
        var i = 0
        backUpParticles.map(p => {
            particles(i) = p
            i+=1})
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
        
        println("Terminate run on anm iteration-basis (T/F) :")
        var useIts = readLine() match {
            case "F" | "f" | "False" | "false" => false
            case "T" | "t" | "True" | "true" => true
            case _ => false
        }
        if(runTerminated) run(maxIts, maxTime, useTime, useIts)
    }

    def printParticles(acc: Boolean, vel: Boolean, pos: Boolean): Unit = 
    {
        println("Printing all particles in system -> "+name)
        for(particle <- particles) println(particle.toString(acc, vel, pos))
    }
}