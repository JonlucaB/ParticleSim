import scala.collection.mutable
/*
    The purpose of the PSystem (Particle System) class is to provide the proper implementation
    of a class that can simulate a particle environment that we can put force, or 'kicks', into
    and see how these forces affect particles in real time
*/

class PSystem(val name: String = "") {
    val particles = new mutable.ListBuffer

    //adds an arbitrary amount of particles into the system
    def addParts(ps: Particle*) = for(p <- ps) particles+= p

    //updates all of the particles
    //with gravity, need to take the force of gracity on every single particle from the collection of particles,
    //then add them together into one final GForce vector, then send that to the particles updateA function
    def update(dT: Double) = {
        particleF = particles
    }

    //write the data of the particles into the file fileName
    def getParticles(fileName: String) = {
        ???
    }
}