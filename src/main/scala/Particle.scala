/*
    The purpose of this class is to provide proper implementation of a particle that we can
    assign a velocity, position, radius, and mass to, and from which we can simulate acceleration,
    collisions, and other functionalities. 
*/

class Particle(
        val name: String = "", 
        val radius: Double, 
        val mass: Double, 
        private var position: Vect = new Vect("position", 0.0, 0.0, 0.0), 
        private var velocity: Vect = new Vect("velocity", 0.0, 0.0, 0.0)) {
    
    val debug = false
    private var acceleration = new Vect("Acceleration", 0.0, 0.0, 0.0)

    def distance(ambassador: Particle): Double = 
        {
            val dVect = ambassador.position - position
            dVect.mag
        }



    //returns the force of gravity between this particle and another (ambassador) as a vector
    //usees the function Fg = [G(m1 * m2) / |d|^3] * d such that d is the distance vector

    
    def gravF(ambassador: Particle): Vect = 
    {
        if(ambassador == this) return new Vect("", 0.0, 0.0, 0.0)

        val distVect = ambassador.position - position
        
        val d = {
            val temp = distVect.mag
            temp * temp * temp
        }
        
        
        val scalD = if(d != 0) (mass * ambassador.mass) / d else 0.0
        
        if(debug)
        {
            println("distVect = "+distVect.toString)
            println("d = "+d)
            println("scalD = "+scalD)
        }
        
        distVect * scalD
    }

    def updateA(force: Vect) = acceleration = {force / mass}

    def updateV(dT: Double) = velocity = {velocity + (acceleration * dT)}

    def updateP(dT: Double) = position = {position + (velocity * dT)}

    def toString(acc: Boolean, vel: Boolean, pos: Boolean): String = {
        val accS = if(acc) "Acceleration ---------- "+acceleration.toString+"\n" else ""
        val velS = if(vel) "Velocity     ---------- "+velocity.toString+"\n" else ""
        val posS = if(pos) "Position     ---------- "+position.toString+"\n" else ""

        "Vector: "+name+"\n+"+accS+"\n"+velS+"\n"+posS+"\n\n"
    }
}