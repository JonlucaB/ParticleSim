/*
    The purpose of this class is to provide proper implementation of a particle that we can
    assign a velocity, position, radius, and mass to, and from which we can simulate acceleration,
    collisions, and other functionalities. 
*/
import scala.xml.XML._
import scalafx.scene.paint.Color

class Particle(
        val name: String = "", 
        val radius: Double, 
        val mass: Double,
        private var acceleration: Vect = new Vect("acceleration", 0.0, 0.0, 0.0), 
        private var velocity: Vect = new Vect("velocity", 0.0, 0.0, 0.0),
        private var position: Vect = new Vect("position", 0.0, 0.0, 0.0)) { 

    val color = Color.color(Math.random(), Math.random(), Math.random());

    val debug = false

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

    def toXML: scala.xml.Node = {
        <particle nme={name.toString} rad={radius.toString} m={mass.toString}>
            {acceleration.toXML}
            {velocity.toXML}
            {position.toXML}
        </particle>
    }

    def toDrawParticle: drawParticle = drawParticle(radius, color, position)
}


object Particle {
    def apply(node: scala.xml.Node): Particle = {
        val name = (node \ "@nm").text
        val radius = (node \ "@rad").text.toDouble
        val mass = (node \ "@m").text.toDouble

        val vects = (node \\ "vect").map(n => Vect(n))

        new Particle(name, radius, mass, vects(0), vects(1), vects(2))
    }
}