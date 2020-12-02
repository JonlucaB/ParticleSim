package ParticleSim

import scala.io.StdIn._
import scala.collection.mutable

abstract class PSystem(val name: String = "")
{
    val particles = mutable.ListBuffer[Particle]()
    val backUpParticles = particles
    
    //adds an arbitrary amount of particles into the system
    def addParts(ps: Particle*) = for(p <- ps) {
        println(particles.length)
        particles += p
        backUpParticles += p
    }
    
    def addParts(ps: List[Particle]) = for(p <- ps) {
        particles += p
        backUpParticles += p
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
        }
    
        
    // Documentation stuff
    def documentParticleData(fileName: String) = {
        try {
            val writeXMLParticles = <Particles>{particles.map(p => p.toXML)}</Particles>
            scala.xml.XML.save(fileName, writeXMLParticles)
        }
        catch {
            case _ : Throwable => println("Invalid fileName: '"+fileName+"' given as argument to provide in System\n")
        }
    }
    
    def printParticles(acc: Boolean, vel: Boolean, pos: Boolean): Unit = 
    {
        println("Printing all particles in system -> "+name)
        for(particle <- particles) println(particle.toString(acc, vel, pos))
    }
}
