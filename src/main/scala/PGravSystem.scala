import scala.collection.mutable
/*
    The purpose of the PGravSystem (Particle System) class is to provide the proper implementation
    of a class that can simulate a particle environment that we can put force, or 'kicks', into
    and see how these forces affect particles in real time
*/
//val name: String = "", val view: Vect, val fwdVect: Vect, val upVect: Vect, val windowSize: Double
class PGravSystem (name: String = "", view: Vect, fwdVect: Vect, upVect: Vect, windowSize: Int) extends PSystem(name, view, fwdVect, upVect, windowSize) {
    //updates all of the particles 
    //updates all of the particles
    //with gravity, need to take the force of gravity on every single particle from the collection of particles,
    //then add them together into one final GForce vector, then send that to the particles updateA function
    def update(dT: Double) = {
        for(particle <- particles)
        {
            val gForces: mutable.ListBuffer[Vect] = particles.map[Vect](p => particle.gravF(p))
        
            val sumForce: Vect = gForces.reduce(_ + _)

            if(debug)
            {
                for(gF <- gForces) println(gF.toString)
                println(sumForce+"\n")
            }
            
            particle.updateA(sumForce)
        }

        for(particle <- particles)
        {
            particle.updateV(dT)
            particle.updateP(dT)
        }
    }

    def draw: Unit = ???
}