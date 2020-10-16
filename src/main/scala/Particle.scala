/*
    The purpose of this class is to provide proper implementation of a particle that we can
    assign a velocity, position, radius, and mass to, and from which we can simulate acceleration,
    collisions, and other functionalities. 
*/

class Particle(
        val name: String = "", 
        val radius: Double, 
        val mass: Double, 
        private var position: Vect = Vect(name+"_position", 0.0, 0.0, 0.0), 
        private var velocity: Vect = new Vect(name+"_velocity", 0.0, 0.0, 0.0)) {

    private var acceleration = new Vect(0.0, 0.0, 0.0)

    def distance(ambassador: Particle): Double = 
        {
            val dVect = ambassador.position - position
            dVect.mag
        }


    //returns the force of gravity between this particle and another (ambassador) as a vector
    //usees the function Fg = [G(m1 * m2) / |d|^3] * d such that d is the distance vector
    def gravF(ambassador: Particle): Vect = 
    {
        val distVect = ambassador.position - position

        val d = {
            val temp = distVect.mag
            temp * temp * temp
        }

        val scalD = (mass * ambassador.mass) / d
        
        distVect * scalD
    }

    def updateA(force: Vect) = acceleration = {force / mass}

    def updateV(dT: Double) = velocity = {velocity + (acceleration * dT)}

    def updateP(dT: Double) = position = {position + (velocity * dT)}
}